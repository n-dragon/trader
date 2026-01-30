package com.trader.patterns;

import com.trader.core.Candle;
import com.trader.indicators.AdaptiveIndicators;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Advanced pattern detection for candlestick patterns and divergences.
 */
public class AdvancedPatternDetector {
    private static final Logger logger = LoggerFactory.getLogger(AdvancedPatternDetector.class);

    private final AdaptiveIndicators indicators;

    public AdvancedPatternDetector() {
        this.indicators = new AdaptiveIndicators();
    }

    /**
     * Detects all patterns in the current candle data.
     */
    public PatternAnalysisResult analyzePatterns(List<Candle> candles) {
        if (candles.size() < 20) {
            return PatternAnalysisResult.builder()
                    .patternsDetected(false)
                    .build();
        }

        List<DetectedPattern> patterns = new ArrayList<>();

        // Detect candlestick patterns
        CandlePattern candlePattern = detectCandlePattern(candles);
        if (candlePattern != CandlePattern.NONE) {
            patterns.add(DetectedPattern.builder()
                    .name(candlePattern.name())
                    .type(candlePattern.getType())
                    .confidence(candlePattern.getConfidence())
                    .description(candlePattern.getDescription())
                    .build());
        }

        // Detect divergences
        DivergenceResult divergence = detectDivergences(candles);
        if (divergence.isDetected()) {
            patterns.add(DetectedPattern.builder()
                    .name(divergence.getType().name())
                    .type(divergence.isBullish() ? PatternType.BULLISH : PatternType.BEARISH)
                    .confidence(divergence.getConfidence())
                    .description(divergence.getDescription())
                    .build());
        }

        // Detect support/resistance levels
        SupportResistanceResult srLevels = detectSupportResistance(candles);

        // Detect trend
        TrendResult trend = detectTrend(candles);

        // Calculate overall signal
        double bullishScore = patterns.stream()
                .filter(p -> p.getType() == PatternType.BULLISH)
                .mapToDouble(DetectedPattern::getConfidence)
                .sum();

        double bearishScore = patterns.stream()
                .filter(p -> p.getType() == PatternType.BEARISH)
                .mapToDouble(DetectedPattern::getConfidence)
                .sum();

        return PatternAnalysisResult.builder()
                .patternsDetected(!patterns.isEmpty())
                .patterns(patterns)
                .candlePattern(candlePattern)
                .divergence(divergence)
                .supportResistance(srLevels)
                .trend(trend)
                .bullishScore(bullishScore)
                .bearishScore(bearishScore)
                .overallSignal(bullishScore > bearishScore + 0.3 ? PatternType.BULLISH :
                        bearishScore > bullishScore + 0.3 ? PatternType.BEARISH : PatternType.NEUTRAL)
                .build();
    }

    /**
     * Detects candlestick patterns.
     */
    public CandlePattern detectCandlePattern(List<Candle> candles) {
        if (candles.size() < 3) return CandlePattern.NONE;

        Candle c0 = candles.get(candles.size() - 1); // Latest candle
        Candle c1 = candles.get(candles.size() - 2);
        Candle c2 = candles.get(candles.size() - 3);

        // Single candle patterns
        if (isHammer(c0)) return CandlePattern.HAMMER;
        if (isInvertedHammer(c0)) return CandlePattern.INVERTED_HAMMER;
        if (isDoji(c0)) return CandlePattern.DOJI;
        if (isShootingStar(c0)) return CandlePattern.SHOOTING_STAR;

        // Two candle patterns
        if (isBullishEngulfing(c1, c0)) return CandlePattern.BULLISH_ENGULFING;
        if (isBearishEngulfing(c1, c0)) return CandlePattern.BEARISH_ENGULFING;
        if (isPiercing(c1, c0)) return CandlePattern.PIERCING;
        if (isDarkCloudCover(c1, c0)) return CandlePattern.DARK_CLOUD_COVER;

        // Three candle patterns
        if (isMorningStar(c2, c1, c0)) return CandlePattern.MORNING_STAR;
        if (isEveningStar(c2, c1, c0)) return CandlePattern.EVENING_STAR;
        if (isThreeWhiteSoldiers(candles)) return CandlePattern.THREE_WHITE_SOLDIERS;
        if (isThreeBlackCrows(candles)) return CandlePattern.THREE_BLACK_CROWS;

        return CandlePattern.NONE;
    }

    /**
     * Detects bullish or bearish divergences between price and RSI.
     */
    public DivergenceResult detectDivergences(List<Candle> candles) {
        int lookback = 20;
        if (candles.size() < lookback) {
            return DivergenceResult.builder().detected(false).build();
        }

        List<Candle> recentCandles = candles.subList(candles.size() - lookback, candles.size());

        // Calculate RSI values
        List<Double> rsiValues = new ArrayList<>();
        for (int i = 14; i <= recentCandles.size(); i++) {
            List<Candle> subset = candles.subList(0, candles.size() - lookback + i);
            rsiValues.add(indicators.calculateRSI(subset, 14));
        }

        if (rsiValues.size() < 2) {
            return DivergenceResult.builder().detected(false).build();
        }

        // Find price lows/highs in first and second half
        int halfPoint = recentCandles.size() / 2;

        double priceLow1 = recentCandles.subList(0, halfPoint).stream()
                .mapToDouble(Candle::getLow).min().orElse(0);
        double priceLow2 = recentCandles.subList(halfPoint, recentCandles.size()).stream()
                .mapToDouble(Candle::getLow).min().orElse(0);
        double priceHigh1 = recentCandles.subList(0, halfPoint).stream()
                .mapToDouble(Candle::getHigh).max().orElse(0);
        double priceHigh2 = recentCandles.subList(halfPoint, recentCandles.size()).stream()
                .mapToDouble(Candle::getHigh).max().orElse(0);

        int rsiHalf = rsiValues.size() / 2;
        double rsiLow1 = rsiValues.subList(0, rsiHalf).stream()
                .mapToDouble(d -> d).min().orElse(50);
        double rsiLow2 = rsiValues.subList(rsiHalf, rsiValues.size()).stream()
                .mapToDouble(d -> d).min().orElse(50);
        double rsiHigh1 = rsiValues.subList(0, rsiHalf).stream()
                .mapToDouble(d -> d).max().orElse(50);
        double rsiHigh2 = rsiValues.subList(rsiHalf, rsiValues.size()).stream()
                .mapToDouble(d -> d).max().orElse(50);

        // Bullish divergence: price makes lower low but RSI makes higher low
        if (priceLow2 < priceLow1 && rsiLow2 > rsiLow1) {
            double confidence = calculateDivergenceConfidence(priceLow1, priceLow2, rsiLow1, rsiLow2);
            return DivergenceResult.builder()
                    .detected(true)
                    .type(DivergenceType.BULLISH_REGULAR)
                    .bullish(true)
                    .confidence(confidence)
                    .description(String.format("Bullish divergence: price low %.2f -> %.2f, RSI low %.1f -> %.1f",
                            priceLow1, priceLow2, rsiLow1, rsiLow2))
                    .build();
        }

        // Bearish divergence: price makes higher high but RSI makes lower high
        if (priceHigh2 > priceHigh1 && rsiHigh2 < rsiHigh1) {
            double confidence = calculateDivergenceConfidence(priceHigh1, priceHigh2, rsiHigh1, rsiHigh2);
            return DivergenceResult.builder()
                    .detected(true)
                    .type(DivergenceType.BEARISH_REGULAR)
                    .bullish(false)
                    .confidence(confidence)
                    .description(String.format("Bearish divergence: price high %.2f -> %.2f, RSI high %.1f -> %.1f",
                            priceHigh1, priceHigh2, rsiHigh1, rsiHigh2))
                    .build();
        }

        return DivergenceResult.builder().detected(false).build();
    }

    /**
     * Detects support and resistance levels.
     */
    public SupportResistanceResult detectSupportResistance(List<Candle> candles) {
        if (candles.size() < 50) {
            return SupportResistanceResult.builder().build();
        }

        List<Candle> recentCandles = candles.subList(candles.size() - 50, candles.size());
        double currentPrice = candles.get(candles.size() - 1).getClose();

        // Find swing highs and lows
        List<Double> swingHighs = new ArrayList<>();
        List<Double> swingLows = new ArrayList<>();

        for (int i = 2; i < recentCandles.size() - 2; i++) {
            Candle c = recentCandles.get(i);
            boolean isSwingHigh = c.getHigh() > recentCandles.get(i - 1).getHigh() &&
                    c.getHigh() > recentCandles.get(i - 2).getHigh() &&
                    c.getHigh() > recentCandles.get(i + 1).getHigh() &&
                    c.getHigh() > recentCandles.get(i + 2).getHigh();

            boolean isSwingLow = c.getLow() < recentCandles.get(i - 1).getLow() &&
                    c.getLow() < recentCandles.get(i - 2).getLow() &&
                    c.getLow() < recentCandles.get(i + 1).getLow() &&
                    c.getLow() < recentCandles.get(i + 2).getLow();

            if (isSwingHigh) swingHighs.add(c.getHigh());
            if (isSwingLow) swingLows.add(c.getLow());
        }

        // Find nearest support and resistance
        double nearestResistance = swingHighs.stream()
                .filter(h -> h > currentPrice)
                .min(Double::compareTo)
                .orElse(currentPrice * 1.05);

        double nearestSupport = swingLows.stream()
                .filter(l -> l < currentPrice)
                .max(Double::compareTo)
                .orElse(currentPrice * 0.95);

        return SupportResistanceResult.builder()
                .nearestResistance(nearestResistance)
                .nearestSupport(nearestSupport)
                .resistanceDistance((nearestResistance - currentPrice) / currentPrice * 100)
                .supportDistance((currentPrice - nearestSupport) / currentPrice * 100)
                .swingHighs(swingHighs)
                .swingLows(swingLows)
                .build();
    }

    /**
     * Detects current trend.
     */
    public TrendResult detectTrend(List<Candle> candles) {
        if (candles.size() < 50) {
            return TrendResult.builder().trend(Trend.SIDEWAYS).build();
        }

        double sma20 = indicators.calculateSMA(candles, 20);
        double sma50 = indicators.calculateSMA(candles, 50);
        double currentPrice = candles.get(candles.size() - 1).getClose();

        // Calculate slope of SMA20
        List<Candle> subset20Ago = candles.subList(0, candles.size() - 20);
        double sma20Earlier = indicators.calculateSMA(subset20Ago, 20);
        double smaSlope = (sma20 - sma20Earlier) / sma20Earlier;

        Trend trend;
        double strength;

        if (currentPrice > sma20 && sma20 > sma50 && smaSlope > 0.01) {
            trend = Trend.STRONG_UPTREND;
            strength = Math.min(1.0, smaSlope * 10);
        } else if (currentPrice > sma20 && smaSlope > 0) {
            trend = Trend.UPTREND;
            strength = Math.min(0.7, smaSlope * 10);
        } else if (currentPrice < sma20 && sma20 < sma50 && smaSlope < -0.01) {
            trend = Trend.STRONG_DOWNTREND;
            strength = Math.min(1.0, Math.abs(smaSlope) * 10);
        } else if (currentPrice < sma20 && smaSlope < 0) {
            trend = Trend.DOWNTREND;
            strength = Math.min(0.7, Math.abs(smaSlope) * 10);
        } else {
            trend = Trend.SIDEWAYS;
            strength = 0.3;
        }

        return TrendResult.builder()
                .trend(trend)
                .strength(strength)
                .sma20(sma20)
                .sma50(sma50)
                .priceAboveSma20(currentPrice > sma20)
                .priceAboveSma50(currentPrice > sma50)
                .sma20AboveSma50(sma20 > sma50)
                .build();
    }

    // Candlestick pattern detection methods
    private boolean isHammer(Candle c) {
        double body = c.getBodySize();
        double lowerShadow = c.getLowerShadow();
        double upperShadow = c.getUpperShadow();
        double range = c.getRange();

        return range > 0 &&
                lowerShadow >= 2 * body &&
                upperShadow <= body * 0.3 &&
                body > 0;
    }

    private boolean isInvertedHammer(Candle c) {
        double body = c.getBodySize();
        double lowerShadow = c.getLowerShadow();
        double upperShadow = c.getUpperShadow();

        return upperShadow >= 2 * body &&
                lowerShadow <= body * 0.3 &&
                body > 0;
    }

    private boolean isDoji(Candle c) {
        double body = c.getBodySize();
        double range = c.getRange();
        return range > 0 && body / range < 0.1;
    }

    private boolean isShootingStar(Candle c) {
        return isInvertedHammer(c) && c.isBearish();
    }

    private boolean isBullishEngulfing(Candle prev, Candle curr) {
        return prev.isBearish() &&
                curr.isBullish() &&
                curr.getOpen() < prev.getClose() &&
                curr.getClose() > prev.getOpen();
    }

    private boolean isBearishEngulfing(Candle prev, Candle curr) {
        return prev.isBullish() &&
                curr.isBearish() &&
                curr.getOpen() > prev.getClose() &&
                curr.getClose() < prev.getOpen();
    }

    private boolean isPiercing(Candle prev, Candle curr) {
        double midPoint = (prev.getOpen() + prev.getClose()) / 2;
        return prev.isBearish() &&
                curr.isBullish() &&
                curr.getOpen() < prev.getLow() &&
                curr.getClose() > midPoint &&
                curr.getClose() < prev.getOpen();
    }

    private boolean isDarkCloudCover(Candle prev, Candle curr) {
        double midPoint = (prev.getOpen() + prev.getClose()) / 2;
        return prev.isBullish() &&
                curr.isBearish() &&
                curr.getOpen() > prev.getHigh() &&
                curr.getClose() < midPoint &&
                curr.getClose() > prev.getOpen();
    }

    private boolean isMorningStar(Candle c2, Candle c1, Candle c0) {
        return c2.isBearish() &&
                c2.getBodySize() > c1.getBodySize() * 2 &&
                isDoji(c1) &&
                c0.isBullish() &&
                c0.getClose() > (c2.getOpen() + c2.getClose()) / 2;
    }

    private boolean isEveningStar(Candle c2, Candle c1, Candle c0) {
        return c2.isBullish() &&
                c2.getBodySize() > c1.getBodySize() * 2 &&
                isDoji(c1) &&
                c0.isBearish() &&
                c0.getClose() < (c2.getOpen() + c2.getClose()) / 2;
    }

    private boolean isThreeWhiteSoldiers(List<Candle> candles) {
        if (candles.size() < 3) return false;
        Candle c0 = candles.get(candles.size() - 1);
        Candle c1 = candles.get(candles.size() - 2);
        Candle c2 = candles.get(candles.size() - 3);

        return c2.isBullish() && c1.isBullish() && c0.isBullish() &&
                c1.getClose() > c2.getClose() &&
                c0.getClose() > c1.getClose() &&
                c1.getOpen() > c2.getOpen() &&
                c0.getOpen() > c1.getOpen();
    }

    private boolean isThreeBlackCrows(List<Candle> candles) {
        if (candles.size() < 3) return false;
        Candle c0 = candles.get(candles.size() - 1);
        Candle c1 = candles.get(candles.size() - 2);
        Candle c2 = candles.get(candles.size() - 3);

        return c2.isBearish() && c1.isBearish() && c0.isBearish() &&
                c1.getClose() < c2.getClose() &&
                c0.getClose() < c1.getClose() &&
                c1.getOpen() < c2.getOpen() &&
                c0.getOpen() < c1.getOpen();
    }

    private double calculateDivergenceConfidence(double price1, double price2, double rsi1, double rsi2) {
        double priceChange = Math.abs(price2 - price1) / price1;
        double rsiChange = Math.abs(rsi2 - rsi1) / 100;
        return Math.min(1.0, (priceChange + rsiChange) * 5);
    }

    // Enums and data classes
    public enum CandlePattern {
        NONE(PatternType.NEUTRAL, 0, "No pattern"),
        HAMMER(PatternType.BULLISH, 0.7, "Hammer - potential reversal"),
        INVERTED_HAMMER(PatternType.BULLISH, 0.6, "Inverted Hammer - potential reversal"),
        DOJI(PatternType.NEUTRAL, 0.5, "Doji - indecision"),
        SHOOTING_STAR(PatternType.BEARISH, 0.7, "Shooting Star - potential reversal"),
        BULLISH_ENGULFING(PatternType.BULLISH, 0.8, "Bullish Engulfing - strong reversal"),
        BEARISH_ENGULFING(PatternType.BEARISH, 0.8, "Bearish Engulfing - strong reversal"),
        PIERCING(PatternType.BULLISH, 0.7, "Piercing Pattern - reversal"),
        DARK_CLOUD_COVER(PatternType.BEARISH, 0.7, "Dark Cloud Cover - reversal"),
        MORNING_STAR(PatternType.BULLISH, 0.85, "Morning Star - strong bullish reversal"),
        EVENING_STAR(PatternType.BEARISH, 0.85, "Evening Star - strong bearish reversal"),
        THREE_WHITE_SOLDIERS(PatternType.BULLISH, 0.9, "Three White Soldiers - strong bullish"),
        THREE_BLACK_CROWS(PatternType.BEARISH, 0.9, "Three Black Crows - strong bearish");

        private final PatternType type;
        private final double confidence;
        private final String description;

        CandlePattern(PatternType type, double confidence, String description) {
            this.type = type;
            this.confidence = confidence;
            this.description = description;
        }

        public PatternType getType() { return type; }
        public double getConfidence() { return confidence; }
        public String getDescription() { return description; }
    }

    public enum PatternType {
        BULLISH, BEARISH, NEUTRAL
    }

    public enum DivergenceType {
        BULLISH_REGULAR, BEARISH_REGULAR, BULLISH_HIDDEN, BEARISH_HIDDEN
    }

    public enum Trend {
        STRONG_UPTREND, UPTREND, SIDEWAYS, DOWNTREND, STRONG_DOWNTREND
    }

    @Data
    @Builder
    public static class DetectedPattern {
        private String name;
        private PatternType type;
        private double confidence;
        private String description;
    }

    @Data
    @Builder
    public static class DivergenceResult {
        private boolean detected;
        private DivergenceType type;
        private boolean bullish;
        private double confidence;
        private String description;
    }

    @Data
    @Builder
    public static class SupportResistanceResult {
        private double nearestResistance;
        private double nearestSupport;
        private double resistanceDistance;
        private double supportDistance;
        private List<Double> swingHighs;
        private List<Double> swingLows;
    }

    @Data
    @Builder
    public static class TrendResult {
        private Trend trend;
        private double strength;
        private double sma20;
        private double sma50;
        private boolean priceAboveSma20;
        private boolean priceAboveSma50;
        private boolean sma20AboveSma50;
    }

    @Data
    @Builder
    public static class PatternAnalysisResult {
        private boolean patternsDetected;
        private List<DetectedPattern> patterns;
        private CandlePattern candlePattern;
        private DivergenceResult divergence;
        private SupportResistanceResult supportResistance;
        private TrendResult trend;
        private double bullishScore;
        private double bearishScore;
        private PatternType overallSignal;
    }
}
