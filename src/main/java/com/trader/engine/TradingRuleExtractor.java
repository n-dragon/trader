package com.trader.engine;

import com.trader.core.Candle;
import com.trader.indicators.AdaptiveIndicators;
import com.trader.validation.BacktestValidator.CombinedRule;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Extracts trading rules from historical data using pattern analysis.
 * Identifies profitable patterns and creates rule combinations.
 */
public class TradingRuleExtractor {
    private static final Logger logger = LoggerFactory.getLogger(TradingRuleExtractor.class);

    private final AdaptiveIndicators indicators;
    private final int windowSize;
    private final double minConfidence;
    private final int minOccurrences;

    public TradingRuleExtractor() {
        this(10, 0.6, 5);
    }

    public TradingRuleExtractor(int windowSize, double minConfidence, int minOccurrences) {
        this.indicators = new AdaptiveIndicators();
        this.windowSize = windowSize > 0 ? windowSize : 10;
        this.minConfidence = minConfidence > 0 ? minConfidence : 0.6;
        this.minOccurrences = minOccurrences > 0 ? minOccurrences : 5;
    }

    /**
     * Extracts trading rules from historical candle data.
     */
    public List<CombinedRule> extractRules(List<Candle> history) {
        if (history.size() < windowSize + 50) {
            logger.warn("Insufficient data for rule extraction: {} candles", history.size());
            return Collections.emptyList();
        }

        List<RuleCandidate> candidates = new ArrayList<>();

        // Extract different types of rules
        candidates.addAll(extractConsecutivePatternRules(history));
        candidates.addAll(extractRSIRules(history));
        candidates.addAll(extractMACDRules(history));
        candidates.addAll(extractVolumeRules(history));
        candidates.addAll(extractBollingerBandRules(history));

        // Filter and rank rules
        List<CombinedRule> validRules = candidates.stream()
                .filter(c -> c.getConfidence() >= minConfidence)
                .filter(c -> c.getOccurrences() >= minOccurrences)
                .sorted(Comparator.comparingDouble(RuleCandidate::getConfidence).reversed())
                .limit(10) // Top 10 rules
                .map(this::convertToRule)
                .collect(Collectors.toList());

        logger.info("Extracted {} valid rules from {} candidates", validRules.size(), candidates.size());

        return validRules;
    }

    /**
     * Extracts rules based on consecutive price movements.
     */
    private List<RuleCandidate> extractConsecutivePatternRules(List<Candle> history) {
        List<RuleCandidate> rules = new ArrayList<>();

        // Rule: N consecutive increases followed by continuation
        for (int n = 2; n <= 5; n++) {
            int successCount = 0;
            int totalOccurrences = 0;

            for (int i = n; i < history.size() - 1; i++) {
                boolean consecutive = true;
                for (int j = 0; j < n; j++) {
                    if (history.get(i - j).getClose() <= history.get(i - j - 1).getClose()) {
                        consecutive = false;
                        break;
                    }
                }

                if (consecutive) {
                    totalOccurrences++;
                    // Check if next candle continues the trend
                    if (history.get(i + 1).getClose() > history.get(i).getClose()) {
                        successCount++;
                    }
                }
            }

            if (totalOccurrences > 0) {
                double confidence = (double) successCount / totalOccurrences;
                rules.add(RuleCandidate.builder()
                        .name(String.format("CONSECUTIVE_UP_%d", n))
                        .description(String.format("%d consecutive bullish candles", n))
                        .ruleType(RuleType.PATTERN)
                        .conditions(List.of(
                                CombinedRule.Condition.builder()
                                        .indicator("consecutive_up")
                                        .operator(">=")
                                        .value(n)
                                        .build()
                        ))
                        .confidence(confidence)
                        .occurrences(totalOccurrences)
                        .build());
            }
        }

        // Rule: N consecutive decreases followed by reversal
        for (int n = 2; n <= 5; n++) {
            int successCount = 0;
            int totalOccurrences = 0;

            for (int i = n; i < history.size() - 1; i++) {
                boolean consecutive = true;
                for (int j = 0; j < n; j++) {
                    if (history.get(i - j).getClose() >= history.get(i - j - 1).getClose()) {
                        consecutive = false;
                        break;
                    }
                }

                if (consecutive) {
                    totalOccurrences++;
                    // Check if next candle reverses
                    if (history.get(i + 1).getClose() > history.get(i).getClose()) {
                        successCount++;
                    }
                }
            }

            if (totalOccurrences > 0) {
                double confidence = (double) successCount / totalOccurrences;
                rules.add(RuleCandidate.builder()
                        .name(String.format("REVERSAL_AFTER_%d_DOWN", n))
                        .description(String.format("Reversal after %d consecutive bearish candles", n))
                        .ruleType(RuleType.REVERSAL)
                        .conditions(List.of(
                                CombinedRule.Condition.builder()
                                        .indicator("consecutive_down")
                                        .operator(">=")
                                        .value(n)
                                        .build()
                        ))
                        .confidence(confidence)
                        .occurrences(totalOccurrences)
                        .build());
            }
        }

        return rules;
    }

    /**
     * Extracts RSI-based rules.
     */
    private List<RuleCandidate> extractRSIRules(List<Candle> history) {
        List<RuleCandidate> rules = new ArrayList<>();

        // Test different RSI thresholds
        int[] oversoldLevels = {20, 25, 30};
        int[] overboughtLevels = {70, 75, 80};

        for (int oversold : oversoldLevels) {
            int successCount = 0;
            int totalOccurrences = 0;

            for (int i = 20; i < history.size() - windowSize; i++) {
                List<Candle> subset = history.subList(0, i + 1);
                double rsi = indicators.calculateRSI(subset, 14);

                if (rsi < oversold) {
                    totalOccurrences++;
                    // Check if price increased in next window
                    double maxFuturePrice = history.subList(i + 1, Math.min(i + 1 + windowSize, history.size()))
                            .stream()
                            .mapToDouble(Candle::getHigh)
                            .max()
                            .orElse(0);

                    if (maxFuturePrice > history.get(i).getClose() * 1.01) { // 1% increase
                        successCount++;
                    }
                }
            }

            if (totalOccurrences > 0) {
                double confidence = (double) successCount / totalOccurrences;
                rules.add(RuleCandidate.builder()
                        .name(String.format("RSI_OVERSOLD_%d", oversold))
                        .description(String.format("RSI below %d (oversold)", oversold))
                        .ruleType(RuleType.INDICATOR)
                        .conditions(List.of(
                                CombinedRule.Condition.builder()
                                        .indicator("RSI_14")
                                        .operator("<")
                                        .value(oversold)
                                        .build()
                        ))
                        .confidence(confidence)
                        .occurrences(totalOccurrences)
                        .build());
            }
        }

        for (int overbought : overboughtLevels) {
            int successCount = 0;
            int totalOccurrences = 0;

            for (int i = 20; i < history.size() - windowSize; i++) {
                List<Candle> subset = history.subList(0, i + 1);
                double rsi = indicators.calculateRSI(subset, 14);

                if (rsi > overbought) {
                    totalOccurrences++;
                    // Check if price decreased in next window
                    double minFuturePrice = history.subList(i + 1, Math.min(i + 1 + windowSize, history.size()))
                            .stream()
                            .mapToDouble(Candle::getLow)
                            .min()
                            .orElse(Double.MAX_VALUE);

                    if (minFuturePrice < history.get(i).getClose() * 0.99) { // 1% decrease
                        successCount++;
                    }
                }
            }

            if (totalOccurrences > 0) {
                double confidence = (double) successCount / totalOccurrences;
                rules.add(RuleCandidate.builder()
                        .name(String.format("RSI_OVERBOUGHT_%d", overbought))
                        .description(String.format("RSI above %d (overbought)", overbought))
                        .ruleType(RuleType.INDICATOR)
                        .conditions(List.of(
                                CombinedRule.Condition.builder()
                                        .indicator("RSI_14")
                                        .operator(">")
                                        .value(overbought)
                                        .build()
                        ))
                        .confidence(confidence)
                        .occurrences(totalOccurrences)
                        .build());
            }
        }

        return rules;
    }

    /**
     * Extracts MACD-based rules.
     */
    private List<RuleCandidate> extractMACDRules(List<Candle> history) {
        List<RuleCandidate> rules = new ArrayList<>();

        int bullishCrossoverSuccess = 0;
        int bullishCrossoverTotal = 0;
        int bearishCrossoverSuccess = 0;
        int bearishCrossoverTotal = 0;

        for (int i = 30; i < history.size() - windowSize; i++) {
            List<Candle> subset = history.subList(0, i + 1);
            List<Candle> prevSubset = history.subList(0, i);

            AdaptiveIndicators.MACDResult current = indicators.calculateMACD(subset);
            AdaptiveIndicators.MACDResult prev = indicators.calculateMACD(prevSubset);

            // Bullish crossover (MACD crosses above signal)
            if (prev.getMacdLine() <= prev.getSignalLine() && current.getMacdLine() > current.getSignalLine()) {
                bullishCrossoverTotal++;
                double futurePrice = history.get(Math.min(i + windowSize, history.size() - 1)).getClose();
                if (futurePrice > history.get(i).getClose()) {
                    bullishCrossoverSuccess++;
                }
            }

            // Bearish crossover (MACD crosses below signal)
            if (prev.getMacdLine() >= prev.getSignalLine() && current.getMacdLine() < current.getSignalLine()) {
                bearishCrossoverTotal++;
                double futurePrice = history.get(Math.min(i + windowSize, history.size() - 1)).getClose();
                if (futurePrice < history.get(i).getClose()) {
                    bearishCrossoverSuccess++;
                }
            }
        }

        if (bullishCrossoverTotal > 0) {
            double confidence = (double) bullishCrossoverSuccess / bullishCrossoverTotal;
            rules.add(RuleCandidate.builder()
                    .name("MACD_BULLISH_CROSSOVER")
                    .description("MACD bullish crossover")
                    .ruleType(RuleType.INDICATOR)
                    .conditions(List.of(
                            CombinedRule.Condition.builder()
                                    .indicator("MACD_crossover")
                                    .operator("==")
                                    .value(1) // 1 = bullish
                                    .build()
                    ))
                    .confidence(confidence)
                    .occurrences(bullishCrossoverTotal)
                    .build());
        }

        if (bearishCrossoverTotal > 0) {
            double confidence = (double) bearishCrossoverSuccess / bearishCrossoverTotal;
            rules.add(RuleCandidate.builder()
                    .name("MACD_BEARISH_CROSSOVER")
                    .description("MACD bearish crossover")
                    .ruleType(RuleType.INDICATOR)
                    .conditions(List.of(
                            CombinedRule.Condition.builder()
                                    .indicator("MACD_crossover")
                                    .operator("==")
                                    .value(-1) // -1 = bearish
                                    .build()
                    ))
                    .confidence(confidence)
                    .occurrences(bearishCrossoverTotal)
                    .build());
        }

        return rules;
    }

    /**
     * Extracts volume-based rules.
     */
    private List<RuleCandidate> extractVolumeRules(List<Candle> history) {
        List<RuleCandidate> rules = new ArrayList<>();

        // High volume breakout
        int successCount = 0;
        int totalOccurrences = 0;

        for (int i = 20; i < history.size() - windowSize; i++) {
            // Calculate average volume
            double avgVolume = history.subList(i - 20, i).stream()
                    .mapToDouble(Candle::getVolume)
                    .average()
                    .orElse(0);

            // Check for volume spike (2x average)
            if (history.get(i).getVolume() > avgVolume * 2) {
                totalOccurrences++;

                // Check if price moved significantly
                double futureMaxPrice = history.subList(i + 1, Math.min(i + 1 + windowSize, history.size()))
                        .stream()
                        .mapToDouble(Candle::getHigh)
                        .max()
                        .orElse(0);

                if (futureMaxPrice > history.get(i).getClose() * 1.02) { // 2% increase
                    successCount++;
                }
            }
        }

        if (totalOccurrences > 0) {
            double confidence = (double) successCount / totalOccurrences;
            rules.add(RuleCandidate.builder()
                    .name("VOLUME_SPIKE_BREAKOUT")
                    .description("Volume spike (2x average)")
                    .ruleType(RuleType.VOLUME)
                    .conditions(List.of(
                            CombinedRule.Condition.builder()
                                    .indicator("volume_ratio")
                                    .operator(">")
                                    .value(2.0)
                                    .build()
                    ))
                    .confidence(confidence)
                    .occurrences(totalOccurrences)
                    .build());
        }

        return rules;
    }

    /**
     * Extracts Bollinger Band rules.
     */
    private List<RuleCandidate> extractBollingerBandRules(List<Candle> history) {
        List<RuleCandidate> rules = new ArrayList<>();

        int lowerBandBounceSuccess = 0;
        int lowerBandBounceTotal = 0;
        int upperBandRejectSuccess = 0;
        int upperBandRejectTotal = 0;

        for (int i = 25; i < history.size() - windowSize; i++) {
            List<Candle> subset = history.subList(0, i + 1);
            AdaptiveIndicators.BollingerBandsResult bb = indicators.calculateBollingerBands(subset, 20, 2.0);

            double currentPrice = history.get(i).getClose();

            // Lower band bounce (potential buy)
            if (currentPrice <= bb.getLowerBand()) {
                lowerBandBounceTotal++;
                double futurePrice = history.get(Math.min(i + windowSize, history.size() - 1)).getClose();
                if (futurePrice > currentPrice * 1.01) {
                    lowerBandBounceSuccess++;
                }
            }

            // Upper band rejection (potential sell)
            if (currentPrice >= bb.getUpperBand()) {
                upperBandRejectTotal++;
                double futurePrice = history.get(Math.min(i + windowSize, history.size() - 1)).getClose();
                if (futurePrice < currentPrice * 0.99) {
                    upperBandRejectSuccess++;
                }
            }
        }

        if (lowerBandBounceTotal > 0) {
            double confidence = (double) lowerBandBounceSuccess / lowerBandBounceTotal;
            rules.add(RuleCandidate.builder()
                    .name("BB_LOWER_BAND_BOUNCE")
                    .description("Price at lower Bollinger Band")
                    .ruleType(RuleType.INDICATOR)
                    .conditions(List.of(
                            CombinedRule.Condition.builder()
                                    .indicator("BB_percentB")
                                    .operator("<=")
                                    .value(0)
                                    .build()
                    ))
                    .confidence(confidence)
                    .occurrences(lowerBandBounceTotal)
                    .build());
        }

        if (upperBandRejectTotal > 0) {
            double confidence = (double) upperBandRejectSuccess / upperBandRejectTotal;
            rules.add(RuleCandidate.builder()
                    .name("BB_UPPER_BAND_REJECT")
                    .description("Price at upper Bollinger Band")
                    .ruleType(RuleType.INDICATOR)
                    .conditions(List.of(
                            CombinedRule.Condition.builder()
                                    .indicator("BB_percentB")
                                    .operator(">=")
                                    .value(1)
                                    .build()
                    ))
                    .confidence(confidence)
                    .occurrences(upperBandRejectTotal)
                    .build());
        }

        return rules;
    }

    /**
     * Converts a rule candidate to a combined rule.
     */
    private CombinedRule convertToRule(RuleCandidate candidate) {
        return CombinedRule.builder()
                .name(candidate.getName())
                .description(candidate.getDescription())
                .conditions(candidate.getConditions())
                .confidence(candidate.getConfidence())
                .historicalWinRate(candidate.getConfidence())
                .build();
    }

    /**
     * Creates combined rules by merging individual rules.
     */
    public List<CombinedRule> createCombinedRules(List<CombinedRule> baseRules) {
        List<CombinedRule> combinedRules = new ArrayList<>(baseRules);

        // Create pairs of rules
        for (int i = 0; i < baseRules.size(); i++) {
            for (int j = i + 1; j < baseRules.size(); j++) {
                CombinedRule rule1 = baseRules.get(i);
                CombinedRule rule2 = baseRules.get(j);

                // Combine conditions
                List<CombinedRule.Condition> combined = new ArrayList<>();
                combined.addAll(rule1.getConditions());
                combined.addAll(rule2.getConditions());

                // Combined confidence (geometric mean)
                double combinedConfidence = Math.sqrt(rule1.getConfidence() * rule2.getConfidence());

                if (combinedConfidence >= minConfidence) {
                    combinedRules.add(CombinedRule.builder()
                            .name(rule1.getName() + "_AND_" + rule2.getName())
                            .description(rule1.getDescription() + " + " + rule2.getDescription())
                            .conditions(combined)
                            .confidence(combinedConfidence)
                            .historicalWinRate(combinedConfidence)
                            .build());
                }
            }
        }

        return combinedRules;
    }

    // Internal types
    public enum RuleType {
        PATTERN,
        INDICATOR,
        VOLUME,
        REVERSAL
    }

    @Data
    @Builder
    public static class RuleCandidate {
        private String name;
        private String description;
        private RuleType ruleType;
        private List<CombinedRule.Condition> conditions;
        private double confidence;
        private int occurrences;
    }
}
