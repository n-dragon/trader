package com.trader.validation;

import com.trader.core.Candle;
import com.trader.core.Trade;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Validates trading strategies using Walk-Forward Analysis and Monte Carlo simulation
 * to prevent overfitting and provide realistic performance estimates.
 */
public class BacktestValidator {
    private static final Logger logger = LoggerFactory.getLogger(BacktestValidator.class);

    private final int trainSize;
    private final int testSize;
    private final int step;
    private final int monteCarloIterations;
    private final ExecutorService executor;

    public BacktestValidator(int trainSize, int testSize, int step, int monteCarloIterations) {
        this.trainSize = trainSize > 0 ? trainSize : 700;
        this.testSize = testSize > 0 ? testSize : 300;
        this.step = step > 0 ? step : 50;
        this.monteCarloIterations = monteCarloIterations > 0 ? monteCarloIterations : 1000;
        this.executor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                r -> {
                    Thread t = new Thread(r, "backtest-validator");
                    t.setDaemon(true);
                    return t;
                }
        );
    }

    /**
     * Performs Walk-Forward Analysis to validate a strategy.
     * This method trains on historical data and tests on unseen data using a sliding window.
     *
     * @param data         Historical candle data
     * @param ruleExtractor Function that extracts trading rules from training data
     * @param backtester    Function that backtests rules on test data
     * @return Validation report with consistency metrics
     */
    public ValidationReport walkForwardAnalysis(
            List<Candle> data,
            Function<List<Candle>, List<CombinedRule>> ruleExtractor,
            Function<WalkForwardInput, TestResult> backtester
    ) {
        if (data.size() < trainSize + testSize) {
            throw new IllegalArgumentException(
                    String.format("Insufficient data: need at least %d candles, got %d",
                            trainSize + testSize, data.size()));
        }

        List<TestResult> results = new ArrayList<>();
        int windowCount = 0;

        for (int i = 0; i + trainSize + testSize <= data.size(); i += step) {
            windowCount++;

            // Training set
            List<Candle> trainData = data.subList(i, i + trainSize);
            List<CombinedRule> rules = ruleExtractor.apply(trainData);

            // Test set (unseen data)
            List<Candle> testData = data.subList(i + trainSize, i + trainSize + testSize);

            // Backtest on test data
            WalkForwardInput input = WalkForwardInput.builder()
                    .rules(rules)
                    .testData(testData)
                    .windowNumber(windowCount)
                    .build();

            TestResult result = backtester.apply(input);
            results.add(result);

            logger.debug("Walk-forward window {}: return={:.2f}%, winRate={:.2f}%",
                    windowCount, result.getReturnPercent(), result.getWinRate() * 100);
        }

        return analyzeConsistency(results);
    }

    /**
     * Performs Monte Carlo simulation to estimate the range of possible outcomes.
     *
     * @param historicalTrades List of historical trades
     * @return Monte Carlo report with percentile outcomes
     */
    public MonteCarloReport monteCarloSimulation(List<Trade> historicalTrades) {
        if (historicalTrades.isEmpty()) {
            throw new IllegalArgumentException("No historical trades provided");
        }

        Random random = new Random();
        List<Double> possibleReturns = new CopyOnWriteArrayList<>();

        // Run simulations in parallel
        List<Callable<Double>> tasks = new ArrayList<>();
        for (int i = 0; i < monteCarloIterations; i++) {
            tasks.add(() -> {
                // Bootstrap sample: randomly resample trades with replacement
                List<Trade> randomSequence = bootstrapSample(historicalTrades, random);
                return calculateTotalReturn(randomSequence);
            });
        }

        try {
            List<Future<Double>> futures = executor.invokeAll(tasks);
            for (Future<Double> future : futures) {
                possibleReturns.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Monte Carlo simulation failed", e);
            Thread.currentThread().interrupt();
        }

        Collections.sort(possibleReturns);

        return MonteCarloReport.builder()
                .iterations(monteCarloIterations)
                .worstCase5Percent(calculatePercentile(possibleReturns, 5))
                .percentile25(calculatePercentile(possibleReturns, 25))
                .median(calculatePercentile(possibleReturns, 50))
                .percentile75(calculatePercentile(possibleReturns, 75))
                .bestCase95Percent(calculatePercentile(possibleReturns, 95))
                .mean(possibleReturns.stream().mapToDouble(d -> d).average().orElse(0))
                .standardDeviation(calculateStdDev(possibleReturns))
                .probabilityOfLoss(calculateProbabilityOfLoss(possibleReturns))
                .build();
    }

    /**
     * Analyzes the consistency of walk-forward results.
     */
    private ValidationReport analyzeConsistency(List<TestResult> results) {
        if (results.isEmpty()) {
            return ValidationReport.builder()
                    .isValid(false)
                    .failureReasons(List.of("No test results"))
                    .build();
        }

        List<String> failureReasons = new ArrayList<>();

        // Calculate average metrics
        double avgReturn = results.stream()
                .mapToDouble(TestResult::getReturnPercent)
                .average()
                .orElse(0);

        double avgWinRate = results.stream()
                .mapToDouble(TestResult::getWinRate)
                .average()
                .orElse(0);

        double avgProfitFactor = results.stream()
                .mapToDouble(TestResult::getProfitFactor)
                .average()
                .orElse(0);

        // Calculate consistency (% of profitable windows)
        long profitableWindows = results.stream()
                .filter(r -> r.getReturnPercent() > 0)
                .count();
        double consistency = (double) profitableWindows / results.size();

        // Calculate return variance
        double returnVariance = calculateVariance(
                results.stream().map(TestResult::getReturnPercent).toList()
        );

        // Validation criteria
        if (avgReturn < 0) {
            failureReasons.add(String.format("Negative average return: %.2f%%", avgReturn));
        }
        if (avgWinRate < 0.4) {
            failureReasons.add(String.format("Low win rate: %.2f%%", avgWinRate * 100));
        }
        if (avgProfitFactor < 1.0) {
            failureReasons.add(String.format("Profit factor below 1: %.2f", avgProfitFactor));
        }
        if (consistency < 0.6) {
            failureReasons.add(String.format("Low consistency: %.2f%% (< 60%%)", consistency * 100));
        }

        return ValidationReport.builder()
                .isValid(failureReasons.isEmpty())
                .windowCount(results.size())
                .averageReturn(avgReturn)
                .averageWinRate(avgWinRate)
                .averageProfitFactor(avgProfitFactor)
                .consistency(consistency)
                .returnVariance(returnVariance)
                .testResults(results)
                .failureReasons(failureReasons)
                .build();
    }

    /**
     * Creates a bootstrap sample (random sample with replacement).
     */
    private List<Trade> bootstrapSample(List<Trade> trades, Random random) {
        List<Trade> sample = new ArrayList<>(trades.size());
        for (int i = 0; i < trades.size(); i++) {
            sample.add(trades.get(random.nextInt(trades.size())));
        }
        return sample;
    }

    /**
     * Calculates total return from a sequence of trades.
     */
    private double calculateTotalReturn(List<Trade> trades) {
        double capital = 10000; // Normalized starting capital
        for (Trade trade : trades) {
            capital += trade.getProfit();
        }
        return ((capital - 10000) / 10000) * 100;
    }

    /**
     * Calculates the percentile value from a sorted list.
     */
    private double calculatePercentile(List<Double> sortedValues, int percentile) {
        if (sortedValues.isEmpty()) return 0;
        int index = (int) Math.ceil(percentile / 100.0 * sortedValues.size()) - 1;
        return sortedValues.get(Math.max(0, Math.min(index, sortedValues.size() - 1)));
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
     * Calculates variance.
     */
    private double calculateVariance(List<Double> values) {
        double stdDev = calculateStdDev(values);
        return stdDev * stdDev;
    }

    /**
     * Calculates probability of loss (% of negative outcomes).
     */
    private double calculateProbabilityOfLoss(List<Double> returns) {
        if (returns.isEmpty()) return 0;
        long negativeCount = returns.stream().filter(r -> r < 0).count();
        return (double) negativeCount / returns.size();
    }

    /**
     * Shuts down the executor service.
     */
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Data classes
    @Data
    @Builder
    public static class WalkForwardInput {
        private List<CombinedRule> rules;
        private List<Candle> testData;
        private int windowNumber;
    }

    @Data
    @Builder
    public static class TestResult {
        private int windowNumber;
        private int tradeCount;
        private double returnPercent;
        private double winRate;
        private double profitFactor;
        private double maxDrawdown;
        private double sharpeRatio;
    }

    @Data
    @Builder
    public static class ValidationReport {
        private boolean isValid;
        private int windowCount;
        private double averageReturn;
        private double averageWinRate;
        private double averageProfitFactor;
        private double consistency;
        private double returnVariance;
        private List<TestResult> testResults;
        private List<String> failureReasons;

        public String getSummary() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Walk-Forward Validation Report ===\n");
            sb.append(String.format("Valid: %s\n", isValid ? "YES" : "NO"));
            sb.append(String.format("Windows tested: %d\n", windowCount));
            sb.append(String.format("Average return: %.2f%%\n", averageReturn));
            sb.append(String.format("Average win rate: %.2f%%\n", averageWinRate * 100));
            sb.append(String.format("Average profit factor: %.2f\n", averageProfitFactor));
            sb.append(String.format("Consistency: %.2f%%\n", consistency * 100));

            if (!failureReasons.isEmpty()) {
                sb.append("\nFailure reasons:\n");
                for (String reason : failureReasons) {
                    sb.append(String.format("  - %s\n", reason));
                }
            }

            return sb.toString();
        }
    }

    @Data
    @Builder
    public static class MonteCarloReport {
        private int iterations;
        private double worstCase5Percent;
        private double percentile25;
        private double median;
        private double percentile75;
        private double bestCase95Percent;
        private double mean;
        private double standardDeviation;
        private double probabilityOfLoss;

        public String getSummary() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Monte Carlo Simulation Report ===\n");
            sb.append(String.format("Iterations: %d\n", iterations));
            sb.append(String.format("Worst case (5%%): %.2f%%\n", worstCase5Percent));
            sb.append(String.format("25th percentile: %.2f%%\n", percentile25));
            sb.append(String.format("Median: %.2f%%\n", median));
            sb.append(String.format("75th percentile: %.2f%%\n", percentile75));
            sb.append(String.format("Best case (95%%): %.2f%%\n", bestCase95Percent));
            sb.append(String.format("Mean: %.2f%%\n", mean));
            sb.append(String.format("Std Dev: %.2f%%\n", standardDeviation));
            sb.append(String.format("Probability of loss: %.2f%%\n", probabilityOfLoss * 100));
            return sb.toString();
        }
    }

    /**
     * Represents a combined trading rule extracted from historical data.
     */
    @Data
    @Builder
    public static class CombinedRule {
        private String name;
        private String description;
        private List<Condition> conditions;
        private double confidence;
        private double historicalWinRate;

        @Data
        @Builder
        public static class Condition {
            private String indicator;
            private String operator;
            private double value;
        }
    }
}
