package com.trader.indicators;

import com.trader.core.Candle;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides adaptive technical indicators that automatically adjust parameters
 * based on market conditions (volatility, trend strength, etc.).
 */
public class AdaptiveIndicators {
    private static final Logger logger = LoggerFactory.getLogger(AdaptiveIndicators.class);

    // Default RSI parameters
    private static final int DEFAULT_RSI_PERIOD = 14;
    private static final int MIN_RSI_PERIOD = 7;
    private static final int MAX_RSI_PERIOD = 30;

    // Default MACD parameters (optimized for crypto)
    private static final int CRYPTO_MACD_FAST = 8;
    private static final int CRYPTO_MACD_SLOW = 21;
    private static final int CRYPTO_MACD_SIGNAL = 5;

    // Standard MACD parameters
    private static final int STD_MACD_FAST = 12;
    private static final int STD_MACD_SLOW = 26;
    private static final int STD_MACD_SIGNAL = 9;

    // Volatility threshold for adaptive adjustments
    private static final double HIGH_VOLATILITY_THRESHOLD = 0.05; // 5%

    /**
     * Calculates RSI with the standard period.
     */
    public double calculateRSI(List<Candle> candles) {
        return calculateRSI(candles, DEFAULT_RSI_PERIOD);
    }

    /**
     * Calculates RSI with a specified period.
     */
    public double calculateRSI(List<Candle> candles, int period) {
        if (candles.size() < period + 1) {
            return 50; // Neutral value if insufficient data
        }

        double avgGain = 0;
        double avgLoss = 0;

        // Calculate initial average gain/loss
        for (int i = 1; i <= period; i++) {
            double change = candles.get(i).getClose() - candles.get(i - 1).getClose();
            if (change > 0) {
                avgGain += change;
            } else {
                avgLoss += Math.abs(change);
            }
        }

        avgGain /= period;
        avgLoss /= period;

        // Calculate smoothed averages for remaining data
        for (int i = period + 1; i < candles.size(); i++) {
            double change = candles.get(i).getClose() - candles.get(i - 1).getClose();
            if (change > 0) {
                avgGain = (avgGain * (period - 1) + change) / period;
                avgLoss = (avgLoss * (period - 1)) / period;
            } else {
                avgGain = (avgGain * (period - 1)) / period;
                avgLoss = (avgLoss * (period - 1) + Math.abs(change)) / period;
            }
        }

        if (avgLoss == 0) {
            return 100;
        }

        double rs = avgGain / avgLoss;
        return 100 - (100 / (1 + rs));
    }

    /**
     * Calculates RSI with adaptive period based on volatility.
     * Uses shorter periods in high volatility, longer in low volatility.
     */
    public RSIResult calculateAdaptiveRSI(List<Candle> candles) {
        double volatility = calculateVolatility(candles, 20);

        // Adjust period based on volatility
        int period;
        if (volatility > HIGH_VOLATILITY_THRESHOLD) {
            period = MIN_RSI_PERIOD; // More reactive in volatile markets
        } else if (volatility < HIGH_VOLATILITY_THRESHOLD / 2) {
            period = MAX_RSI_PERIOD; // Smoother in calm markets
        } else {
            period = DEFAULT_RSI_PERIOD;
        }

        double rsi = calculateRSI(candles, period);

        return RSIResult.builder()
                .value(rsi)
                .period(period)
                .volatility(volatility)
                .isOverbought(rsi > 70)
                .isOversold(rsi < 30)
                .build();
    }

    /**
     * Optimizes RSI period by backtesting different periods.
     */
    public OptimizedParams optimizeRSIPeriod(List<Candle> history) {
        int bestPeriod = DEFAULT_RSI_PERIOD;
        double bestScore = 0;

        for (int period = MIN_RSI_PERIOD; period <= MAX_RSI_PERIOD; period++) {
            double score = backtestRSI(history, period);
            if (score > bestScore) {
                bestScore = score;
                bestPeriod = period;
            }
        }

        logger.debug("Optimized RSI period: {} (score: {:.4f})", bestPeriod, bestScore);

        return OptimizedParams.builder()
                .parameter("period")
                .value(bestPeriod)
                .score(bestScore)
                .build();
    }

    /**
     * Simple backtest for RSI to evaluate a period.
     * Returns a score based on how well the RSI predicts price reversals.
     */
    private double backtestRSI(List<Candle> candles, int period) {
        int correctPredictions = 0;
        int totalSignals = 0;
        int lookAhead = 5; // Check reversal within 5 candles

        for (int i = period + 1; i < candles.size() - lookAhead; i++) {
            List<Candle> subset = candles.subList(0, i + 1);
            double rsi = calculateRSI(subset, period);

            // Check for oversold signal (buy signal)
            if (rsi < 30) {
                totalSignals++;
                // Check if price increased in next 5 candles
                double futureMaxPrice = candles.subList(i + 1, i + 1 + lookAhead).stream()
                        .mapToDouble(Candle::getHigh)
                        .max()
                        .orElse(0);
                if (futureMaxPrice > candles.get(i).getClose()) {
                    correctPredictions++;
                }
            }

            // Check for overbought signal (sell signal)
            if (rsi > 70) {
                totalSignals++;
                // Check if price decreased in next 5 candles
                double futureMinPrice = candles.subList(i + 1, i + 1 + lookAhead).stream()
                        .mapToDouble(Candle::getLow)
                        .min()
                        .orElse(Double.MAX_VALUE);
                if (futureMinPrice < candles.get(i).getClose()) {
                    correctPredictions++;
                }
            }
        }

        return totalSignals > 0 ? (double) correctPredictions / totalSignals : 0;
    }

    /**
     * Calculates MACD with standard parameters.
     */
    public MACDResult calculateMACD(List<Candle> candles) {
        return calculateMACD(candles, STD_MACD_FAST, STD_MACD_SLOW, STD_MACD_SIGNAL);
    }

    /**
     * Calculates MACD optimized for cryptocurrency (more reactive).
     */
    public MACDResult calculateCryptoMACD(List<Candle> candles) {
        return calculateMACD(candles, CRYPTO_MACD_FAST, CRYPTO_MACD_SLOW, CRYPTO_MACD_SIGNAL);
    }

    /**
     * Calculates MACD with specified parameters.
     */
    public MACDResult calculateMACD(List<Candle> candles, int fastPeriod, int slowPeriod, int signalPeriod) {
        if (candles.size() < slowPeriod + signalPeriod) {
            return MACDResult.builder()
                    .macdLine(0)
                    .signalLine(0)
                    .histogram(0)
                    .build();
        }

        List<Double> prices = candles.stream()
                .map(Candle::getClose)
                .toList();

        // Calculate EMAs
        List<Double> fastEMA = calculateEMA(prices, fastPeriod);
        List<Double> slowEMA = calculateEMA(prices, slowPeriod);

        // Calculate MACD line
        List<Double> macdLine = new ArrayList<>();
        int startIdx = slowPeriod - 1;
        for (int i = startIdx; i < prices.size(); i++) {
            int fastIdx = i - (slowPeriod - fastPeriod);
            if (fastIdx >= 0 && fastIdx < fastEMA.size()) {
                macdLine.add(fastEMA.get(fastIdx) - slowEMA.get(i - startIdx));
            }
        }

        if (macdLine.isEmpty()) {
            return MACDResult.builder().build();
        }

        // Calculate signal line (EMA of MACD line)
        List<Double> signalLine = calculateEMA(macdLine, signalPeriod);

        if (signalLine.isEmpty()) {
            return MACDResult.builder()
                    .macdLine(macdLine.get(macdLine.size() - 1))
                    .build();
        }

        double currentMACD = macdLine.get(macdLine.size() - 1);
        double currentSignal = signalLine.get(signalLine.size() - 1);
        double histogram = currentMACD - currentSignal;

        // Determine crossover
        boolean bullishCrossover = false;
        boolean bearishCrossover = false;

        if (macdLine.size() >= 2 && signalLine.size() >= 2) {
            double prevMACD = macdLine.get(macdLine.size() - 2);
            double prevSignal = signalLine.get(signalLine.size() - 2);

            if (prevMACD <= prevSignal && currentMACD > currentSignal) {
                bullishCrossover = true;
            } else if (prevMACD >= prevSignal && currentMACD < currentSignal) {
                bearishCrossover = true;
            }
        }

        return MACDResult.builder()
                .macdLine(currentMACD)
                .signalLine(currentSignal)
                .histogram(histogram)
                .bullishCrossover(bullishCrossover)
                .bearishCrossover(bearishCrossover)
                .fastPeriod(fastPeriod)
                .slowPeriod(slowPeriod)
                .signalPeriod(signalPeriod)
                .build();
    }

    /**
     * Calculates Exponential Moving Average (EMA).
     */
    public List<Double> calculateEMA(List<Double> prices, int period) {
        List<Double> ema = new ArrayList<>();

        if (prices.size() < period) {
            return ema;
        }

        // Start with SMA for the first EMA value
        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += prices.get(i);
        }
        double multiplier = 2.0 / (period + 1);
        double prevEMA = sum / period;
        ema.add(prevEMA);

        // Calculate EMA for remaining prices
        for (int i = period; i < prices.size(); i++) {
            double currentEMA = (prices.get(i) - prevEMA) * multiplier + prevEMA;
            ema.add(currentEMA);
            prevEMA = currentEMA;
        }

        return ema;
    }

    /**
     * Calculates Simple Moving Average (SMA).
     */
    public double calculateSMA(List<Candle> candles, int period) {
        if (candles.size() < period) {
            return 0;
        }

        return candles.subList(candles.size() - period, candles.size()).stream()
                .mapToDouble(Candle::getClose)
                .average()
                .orElse(0);
    }

    /**
     * Calculates Bollinger Bands.
     */
    public BollingerBandsResult calculateBollingerBands(List<Candle> candles, int period, double stdDevMultiplier) {
        if (candles.size() < period) {
            return BollingerBandsResult.builder().build();
        }

        List<Double> closePrices = candles.subList(candles.size() - period, candles.size()).stream()
                .map(Candle::getClose)
                .toList();

        double sma = closePrices.stream().mapToDouble(d -> d).average().orElse(0);
        double stdDev = calculateStdDev(closePrices);

        double upperBand = sma + (stdDevMultiplier * stdDev);
        double lowerBand = sma - (stdDevMultiplier * stdDev);
        double currentPrice = candles.get(candles.size() - 1).getClose();

        // Calculate %B (position within bands)
        double percentB = (currentPrice - lowerBand) / (upperBand - lowerBand);

        // Calculate bandwidth
        double bandwidth = (upperBand - lowerBand) / sma;

        return BollingerBandsResult.builder()
                .upperBand(upperBand)
                .middleBand(sma)
                .lowerBand(lowerBand)
                .percentB(percentB)
                .bandwidth(bandwidth)
                .build();
    }

    /**
     * Calculates Average True Range (ATR).
     */
    public double calculateATR(List<Candle> candles, int period) {
        if (candles.size() < period + 1) {
            return 0;
        }

        List<Double> trueRanges = new ArrayList<>();

        for (int i = 1; i < candles.size(); i++) {
            Candle current = candles.get(i);
            Candle previous = candles.get(i - 1);

            double tr = Math.max(
                    current.getHigh() - current.getLow(),
                    Math.max(
                            Math.abs(current.getHigh() - previous.getClose()),
                            Math.abs(current.getLow() - previous.getClose())
                    )
            );
            trueRanges.add(tr);
        }

        // Calculate ATR as EMA of true ranges
        List<Double> atrValues = calculateEMA(trueRanges, period);
        return atrValues.isEmpty() ? 0 : atrValues.get(atrValues.size() - 1);
    }

    /**
     * Calculates volatility (standard deviation of returns).
     */
    public double calculateVolatility(List<Candle> candles, int period) {
        if (candles.size() < period + 1) {
            return 0;
        }

        List<Double> returns = new ArrayList<>();
        List<Candle> recentCandles = candles.subList(candles.size() - period - 1, candles.size());

        for (int i = 1; i < recentCandles.size(); i++) {
            double prevClose = recentCandles.get(i - 1).getClose();
            double currClose = recentCandles.get(i).getClose();
            if (prevClose > 0) {
                returns.add((currClose - prevClose) / prevClose);
            }
        }

        return calculateStdDev(returns);
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

    // Result classes
    @Data
    @Builder
    public static class RSIResult {
        private double value;
        private int period;
        private double volatility;
        private boolean isOverbought;
        private boolean isOversold;
    }

    @Data
    @Builder
    public static class MACDResult {
        private double macdLine;
        private double signalLine;
        private double histogram;
        private boolean bullishCrossover;
        private boolean bearishCrossover;
        private int fastPeriod;
        private int slowPeriod;
        private int signalPeriod;

        public boolean isBullish() {
            return macdLine > signalLine;
        }

        public boolean isBearish() {
            return macdLine < signalLine;
        }
    }

    @Data
    @Builder
    public static class BollingerBandsResult {
        private double upperBand;
        private double middleBand;
        private double lowerBand;
        private double percentB;
        private double bandwidth;

        public boolean isOverbought() {
            return percentB > 1.0;
        }

        public boolean isOversold() {
            return percentB < 0;
        }
    }

    @Data
    @Builder
    public static class OptimizedParams {
        private String parameter;
        private int value;
        private double score;
    }
}
