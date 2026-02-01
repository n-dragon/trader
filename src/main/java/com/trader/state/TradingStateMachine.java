package com.trader.state;

import com.trader.core.TradingSignal;
import com.trader.risk.CircuitBreaker;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple state machine for signal detection.
 * Only detects and validates signals - does not execute orders.
 */
public class TradingStateMachine {
    private static final Logger logger = LoggerFactory.getLogger(TradingStateMachine.class);

    private final CircuitBreaker circuitBreaker;
    private final List<SignalListener> listeners;

    private volatile TradingState currentState;
    private TradingSignal lastSignal;
    private Instant lastStateChange;

    public TradingStateMachine(CircuitBreaker circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
        this.listeners = new ArrayList<>();
        this.currentState = TradingState.IDLE;
        this.lastStateChange = Instant.now();
    }

    /**
     * Trading states - simplified for signal detection only.
     */
    public enum TradingState {
        IDLE,           // Waiting for signals
        SIGNAL_DETECTED,// Signal detected, awaiting confirmation
        PAUSED          // Trading paused (circuit breaker)
    }

    /**
     * Processes a trading signal and notifies listeners if valid.
     */
    public SignalResult processSignal(TradingSignal signal) {
        logger.debug("Processing signal {} in state {}", signal.getType(), currentState);

        // Check circuit breaker
        if (!circuitBreaker.allowTrade()) {
            if (currentState != TradingState.PAUSED) {
                transitionTo(TradingState.PAUSED, "Circuit breaker active");
            }
            return SignalResult.rejected("Circuit breaker active");
        }

        // Resume from paused if circuit breaker allows
        if (currentState == TradingState.PAUSED) {
            transitionTo(TradingState.IDLE, "Circuit breaker cleared");
        }

        // Ignore neutral signals
        if (signal.getType() == TradingSignal.SignalType.NEUTRAL) {
            return SignalResult.ignored("Neutral signal");
        }

        // Validate signal confidence
        if (!signal.isConfident(0.5)) {
            return SignalResult.rejected("Low confidence: " + signal.getConfidence());
        }

        // Signal is valid - store and notify
        lastSignal = signal;
        transitionTo(TradingState.SIGNAL_DETECTED, "Signal: " + signal.getType());

        // Notify all listeners
        notifyListeners(signal);

        // Return to idle after processing
        transitionTo(TradingState.IDLE, "Signal processed");

        return SignalResult.accepted(signal);
    }

    /**
     * Adds a listener for detected signals.
     */
    public void addSignalListener(SignalListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a signal listener.
     */
    public void removeSignalListener(SignalListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(TradingSignal signal) {
        for (SignalListener listener : listeners) {
            try {
                listener.onSignalDetected(signal);
            } catch (Exception e) {
                logger.error("Error notifying signal listener", e);
            }
        }
    }

    private void transitionTo(TradingState newState, String reason) {
        TradingState oldState = currentState;
        currentState = newState;
        lastStateChange = Instant.now();
        logger.info("State: {} -> {} ({})", oldState, newState, reason);
    }

    /**
     * Manually pauses signal detection.
     */
    public void pause(String reason) {
        if (currentState != TradingState.PAUSED) {
            transitionTo(TradingState.PAUSED, "Manual pause: " + reason);
        }
    }

    /**
     * Resumes signal detection.
     */
    public void resume() {
        if (currentState == TradingState.PAUSED && circuitBreaker.allowTrade()) {
            transitionTo(TradingState.IDLE, "Resumed");
        }
    }

    /**
     * Resets the state machine.
     */
    public void reset() {
        lastSignal = null;
        transitionTo(TradingState.IDLE, "Reset");
    }

    // Getters
    public TradingState getCurrentState() {
        return currentState;
    }

    public TradingSignal getLastSignal() {
        return lastSignal;
    }

    public Instant getLastStateChange() {
        return lastStateChange;
    }

    public boolean canProcess() {
        return currentState != TradingState.PAUSED && circuitBreaker.allowTrade();
    }

    /**
     * Result of signal processing.
     */
    @Data
    @Builder
    public static class SignalResult {
        private boolean accepted;
        private String message;
        private TradingSignal signal;

        public static SignalResult accepted(TradingSignal signal) {
            return SignalResult.builder()
                    .accepted(true)
                    .message("Signal accepted")
                    .signal(signal)
                    .build();
        }

        public static SignalResult rejected(String reason) {
            return SignalResult.builder()
                    .accepted(false)
                    .message(reason)
                    .build();
        }

        public static SignalResult ignored(String reason) {
            return SignalResult.builder()
                    .accepted(false)
                    .message(reason)
                    .build();
        }
    }

    /**
     * Listener interface for signal detection.
     */
    public interface SignalListener {
        void onSignalDetected(TradingSignal signal);
    }
}
