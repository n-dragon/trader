package com.trader.risk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Circuit breaker pattern implementation for trading protection.
 * Suspends trading when too many losses occur or daily loss limit is reached.
 */
public class CircuitBreaker {
    private static final Logger logger = LoggerFactory.getLogger(CircuitBreaker.class);

    private final int maxConsecutiveLosses;
    private final double maxDailyLossPercent;
    private final Duration resetDuration;
    private final Consumer<String> alertCallback;
    private final ScheduledExecutorService scheduler;

    private int consecutiveLosses;
    private double dailyLoss;
    private double dailyStartCapital;
    private boolean circuitOpen;
    private Instant circuitOpenTime;
    private String circuitOpenReason;

    public CircuitBreaker(
            int maxConsecutiveLosses,
            double maxDailyLossPercent,
            Duration resetDuration,
            Consumer<String> alertCallback
    ) {
        this.maxConsecutiveLosses = maxConsecutiveLosses > 0 ? maxConsecutiveLosses : 5;
        this.maxDailyLossPercent = maxDailyLossPercent > 0 ? maxDailyLossPercent : 5.0;
        this.resetDuration = resetDuration != null ? resetDuration : Duration.ofHours(24);
        this.alertCallback = alertCallback;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "circuit-breaker-scheduler");
            t.setDaemon(true);
            return t;
        });
        this.consecutiveLosses = 0;
        this.dailyLoss = 0;
        this.circuitOpen = false;
    }

    /**
     * Checks if trading is allowed.
     */
    public boolean allowTrade() {
        if (circuitOpen) {
            logger.warn("Circuit is OPEN - Trading suspended since {} due to: {}",
                    circuitOpenTime, circuitOpenReason);
            return false;
        }
        return true;
    }

    /**
     * Records the result of a trade.
     *
     * @param profit     The profit/loss of the trade
     * @param capital    Current total capital
     */
    public void recordTradeResult(double profit, double capital) {
        if (profit < 0) {
            consecutiveLosses++;
            dailyLoss += Math.abs(profit);

            logger.info("Loss recorded: {:.2f}, consecutive losses: {}, daily loss: {:.2f}",
                    profit, consecutiveLosses, dailyLoss);

            // Check consecutive losses
            if (consecutiveLosses >= maxConsecutiveLosses) {
                openCircuit(String.format("Too many consecutive losses (%d)", consecutiveLosses));
            }

            // Check daily loss limit
            double dailyLossPercent = (dailyLoss / dailyStartCapital) * 100;
            if (dailyLossPercent >= maxDailyLossPercent) {
                openCircuit(String.format("Daily loss limit reached (%.2f%%)", dailyLossPercent));
            }
        } else {
            // Reset consecutive losses on a win
            if (consecutiveLosses > 0) {
                logger.info("Win recorded, resetting consecutive losses counter");
            }
            consecutiveLosses = 0;
        }
    }

    /**
     * Opens the circuit breaker, suspending trading.
     */
    public void openCircuit(String reason) {
        if (circuitOpen) {
            return; // Already open
        }

        circuitOpen = true;
        circuitOpenTime = Instant.now();
        circuitOpenReason = reason;

        String message = String.format("CIRCUIT BREAKER ACTIVATED: %s", reason);
        logger.error(message);

        // Notify via callback
        if (alertCallback != null) {
            alertCallback.accept(message);
        }

        // Schedule automatic reset
        scheduleCircuitReset();
    }

    /**
     * Manually closes the circuit breaker.
     */
    public void closeCircuit() {
        if (!circuitOpen) {
            return;
        }

        circuitOpen = false;
        consecutiveLosses = 0;
        circuitOpenTime = null;
        circuitOpenReason = null;

        logger.info("Circuit breaker manually closed - Trading resumed");

        if (alertCallback != null) {
            alertCallback.accept("Circuit breaker closed - Trading resumed");
        }
    }

    /**
     * Schedules automatic circuit reset after the configured duration.
     */
    private void scheduleCircuitReset() {
        logger.info("Scheduling circuit reset in {}", resetDuration);

        scheduler.schedule(() -> {
            logger.info("Automatic circuit reset triggered");
            closeCircuit();
        }, resetDuration.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Resets daily counters. Should be called at the start of each trading day.
     */
    public void resetDailyCounters(double currentCapital) {
        dailyLoss = 0;
        dailyStartCapital = currentCapital;
        logger.info("Daily counters reset, start capital: {:.2f}", currentCapital);
    }

    /**
     * Gets the current state of the circuit breaker.
     */
    public CircuitBreakerState getState() {
        return CircuitBreakerState.builder()
                .circuitOpen(circuitOpen)
                .consecutiveLosses(consecutiveLosses)
                .dailyLoss(dailyLoss)
                .dailyLossPercent(dailyStartCapital > 0 ? (dailyLoss / dailyStartCapital) * 100 : 0)
                .circuitOpenTime(circuitOpenTime)
                .circuitOpenReason(circuitOpenReason)
                .build();
    }

    /**
     * Shuts down the scheduler.
     */
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Getters
    public boolean isCircuitOpen() {
        return circuitOpen;
    }

    public int getConsecutiveLosses() {
        return consecutiveLosses;
    }

    public double getDailyLoss() {
        return dailyLoss;
    }

    @lombok.Data
    @lombok.Builder
    public static class CircuitBreakerState {
        private boolean circuitOpen;
        private int consecutiveLosses;
        private double dailyLoss;
        private double dailyLossPercent;
        private Instant circuitOpenTime;
        private String circuitOpenReason;
    }
}
