package com.trader.exchange;

import com.trader.core.Exchange;
import com.trader.core.PriceData;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Validates price data across multiple exchanges to detect anomalies,
 * manipulation, and ensure price consensus before executing trades.
 */
public class MultiExchangeValidator {
    private static final Logger logger = LoggerFactory.getLogger(MultiExchangeValidator.class);

    private static final double DEFAULT_MAX_DEVIATION = 0.01; // 1%
    private static final double OUTLIER_Z_SCORE_THRESHOLD = 2.5;
    private static final long MAX_PRICE_AGE_MS = 5000; // 5 seconds

    private final double maxDeviation;
    private final int minExchangesForConsensus;

    public MultiExchangeValidator() {
        this(DEFAULT_MAX_DEVIATION, 3);
    }

    public MultiExchangeValidator(double maxDeviation, int minExchangesForConsensus) {
        this.maxDeviation = maxDeviation;
        this.minExchangesForConsensus = minExchangesForConsensus;
    }

    /**
     * Validates price data from multiple exchanges.
     *
     * @param prices Map of exchange to price data
     * @return Validation result with consensus price and any issues detected
     */
    public ValidationResult validatePrices(Map<Exchange, PriceData> prices) {
        List<String> warnings = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        if (prices.size() < minExchangesForConsensus) {
            errors.add(String.format("Insufficient exchanges: %d (minimum: %d)",
                    prices.size(), minExchangesForConsensus));
            return ValidationResult.builder()
                    .valid(false)
                    .errors(errors)
                    .build();
        }

        // Check for stale data
        for (Map.Entry<Exchange, PriceData> entry : prices.entrySet()) {
            if (entry.getValue().isStale(MAX_PRICE_AGE_MS)) {
                warnings.add(String.format("Stale data from %s", entry.getKey()));
            }
        }

        // Extract prices
        List<Double> priceList = prices.values().stream()
                .map(PriceData::getPrice)
                .filter(p -> p > 0)
                .collect(Collectors.toList());

        if (priceList.isEmpty()) {
            errors.add("No valid prices available");
            return ValidationResult.builder()
                    .valid(false)
                    .errors(errors)
                    .build();
        }

        // Detect outliers using Grubbs' test
        OutlierResult outlierResult = detectOutliers(prices);
        if (outlierResult.hasOutliers()) {
            warnings.add(String.format("Outliers detected: %s", outlierResult.outlierExchanges));

            // Remove outliers from price list
            priceList = prices.entrySet().stream()
                    .filter(e -> !outlierResult.outlierExchanges.contains(e.getKey()))
                    .map(e -> e.getValue().getPrice())
                    .collect(Collectors.toList());
        }

        // Calculate coefficient of variation
        double mean = priceList.stream().mapToDouble(d -> d).average().orElse(0);
        double stdDev = calculateStdDev(priceList);
        double cv = stdDev / mean;

        if (cv > maxDeviation) {
            errors.add(String.format("Price deviation too high: CV=%.2f%% (max: %.2f%%)",
                    cv * 100, maxDeviation * 100));
        }

        // Calculate volume-weighted average price (VWAP)
        double vwap = calculateVWAP(prices, outlierResult.outlierExchanges);

        // Check if reference exchange (Binance) is close to VWAP
        PriceData binancePrice = prices.get(Exchange.BINANCE);
        if (binancePrice != null) {
            double binanceDeviation = Math.abs(binancePrice.getPrice() - vwap) / vwap;
            if (binanceDeviation > maxDeviation / 2) {
                warnings.add(String.format("Binance price deviates %.2f%% from VWAP",
                        binanceDeviation * 100));
            }
        }

        // Check for potential manipulation (sudden large deviations)
        ManipulationCheckResult manipulationCheck = checkForManipulation(prices);
        if (manipulationCheck.isPotentialManipulation()) {
            warnings.add("Potential price manipulation detected: " + manipulationCheck.reason);
        }

        boolean valid = errors.isEmpty() && !manipulationCheck.isPotentialManipulation();

        return ValidationResult.builder()
                .valid(valid)
                .consensusPrice(vwap)
                .simpleMean(mean)
                .coefficientOfVariation(cv)
                .outlierExchanges(outlierResult.outlierExchanges)
                .warnings(warnings)
                .errors(errors)
                .exchangeCount(prices.size())
                .validExchangeCount(prices.size() - outlierResult.outlierExchanges.size())
                .timestamp(Instant.now())
                .build();
    }

    /**
     * Validates that a price movement is consistent across exchanges.
     */
    public boolean validatePriceMovement(Map<Exchange, PriceData> prices) {
        ValidationResult result = validatePrices(prices);

        if (!result.isValid()) {
            logger.warn("Price validation failed: {}", result.getErrors());
            return false;
        }

        if (!result.getWarnings().isEmpty()) {
            logger.info("Price validation warnings: {}", result.getWarnings());
        }

        return result.getCoefficientOfVariation() <= maxDeviation;
    }

    /**
     * Detects outlier prices using simplified Grubbs' test (Z-score based).
     */
    private OutlierResult detectOutliers(Map<Exchange, PriceData> prices) {
        List<Double> priceList = prices.values().stream()
                .map(PriceData::getPrice)
                .collect(Collectors.toList());

        double mean = priceList.stream().mapToDouble(d -> d).average().orElse(0);
        double stdDev = calculateStdDev(priceList);

        Set<Exchange> outliers = new HashSet<>();

        if (stdDev > 0) {
            for (Map.Entry<Exchange, PriceData> entry : prices.entrySet()) {
                double zScore = Math.abs(entry.getValue().getPrice() - mean) / stdDev;
                if (zScore > OUTLIER_Z_SCORE_THRESHOLD) {
                    outliers.add(entry.getKey());
                    logger.debug("Outlier detected: {} with Z-score {:.2f}", entry.getKey(), zScore);
                }
            }
        }

        return new OutlierResult(outliers);
    }

    /**
     * Calculates Volume-Weighted Average Price (VWAP).
     */
    private double calculateVWAP(Map<Exchange, PriceData> prices, Set<Exchange> excludeExchanges) {
        double weightedPrice = 0;
        double totalVolume = 0;

        for (Map.Entry<Exchange, PriceData> entry : prices.entrySet()) {
            if (excludeExchanges.contains(entry.getKey())) {
                continue;
            }

            PriceData data = entry.getValue();
            double volume = data.getVolume() > 0 ? data.getVolume() : 1;
            weightedPrice += data.getPrice() * volume;
            totalVolume += volume;
        }

        return totalVolume > 0 ? weightedPrice / totalVolume : 0;
    }

    /**
     * Checks for potential price manipulation patterns.
     */
    private ManipulationCheckResult checkForManipulation(Map<Exchange, PriceData> prices) {
        // Check for single exchange with abnormally different price
        List<Double> priceList = prices.values().stream()
                .map(PriceData::getPrice)
                .collect(Collectors.toList());

        double mean = priceList.stream().mapToDouble(d -> d).average().orElse(0);

        for (Map.Entry<Exchange, PriceData> entry : prices.entrySet()) {
            double deviation = Math.abs(entry.getValue().getPrice() - mean) / mean;

            // If one exchange deviates more than 3x the max deviation, flag it
            if (deviation > maxDeviation * 3) {
                return new ManipulationCheckResult(true,
                        String.format("%s deviates %.2f%% from mean", entry.getKey(), deviation * 100));
            }
        }

        // Check for abnormally wide spreads
        for (PriceData data : prices.values()) {
            if (data.getSpreadPercent() > 1.0) { // > 1% spread
                return new ManipulationCheckResult(true,
                        String.format("Abnormal spread on %s: %.2f%%", data.getExchange(), data.getSpreadPercent()));
            }
        }

        return new ManipulationCheckResult(false, null);
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

    @Data
    @Builder
    public static class ValidationResult {
        private boolean valid;
        private double consensusPrice;
        private double simpleMean;
        private double coefficientOfVariation;
        private Set<Exchange> outlierExchanges;
        private List<String> warnings;
        private List<String> errors;
        private int exchangeCount;
        private int validExchangeCount;
        private Instant timestamp;

        public String getSummary() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Multi-Exchange Validation ===\n");
            sb.append(String.format("Valid: %s\n", valid ? "YES" : "NO"));
            sb.append(String.format("Consensus Price: %.2f\n", consensusPrice));
            sb.append(String.format("Simple Mean: %.2f\n", simpleMean));
            sb.append(String.format("CV: %.4f%%\n", coefficientOfVariation * 100));
            sb.append(String.format("Exchanges: %d/%d valid\n", validExchangeCount, exchangeCount));

            if (!outlierExchanges.isEmpty()) {
                sb.append(String.format("Outliers: %s\n", outlierExchanges));
            }
            if (!warnings.isEmpty()) {
                sb.append("Warnings:\n");
                warnings.forEach(w -> sb.append(String.format("  - %s\n", w)));
            }
            if (!errors.isEmpty()) {
                sb.append("Errors:\n");
                errors.forEach(e -> sb.append(String.format("  - %s\n", e)));
            }

            return sb.toString();
        }
    }

    private static class OutlierResult {
        final Set<Exchange> outlierExchanges;

        OutlierResult(Set<Exchange> outlierExchanges) {
            this.outlierExchanges = outlierExchanges;
        }

        boolean hasOutliers() {
            return !outlierExchanges.isEmpty();
        }
    }

    private static class ManipulationCheckResult {
        final boolean potentialManipulation;
        final String reason;

        ManipulationCheckResult(boolean potentialManipulation, String reason) {
            this.potentialManipulation = potentialManipulation;
            this.reason = reason;
        }

        boolean isPotentialManipulation() {
            return potentialManipulation;
        }
    }
}
