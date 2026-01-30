package com.trader.patterns;

import com.trader.core.Candle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pattern Detector Tests")
class PatternDetectorTest {

    private AdvancedPatternDetector detector;

    @BeforeEach
    void setUp() {
        detector = new AdvancedPatternDetector();
    }

    @Test
    @DisplayName("Should detect hammer pattern")
    void testDetectHammer() {
        // Create a hammer candle: small body, long lower shadow, small upper shadow
        Candle hammer = Candle.builder()
                .symbol("BTCUSDT")
                .open(100.0)
                .close(101.0)  // Small bullish body
                .high(101.5)   // Small upper shadow
                .low(95.0)     // Long lower shadow
                .openTime(Instant.now())
                .closeTime(Instant.now())
                .build();

        List<Candle> candles = new ArrayList<>();
        candles.add(createCandle(98, 99, 100, 97));
        candles.add(createCandle(99, 100, 101, 98));
        candles.add(hammer);

        AdvancedPatternDetector.CandlePattern pattern = detector.detectCandlePattern(candles);

        assertEquals(AdvancedPatternDetector.CandlePattern.HAMMER, pattern);
    }

    @Test
    @DisplayName("Should detect bullish engulfing pattern")
    void testDetectBullishEngulfing() {
        // Previous candle: bearish (close < open)
        Candle bearish = createCandle(105, 100, 106, 99);  // Open=105, Close=100, bearish

        // Current candle: bullish, engulfs previous
        Candle bullish = createCandle(99, 107, 108, 98);   // Open=99, Close=107, engulfs 100-105

        List<Candle> candles = new ArrayList<>();
        candles.add(createCandle(103, 104, 105, 102));
        candles.add(bearish);
        candles.add(bullish);

        AdvancedPatternDetector.CandlePattern pattern = detector.detectCandlePattern(candles);

        assertEquals(AdvancedPatternDetector.CandlePattern.BULLISH_ENGULFING, pattern);
    }

    @Test
    @DisplayName("Should detect doji pattern")
    void testDetectDoji() {
        // Doji: open and close are very close
        Candle doji = Candle.builder()
                .symbol("BTCUSDT")
                .open(100.0)
                .close(100.1)  // Almost same as open
                .high(102.0)
                .low(98.0)
                .openTime(Instant.now())
                .closeTime(Instant.now())
                .build();

        List<Candle> candles = new ArrayList<>();
        candles.add(createCandle(98, 99, 100, 97));
        candles.add(createCandle(99, 100, 101, 98));
        candles.add(doji);

        AdvancedPatternDetector.CandlePattern pattern = detector.detectCandlePattern(candles);

        assertEquals(AdvancedPatternDetector.CandlePattern.DOJI, pattern);
    }

    @Test
    @DisplayName("Should analyze patterns with sufficient data")
    void testAnalyzePatterns() {
        List<Candle> candles = createTrendingCandles(50, 100, 0.5); // Uptrending data

        AdvancedPatternDetector.PatternAnalysisResult result = detector.analyzePatterns(candles);

        assertNotNull(result);
        assertNotNull(result.getTrend());
    }

    @Test
    @DisplayName("Should detect uptrend")
    void testDetectUptrend() {
        List<Candle> candles = createTrendingCandles(60, 100, 1.0); // Strong uptrend

        AdvancedPatternDetector.TrendResult trend = detector.detectTrend(candles);

        assertNotNull(trend);
        assertTrue(trend.getTrend() == AdvancedPatternDetector.Trend.UPTREND ||
                trend.getTrend() == AdvancedPatternDetector.Trend.STRONG_UPTREND);
    }

    @Test
    @DisplayName("Should detect downtrend")
    void testDetectDowntrend() {
        List<Candle> candles = createTrendingCandles(60, 100, -1.0); // Strong downtrend

        AdvancedPatternDetector.TrendResult trend = detector.detectTrend(candles);

        assertNotNull(trend);
        assertTrue(trend.getTrend() == AdvancedPatternDetector.Trend.DOWNTREND ||
                trend.getTrend() == AdvancedPatternDetector.Trend.STRONG_DOWNTREND);
    }

    // Helper methods
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
}
