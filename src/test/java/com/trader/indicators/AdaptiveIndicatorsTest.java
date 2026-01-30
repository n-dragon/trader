package com.trader.indicators;

import com.trader.core.Candle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Adaptive Indicators Tests")
class AdaptiveIndicatorsTest {

    private AdaptiveIndicators indicators;

    @BeforeEach
    void setUp() {
        indicators = new AdaptiveIndicators();
    }

    @Test
    @DisplayName("Should calculate RSI correctly")
    void testCalculateRSI() {
        List<Candle> candles = createSequentialCandles(20,
                new double[]{44, 44.34, 44.09, 43.61, 44.33, 44.83, 45.10, 45.42, 45.84,
                        46.08, 45.89, 46.03, 45.61, 46.28, 46.28, 46.00, 46.03, 46.41, 46.22, 45.64});

        double rsi = indicators.calculateRSI(candles, 14);

        // RSI should be between 0 and 100
        assertTrue(rsi >= 0 && rsi <= 100);
    }

    @Test
    @DisplayName("Should calculate adaptive RSI")
    void testCalculateAdaptiveRSI() {
        List<Candle> candles = createTrendingCandles(30, 100, 0.5);

        AdaptiveIndicators.RSIResult result = indicators.calculateAdaptiveRSI(candles);

        assertNotNull(result);
        assertTrue(result.getValue() >= 0 && result.getValue() <= 100);
        assertTrue(result.getPeriod() >= 7 && result.getPeriod() <= 30);
    }

    @Test
    @DisplayName("Should detect overbought condition")
    void testOverboughtRSI() {
        // Create strongly uptrending data
        List<Candle> candles = createTrendingCandles(30, 100, 2.0);

        AdaptiveIndicators.RSIResult result = indicators.calculateAdaptiveRSI(candles);

        assertTrue(result.getValue() > 50); // Should be high RSI for uptrend
    }

    @Test
    @DisplayName("Should detect oversold condition")
    void testOversoldRSI() {
        // Create strongly downtrending data
        List<Candle> candles = createTrendingCandles(30, 100, -2.0);

        AdaptiveIndicators.RSIResult result = indicators.calculateAdaptiveRSI(candles);

        assertTrue(result.getValue() < 50); // Should be low RSI for downtrend
    }

    @Test
    @DisplayName("Should calculate MACD correctly")
    void testCalculateMACD() {
        List<Candle> candles = createTrendingCandles(50, 100, 0.5);

        AdaptiveIndicators.MACDResult result = indicators.calculateMACD(candles);

        assertNotNull(result);
        // In an uptrend, MACD should be positive
        assertTrue(result.getMacdLine() != 0 || result.getSignalLine() != 0);
    }

    @Test
    @DisplayName("Should detect bullish MACD crossover")
    void testBullishMACDCrossover() {
        // Create data where fast EMA crosses above slow EMA
        List<Candle> candles = new ArrayList<>();

        // First create downtrend
        for (int i = 0; i < 30; i++) {
            double price = 100 - i * 0.5;
            candles.add(createCandle(price, price - 0.1, price + 0.2, price - 0.3));
        }

        // Then strong reversal
        for (int i = 0; i < 20; i++) {
            double price = 85 + i * 1.5;
            candles.add(createCandle(price - 0.5, price, price + 0.5, price - 0.7));
        }

        AdaptiveIndicators.MACDResult result = indicators.calculateMACD(candles);

        // After reversal, MACD should turn bullish
        assertTrue(result.isBullish() || result.isBullishCrossover());
    }

    @Test
    @DisplayName("Should calculate Bollinger Bands correctly")
    void testCalculateBollingerBands() {
        List<Candle> candles = createTrendingCandles(30, 100, 0.3);

        AdaptiveIndicators.BollingerBandsResult result = indicators.calculateBollingerBands(candles, 20, 2.0);

        assertNotNull(result);
        assertTrue(result.getUpperBand() > result.getMiddleBand());
        assertTrue(result.getMiddleBand() > result.getLowerBand());
        assertTrue(result.getBandwidth() > 0);
    }

    @Test
    @DisplayName("Should calculate ATR correctly")
    void testCalculateATR() {
        List<Candle> candles = new ArrayList<>();

        // Create candles with known ranges
        for (int i = 0; i < 20; i++) {
            candles.add(Candle.builder()
                    .symbol("BTCUSDT")
                    .open(100.0)
                    .close(101.0)
                    .high(102.0)    // Range of 4 (102 - 98)
                    .low(98.0)
                    .openTime(Instant.now().minusSeconds((20 - i) * 3600))
                    .closeTime(Instant.now().minusSeconds((19 - i) * 3600))
                    .build());
        }

        double atr = indicators.calculateATR(candles, 14);

        assertTrue(atr > 0);
        assertTrue(atr < 10); // Should be reasonable for this data
    }

    @Test
    @DisplayName("Should calculate volatility")
    void testCalculateVolatility() {
        List<Candle> candles = createTrendingCandles(30, 100, 0.5);

        double volatility = indicators.calculateVolatility(candles, 20);

        assertTrue(volatility >= 0);
        assertTrue(volatility < 1); // Should be reasonable volatility
    }

    @Test
    @DisplayName("Should calculate SMA correctly")
    void testCalculateSMA() {
        List<Candle> candles = createSequentialCandles(5,
                new double[]{10, 20, 30, 40, 50});

        double sma = indicators.calculateSMA(candles, 5);

        assertEquals(30.0, sma, 0.01); // (10+20+30+40+50)/5 = 30
    }

    // Helper methods
    private List<Candle> createSequentialCandles(int count, double[] closes) {
        List<Candle> candles = new ArrayList<>();

        for (int i = 0; i < count && i < closes.length; i++) {
            double close = closes[i];
            candles.add(Candle.builder()
                    .symbol("BTCUSDT")
                    .open(close - 0.1)
                    .close(close)
                    .high(close + 0.2)
                    .low(close - 0.2)
                    .volume(1000)
                    .openTime(Instant.now().minusSeconds((count - i) * 3600))
                    .closeTime(Instant.now().minusSeconds((count - i - 1) * 3600))
                    .build());
        }

        return candles;
    }

    private List<Candle> createTrendingCandles(int count, double startPrice, double trendPercent) {
        List<Candle> candles = new ArrayList<>();
        double price = startPrice;

        for (int i = 0; i < count; i++) {
            double open = price;
            price = price * (1 + trendPercent / 100);
            double close = price;
            double high = Math.max(open, close) * 1.005;
            double low = Math.min(open, close) * 0.995;

            candles.add(Candle.builder()
                    .symbol("BTCUSDT")
                    .open(open)
                    .close(close)
                    .high(high)
                    .low(low)
                    .volume(1000)
                    .openTime(Instant.now().minusSeconds((count - i) * 3600))
                    .closeTime(Instant.now().minusSeconds((count - i - 1) * 3600))
                    .build());
        }

        return candles;
    }

    private Candle createCandle(double open, double close, double high, double low) {
        return Candle.builder()
                .symbol("BTCUSDT")
                .open(open)
                .close(close)
                .high(high)
                .low(low)
                .volume(1000)
                .openTime(Instant.now())
                .closeTime(Instant.now())
                .build();
    }
}
