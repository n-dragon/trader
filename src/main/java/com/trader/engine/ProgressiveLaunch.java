package com.trader.engine;

import com.trader.config.DynamicConfig;
import com.trader.core.Trade;
import com.trader.core.TradingBot;
import com.trader.metrics.PerformanceMetrics;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Progressive launch manager for safely deploying trading strategies.
 * Implements a staged approach: Paper Trading -> Micro-trading -> Gradual capital increase.
 */
public class ProgressiveLaunch {
    private static final Logger logger = LoggerFactory.getLogger(ProgressiveLaunch.class);

    // Launch phases
    public enum LaunchPhase {
        PAPER_TRADING,      // Simulated trading
        MICRO_TRADING,      // Minimal real capital
        SCALING_UP,         // Gradually increasing capital
        FULL_PRODUCTION     // Full capital deployment
    }

    // Capital progression steps
    private static final double[] CAPITAL_STEPS = {100, 250, 500, 1000, 2500, 5000, 10000};

    // Minimum performance thresholds
    private static final double MIN_SHARPE_RATIO = 1.5;
    private static final double MIN_WIN_RATE = 0.55;
    private static final double MAX_DRAWDOWN = 0.15;
    private static final int MIN_TRADES_PER_PHASE = 20;

    private final TradingBot bot;
    private final ScheduledExecutorService scheduler;
    private final List<PhaseResult> phaseResults;
    private final Consumer<LaunchEvent> eventCallback;

    private volatile LaunchPhase currentPhase;
    private volatile int currentCapitalStep;
    private volatile boolean running;
    private volatile Instant phaseStartTime;

    public ProgressiveLaunch(TradingBot bot, Consumer<LaunchEvent> eventCallback) {
        this.bot = bot;
        this.eventCallback = eventCallback;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "progressive-launch");
            t.setDaemon(true);
            return t;
        });
        this.phaseResults = new CopyOnWriteArrayList<>();
        this.currentPhase = LaunchPhase.PAPER_TRADING;
        this.currentCapitalStep = 0;
        this.running = false;
    }

    /**
     * Starts the progressive launch process.
     */
    public void start() {
        if (running) {
            logger.warn("Progressive launch is already running");
            return;
        }

        running = true;
        logger.info("Starting progressive launch process...");
        publishEvent(LaunchEventType.LAUNCH_STARTED, "Progressive launch initiated");

        // Start with paper trading
        startPhase(LaunchPhase.PAPER_TRADING);
    }

    /**
     * Stops the progressive launch process.
     */
    public void stop() {
        if (!running) {
            return;
        }

        running = false;
        bot.stop();
        publishEvent(LaunchEventType.LAUNCH_STOPPED, "Progressive launch stopped");
        logger.info("Progressive launch stopped");
    }

    /**
     * Starts a new launch phase.
     */
    private void startPhase(LaunchPhase phase) {
        currentPhase = phase;
        phaseStartTime = Instant.now();

        logger.info("Starting phase: {}", phase);
        publishEvent(LaunchEventType.PHASE_STARTED, "Phase started: " + phase);

        switch (phase) {
            case PAPER_TRADING -> startPaperTrading();
            case MICRO_TRADING -> startMicroTrading();
            case SCALING_UP -> startScalingUp();
            case FULL_PRODUCTION -> startFullProduction();
        }
    }

    /**
     * Phase 1: Paper Trading (simulated, no real money).
     */
    private void startPaperTrading() {
        logger.info("Starting paper trading phase (30 days recommended)");

        // Configure bot for paper trading
        bot.getConfig().setProperty("trading.mode", "paper");
        bot.start();

        // Schedule phase evaluation after 30 days (or configurable period)
        schedulePaperTradingEvaluation();
    }

    /**
     * Schedules evaluation of paper trading results.
     */
    private void schedulePaperTradingEvaluation() {
        Duration phaseDuration = Duration.ofDays(30); // 30 days of paper trading

        scheduler.schedule(() -> {
            if (!running) return;

            PhaseResult result = evaluatePhase();
            phaseResults.add(result);

            logger.info("Paper trading results: sharpe={}, winRate={}, trades={}",
                    result.getSharpeRatio(), result.getWinRate(), result.getTradeCount());

            if (meetsPerformanceTargets(result)) {
                logger.info("Paper trading targets met, advancing to micro-trading");
                bot.stop();
                startPhase(LaunchPhase.MICRO_TRADING);
            } else {
                logger.warn("Paper trading targets NOT met, continuing paper trading");
                publishEvent(LaunchEventType.TARGETS_NOT_MET,
                        "Paper trading targets not met: " + result.getFailureReasons());
                // Reset and continue paper trading
                phaseStartTime = Instant.now();
                schedulePaperTradingEvaluation();
            }
        }, phaseDuration.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Phase 2: Micro-trading (minimal real capital).
     */
    private void startMicroTrading() {
        double capital = CAPITAL_STEPS[0]; // Start with $100
        logger.info("Starting micro-trading phase with ${}", capital);

        // Configure bot for live trading with minimal capital
        bot.getConfig().setProperty("trading.mode", "live");
        bot.getConfig().setProperty("total.capital", String.valueOf(capital));
        bot.start();

        // Schedule evaluation after 1 week
        scheduleMicroTradingEvaluation();
    }

    /**
     * Schedules evaluation of micro-trading results.
     */
    private void scheduleMicroTradingEvaluation() {
        Duration phaseDuration = Duration.ofDays(7); // 1 week of micro-trading

        scheduler.schedule(() -> {
            if (!running) return;

            PhaseResult result = evaluatePhase();
            phaseResults.add(result);

            logger.info("Micro-trading results: sharpe={}, winRate={}, trades={}",
                    result.getSharpeRatio(), result.getWinRate(), result.getTradeCount());

            if (meetsPerformanceTargets(result)) {
                logger.info("Micro-trading targets met, advancing to scaling phase");
                currentCapitalStep = 1; // Move to next capital step
                bot.stop();
                startPhase(LaunchPhase.SCALING_UP);
            } else {
                logger.warn("Micro-trading targets NOT met");
                publishEvent(LaunchEventType.TARGETS_NOT_MET,
                        "Micro-trading targets not met: " + result.getFailureReasons());
                // Go back to paper trading
                bot.stop();
                startPhase(LaunchPhase.PAPER_TRADING);
            }
        }, phaseDuration.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Phase 3: Scaling Up (gradually increasing capital).
     */
    private void startScalingUp() {
        if (currentCapitalStep >= CAPITAL_STEPS.length) {
            // Reached full capital
            startPhase(LaunchPhase.FULL_PRODUCTION);
            return;
        }

        double capital = CAPITAL_STEPS[currentCapitalStep];
        logger.info("Scaling up to ${} (step {}/{})",
                capital, currentCapitalStep + 1, CAPITAL_STEPS.length);

        bot.getConfig().setProperty("total.capital", String.valueOf(capital));
        bot.start();

        // Schedule evaluation after 2 weeks per step
        scheduleScalingEvaluation();
    }

    /**
     * Schedules evaluation of scaling phase results.
     */
    private void scheduleScalingEvaluation() {
        Duration phaseDuration = Duration.ofDays(14); // 2 weeks per capital step

        scheduler.schedule(() -> {
            if (!running) return;

            PhaseResult result = evaluatePhase();
            phaseResults.add(result);

            double currentCapital = CAPITAL_STEPS[currentCapitalStep];
            logger.info("Scaling step ${} results: sharpe={}, winRate={}, drawdown={}",
                    currentCapital, result.getSharpeRatio(), result.getWinRate(), result.getMaxDrawdown());

            if (meetsPerformanceTargets(result)) {
                currentCapitalStep++;

                if (currentCapitalStep >= CAPITAL_STEPS.length) {
                    logger.info("All scaling steps completed, advancing to full production");
                    bot.stop();
                    startPhase(LaunchPhase.FULL_PRODUCTION);
                } else {
                    logger.info("Scaling targets met, advancing to next step");
                    bot.stop();
                    startScalingUp();
                }
            } else {
                logger.warn("Scaling targets NOT met at ${}", currentCapital);
                publishEvent(LaunchEventType.TARGETS_NOT_MET,
                        String.format("Targets not met at $%.0f: %s", currentCapital, result.getFailureReasons()));
                // Stay at current capital level
                phaseStartTime = Instant.now();
                scheduleScalingEvaluation();
            }
        }, phaseDuration.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Phase 4: Full Production.
     */
    private void startFullProduction() {
        logger.info("Entering full production mode");
        publishEvent(LaunchEventType.FULL_PRODUCTION_REACHED, "Full production mode activated");

        // Continue with full capital, no more evaluations needed
        // Bot continues running normally
    }

    /**
     * Evaluates the current phase performance.
     */
    private PhaseResult evaluatePhase() {
        List<Trade> trades = getPhaseTradesInternal();
        PerformanceMetrics metricsCalculator = new PerformanceMetrics();

        double winRate = 0;
        double sharpeRatio = 0;
        double maxDrawdown = 0;
        double profitFactor = 0;
        double totalReturn = 0;

        if (!trades.isEmpty()) {
            // Calculate metrics
            long wins = trades.stream().filter(t -> t.getProfit() > 0).count();
            winRate = (double) wins / trades.size();

            double totalProfit = trades.stream()
                    .filter(t -> t.getProfit() > 0)
                    .mapToDouble(Trade::getProfit)
                    .sum();
            double totalLoss = Math.abs(trades.stream()
                    .filter(t -> t.getProfit() < 0)
                    .mapToDouble(Trade::getProfit)
                    .sum());

            profitFactor = totalLoss > 0 ? totalProfit / totalLoss : totalProfit > 0 ? Double.MAX_VALUE : 0;

            // Calculate returns for Sharpe
            List<Double> returns = trades.stream()
                    .map(Trade::getProfitPercent)
                    .toList();

            if (!returns.isEmpty()) {
                double avgReturn = returns.stream().mapToDouble(d -> d).average().orElse(0);
                double stdDev = calculateStdDev(returns);
                sharpeRatio = stdDev > 0 ? avgReturn / stdDev * Math.sqrt(252) : 0;
            }

            // Calculate max drawdown
            maxDrawdown = calculateMaxDrawdown(trades);
            totalReturn = trades.stream().mapToDouble(Trade::getProfit).sum();
        }

        List<String> failureReasons = new ArrayList<>();
        if (trades.size() < MIN_TRADES_PER_PHASE) {
            failureReasons.add(String.format("Insufficient trades: %d (min: %d)", trades.size(), MIN_TRADES_PER_PHASE));
        }
        if (sharpeRatio < MIN_SHARPE_RATIO) {
            failureReasons.add(String.format("Low Sharpe ratio: %.2f (min: %.2f)", sharpeRatio, MIN_SHARPE_RATIO));
        }
        if (winRate < MIN_WIN_RATE) {
            failureReasons.add(String.format("Low win rate: %.2f%% (min: %.0f%%)", winRate * 100, MIN_WIN_RATE * 100));
        }
        if (maxDrawdown > MAX_DRAWDOWN) {
            failureReasons.add(String.format("High drawdown: %.2f%% (max: %.0f%%)", maxDrawdown * 100, MAX_DRAWDOWN * 100));
        }

        return PhaseResult.builder()
                .phase(currentPhase)
                .startTime(phaseStartTime)
                .endTime(Instant.now())
                .tradeCount(trades.size())
                .winRate(winRate)
                .sharpeRatio(sharpeRatio)
                .maxDrawdown(maxDrawdown)
                .profitFactor(profitFactor)
                .totalReturn(totalReturn)
                .meetsTargets(failureReasons.isEmpty())
                .failureReasons(failureReasons)
                .build();
    }

    /**
     * Gets trades from current phase.
     */
    private List<Trade> getPhaseTradesInternal() {
        // This would normally get trades from the bot's trade history
        // For now, return empty list - in real implementation, filter by phaseStartTime
        return new ArrayList<>();
    }

    /**
     * Checks if performance targets are met.
     */
    private boolean meetsPerformanceTargets(PhaseResult result) {
        return result.isMeetsTargets();
    }

    /**
     * Publishes a launch event.
     */
    private void publishEvent(LaunchEventType type, String message) {
        if (eventCallback != null) {
            eventCallback.accept(LaunchEvent.builder()
                    .type(type)
                    .phase(currentPhase)
                    .message(message)
                    .timestamp(Instant.now())
                    .build());
        }
    }

    /**
     * Calculates standard deviation.
     */
    private double calculateStdDev(List<Double> values) {
        if (values.isEmpty()) return 0;
        double mean = values.stream().mapToDouble(d -> d).average().orElse(0);
        double sumSquaredDiff = values.stream()
                .mapToDouble(v -> Math.pow(v - mean, 2))
                .sum();
        return Math.sqrt(sumSquaredDiff / values.size());
    }

    /**
     * Calculates maximum drawdown from trades.
     */
    private double calculateMaxDrawdown(List<Trade> trades) {
        if (trades.isEmpty()) return 0;

        double peak = 0;
        double maxDrawdown = 0;
        double cumulative = 0;

        for (Trade trade : trades) {
            cumulative += trade.getProfit();
            if (cumulative > peak) {
                peak = cumulative;
            }
            double drawdown = peak > 0 ? (peak - cumulative) / peak : 0;
            maxDrawdown = Math.max(maxDrawdown, drawdown);
        }

        return maxDrawdown;
    }

    /**
     * Gets current launch status.
     */
    public LaunchStatus getStatus() {
        return LaunchStatus.builder()
                .currentPhase(currentPhase)
                .currentCapitalStep(currentCapitalStep)
                .currentCapital(currentCapitalStep < CAPITAL_STEPS.length ?
                        CAPITAL_STEPS[currentCapitalStep] : CAPITAL_STEPS[CAPITAL_STEPS.length - 1])
                .phaseStartTime(phaseStartTime)
                .phaseDuration(phaseStartTime != null ?
                        Duration.between(phaseStartTime, Instant.now()) : Duration.ZERO)
                .running(running)
                .phaseResults(new ArrayList<>(phaseResults))
                .build();
    }

    /**
     * Forces advancement to next phase (for testing/override).
     */
    public void forceAdvancePhase() {
        logger.warn("Forcing phase advancement from {}", currentPhase);

        bot.stop();

        switch (currentPhase) {
            case PAPER_TRADING -> startPhase(LaunchPhase.MICRO_TRADING);
            case MICRO_TRADING -> {
                currentCapitalStep = 1;
                startPhase(LaunchPhase.SCALING_UP);
            }
            case SCALING_UP -> {
                currentCapitalStep++;
                if (currentCapitalStep >= CAPITAL_STEPS.length) {
                    startPhase(LaunchPhase.FULL_PRODUCTION);
                } else {
                    startScalingUp();
                }
            }
            case FULL_PRODUCTION -> logger.info("Already in full production");
        }
    }

    /**
     * Shuts down the progressive launch manager.
     */
    public void shutdown() {
        stop();
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

    // Event types
    public enum LaunchEventType {
        LAUNCH_STARTED,
        LAUNCH_STOPPED,
        PHASE_STARTED,
        PHASE_COMPLETED,
        TARGETS_MET,
        TARGETS_NOT_MET,
        FULL_PRODUCTION_REACHED,
        ERROR
    }

    @Data
    @Builder
    public static class LaunchEvent {
        private LaunchEventType type;
        private LaunchPhase phase;
        private String message;
        private Instant timestamp;
    }

    @Data
    @Builder
    public static class PhaseResult {
        private LaunchPhase phase;
        private Instant startTime;
        private Instant endTime;
        private int tradeCount;
        private double winRate;
        private double sharpeRatio;
        private double maxDrawdown;
        private double profitFactor;
        private double totalReturn;
        private boolean meetsTargets;
        private List<String> failureReasons;
    }

    @Data
    @Builder
    public static class LaunchStatus {
        private LaunchPhase currentPhase;
        private int currentCapitalStep;
        private double currentCapital;
        private Instant phaseStartTime;
        private Duration phaseDuration;
        private boolean running;
        private List<PhaseResult> phaseResults;
    }
}
