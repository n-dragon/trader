package com.trader.state;

import com.trader.core.Position;
import com.trader.core.Trade;
import com.trader.core.TradingSignal;
import com.trader.risk.CircuitBreaker;
import com.trader.risk.RiskManagement;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * State machine for managing trading states and transitions.
 * Ensures consistent behavior and prevents invalid state transitions.
 */
public class TradingStateMachine {
    private static final Logger logger = LoggerFactory.getLogger(TradingStateMachine.class);

    private final RiskManagement riskManagement;
    private final CircuitBreaker circuitBreaker;
    private final ReentrantLock stateLock;
    private final List<StateChangeListener> listeners;

    private volatile TradingState currentState;
    private Position openPosition;
    private TradingSignal pendingSignal;
    private Instant lastStateChange;
    private String currentSymbol;

    public TradingStateMachine(RiskManagement riskManagement, CircuitBreaker circuitBreaker) {
        this.riskManagement = riskManagement;
        this.circuitBreaker = circuitBreaker;
        this.stateLock = new ReentrantLock();
        this.listeners = new ArrayList<>();
        this.currentState = TradingState.IDLE;
        this.lastStateChange = Instant.now();
    }

    /**
     * Trading states.
     */
    public enum TradingState {
        IDLE,           // No position, waiting for signals
        ANALYZING,      // Analyzing market for potential entry
        WAITING_ENTRY,  // Signal detected, waiting for confirmation
        IN_POSITION,    // Position is open
        WAITING_EXIT,   // Exit signal, waiting for execution
        EMERGENCY_EXIT, // Emergency exit (stop-loss triggered)
        PAUSED,         // Trading paused (manual or circuit breaker)
        ERROR           // Error state
    }

    /**
     * Handles a trading signal and updates state accordingly.
     */
    public StateTransitionResult handleSignal(TradingSignal signal) {
        stateLock.lock();
        try {
            logger.debug("Handling signal {} in state {}", signal.getType(), currentState);

            // Check circuit breaker
            if (!circuitBreaker.allowTrade()) {
                if (currentState != TradingState.PAUSED) {
                    transitionTo(TradingState.PAUSED, "Circuit breaker active");
                }
                return StateTransitionResult.blocked("Circuit breaker active");
            }

            switch (currentState) {
                case IDLE:
                    return handleIdleState(signal);

                case ANALYZING:
                    return handleAnalyzingState(signal);

                case WAITING_ENTRY:
                    return handleWaitingEntryState(signal);

                case IN_POSITION:
                    return handleInPositionState(signal);

                case WAITING_EXIT:
                    return handleWaitingExitState(signal);

                case PAUSED:
                    return StateTransitionResult.blocked("Trading is paused");

                case ERROR:
                    return StateTransitionResult.blocked("System in error state");

                default:
                    return StateTransitionResult.blocked("Unknown state");
            }
        } finally {
            stateLock.unlock();
        }
    }

    private StateTransitionResult handleIdleState(TradingSignal signal) {
        if (signal.isEntrySignal() && signal.isConfident(0.5)) {
            // Check risk rules
            double estimatedPositionValue = signal.getSuggestedQuantity() * signal.getPrice();
            RiskManagement.RiskCheckResult riskCheck = riskManagement.checkRiskRules(estimatedPositionValue);

            if (!riskCheck.isAllowed()) {
                return StateTransitionResult.blocked("Risk rules violated: " + riskCheck.getViolations());
            }

            pendingSignal = signal;
            transitionTo(TradingState.WAITING_ENTRY, "Entry signal received");

            return StateTransitionResult.success(
                    TradingState.IDLE,
                    TradingState.WAITING_ENTRY,
                    "Preparing entry"
            );
        }

        return StateTransitionResult.noAction("No actionable signal in IDLE state");
    }

    private StateTransitionResult handleAnalyzingState(TradingSignal signal) {
        if (signal.isEntrySignal() && signal.isConfident(0.7)) {
            pendingSignal = signal;
            transitionTo(TradingState.WAITING_ENTRY, "Strong entry signal confirmed");
            return StateTransitionResult.success(TradingState.ANALYZING, TradingState.WAITING_ENTRY, "Entry confirmed");
        }

        // Return to idle if no strong signal
        transitionTo(TradingState.IDLE, "No strong signal detected");
        return StateTransitionResult.success(TradingState.ANALYZING, TradingState.IDLE, "Analysis complete, no entry");
    }

    private StateTransitionResult handleWaitingEntryState(TradingSignal signal) {
        // Timeout or conflicting signal
        if (signal.isExitSignal() || signal.getType() == TradingSignal.SignalType.NEUTRAL) {
            pendingSignal = null;
            transitionTo(TradingState.IDLE, "Entry cancelled due to conflicting signal");
            return StateTransitionResult.success(TradingState.WAITING_ENTRY, TradingState.IDLE, "Entry cancelled");
        }

        return StateTransitionResult.noAction("Waiting for entry execution");
    }

    private StateTransitionResult handleInPositionState(TradingSignal signal) {
        if (openPosition == null) {
            transitionTo(TradingState.ERROR, "No open position in IN_POSITION state");
            return StateTransitionResult.error("Invalid state: no position");
        }

        // Check for exit conditions
        double currentPrice = signal.getPrice();

        // Stop-loss check
        if (openPosition.isStopLossTriggered(currentPrice)) {
            transitionTo(TradingState.EMERGENCY_EXIT, "Stop-loss triggered");
            return StateTransitionResult.success(
                    TradingState.IN_POSITION,
                    TradingState.EMERGENCY_EXIT,
                    String.format("Stop-loss at %.2f", currentPrice)
            );
        }

        // Take-profit check
        if (openPosition.isTakeProfitReached(currentPrice)) {
            transitionTo(TradingState.WAITING_EXIT, "Take-profit reached");
            return StateTransitionResult.success(
                    TradingState.IN_POSITION,
                    TradingState.WAITING_EXIT,
                    String.format("Take-profit at %.2f", currentPrice)
            );
        }

        // Update trailing stop
        if (openPosition.updateTrailingStop(currentPrice)) {
            logger.info("Trailing stop updated to {:.2f}", openPosition.getStopLossPrice());
        }

        // Exit signal
        if (signal.isExitSignal() && signal.isConfident(0.6)) {
            transitionTo(TradingState.WAITING_EXIT, "Exit signal received");
            return StateTransitionResult.success(
                    TradingState.IN_POSITION,
                    TradingState.WAITING_EXIT,
                    "Exit signal"
            );
        }

        return StateTransitionResult.noAction("Monitoring position");
    }

    private StateTransitionResult handleWaitingExitState(TradingSignal signal) {
        // If price moves against us during exit wait, trigger emergency exit
        if (openPosition != null && openPosition.isStopLossTriggered(signal.getPrice())) {
            transitionTo(TradingState.EMERGENCY_EXIT, "Stop-loss during exit wait");
            return StateTransitionResult.success(TradingState.WAITING_EXIT, TradingState.EMERGENCY_EXIT, "Emergency exit");
        }

        return StateTransitionResult.noAction("Waiting for exit execution");
    }

    /**
     * Called when an entry order is filled.
     */
    public void onEntryFilled(Position position) {
        stateLock.lock();
        try {
            if (currentState != TradingState.WAITING_ENTRY) {
                logger.warn("Entry filled in unexpected state: {}", currentState);
            }

            this.openPosition = position;
            this.currentSymbol = position.getSymbol();
            riskManagement.registerPosition(position);

            transitionTo(TradingState.IN_POSITION, "Entry filled at " + position.getEntryPrice());

            notifyListeners(StateChangeEvent.builder()
                    .fromState(TradingState.WAITING_ENTRY)
                    .toState(TradingState.IN_POSITION)
                    .position(position)
                    .timestamp(Instant.now())
                    .build());
        } finally {
            stateLock.unlock();
        }
    }

    /**
     * Called when an exit order is filled.
     */
    public void onExitFilled(double exitPrice, String exitReason) {
        stateLock.lock();
        try {
            if (openPosition == null) {
                logger.error("Exit filled but no open position");
                return;
            }

            Trade trade = openPosition.closePosition(exitPrice, exitReason);
            riskManagement.recordTrade(trade);
            circuitBreaker.recordTradeResult(trade.getProfit(), riskManagement.getTotalCapital());

            TradingState previousState = currentState;
            openPosition = null;
            pendingSignal = null;
            currentSymbol = null;

            transitionTo(TradingState.IDLE, String.format("Exit filled at %.2f, P&L: %.2f", exitPrice, trade.getProfit()));

            notifyListeners(StateChangeEvent.builder()
                    .fromState(previousState)
                    .toState(TradingState.IDLE)
                    .trade(trade)
                    .timestamp(Instant.now())
                    .build());
        } finally {
            stateLock.unlock();
        }
    }

    /**
     * Manually pauses trading.
     */
    public void pause(String reason) {
        stateLock.lock();
        try {
            if (currentState != TradingState.PAUSED) {
                transitionTo(TradingState.PAUSED, "Manual pause: " + reason);
            }
        } finally {
            stateLock.unlock();
        }
    }

    /**
     * Resumes trading from paused state.
     */
    public void resume() {
        stateLock.lock();
        try {
            if (currentState == TradingState.PAUSED) {
                if (openPosition != null) {
                    transitionTo(TradingState.IN_POSITION, "Resumed with open position");
                } else {
                    transitionTo(TradingState.IDLE, "Resumed");
                }
            }
        } finally {
            stateLock.unlock();
        }
    }

    /**
     * Resets the state machine.
     */
    public void reset() {
        stateLock.lock();
        try {
            openPosition = null;
            pendingSignal = null;
            currentSymbol = null;
            transitionTo(TradingState.IDLE, "State machine reset");
        } finally {
            stateLock.unlock();
        }
    }

    private void transitionTo(TradingState newState, String reason) {
        TradingState oldState = currentState;
        currentState = newState;
        lastStateChange = Instant.now();
        logger.info("State transition: {} -> {} ({})", oldState, newState, reason);
    }

    public void addListener(StateChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners(StateChangeEvent event) {
        for (StateChangeListener listener : listeners) {
            try {
                listener.onStateChange(event);
            } catch (Exception e) {
                logger.error("Error notifying state change listener", e);
            }
        }
    }

    // Getters
    public TradingState getCurrentState() {
        return currentState;
    }

    public Position getOpenPosition() {
        return openPosition;
    }

    public TradingSignal getPendingSignal() {
        return pendingSignal;
    }

    public Instant getLastStateChange() {
        return lastStateChange;
    }

    public String getCurrentSymbol() {
        return currentSymbol;
    }

    public boolean isInPosition() {
        return currentState == TradingState.IN_POSITION;
    }

    public boolean canTrade() {
        return currentState == TradingState.IDLE && circuitBreaker.allowTrade();
    }

    @Data
    @Builder
    public static class StateTransitionResult {
        private boolean success;
        private boolean blocked;
        private TradingState fromState;
        private TradingState toState;
        private String message;

        public static StateTransitionResult success(TradingState from, TradingState to, String message) {
            return StateTransitionResult.builder()
                    .success(true)
                    .blocked(false)
                    .fromState(from)
                    .toState(to)
                    .message(message)
                    .build();
        }

        public static StateTransitionResult blocked(String reason) {
            return StateTransitionResult.builder()
                    .success(false)
                    .blocked(true)
                    .message(reason)
                    .build();
        }

        public static StateTransitionResult noAction(String reason) {
            return StateTransitionResult.builder()
                    .success(true)
                    .blocked(false)
                    .message(reason)
                    .build();
        }

        public static StateTransitionResult error(String error) {
            return StateTransitionResult.builder()
                    .success(false)
                    .blocked(false)
                    .message(error)
                    .build();
        }
    }

    @Data
    @Builder
    public static class StateChangeEvent {
        private TradingState fromState;
        private TradingState toState;
        private Position position;
        private Trade trade;
        private Instant timestamp;
    }

    public interface StateChangeListener {
        void onStateChange(StateChangeEvent event);
    }
}
