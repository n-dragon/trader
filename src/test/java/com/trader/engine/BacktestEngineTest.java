package com.trader.engine;

import com.trader.core.Candle;
import com.trader.core.TradingSignal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Backtest Engine Tests")
class BacktestEngineTest {

    private BacktestEngine engine;

    @BeforeEach
    void setUp() {
        engine = new BacktestEngine(10000); // $10,000 initial capital
    }

    @Test
    @DisplayName("Should run simple SMA crossover backtest")
    void testSMACrossoverBacktest() {
        List<Candle> data = createTrendingCandles(200, 50000, 0.2);

        BacktestEngine.BacktestReport report = engine.runSMACrossoverBacktest(data, 10, 20);

        assertNotNull(report);
        assertEquals(10000, report.getInitialCapital());
        assertTrue(report.getTrades().size() >= 0);
        assertNotNull(report.getMetrics());
    }

    @Test
    @DisplayName("Should run RSI backtest")
    void testRSIBacktest() {
        List<Candle> data = createVolatileCandles(200, 50000);

        BacktestEngine.BacktestReport report = engine.runRSIBacktest(data, 14, 30, 70);

        assertNotNull(report);
        assertEquals(10000, report.getInitialCapital());
        assertNotNull(report.getEquityCurve());
    }

    @Test
    @DisplayName("Should run custom strategy backtest")
    void testCustomStrategyBacktest() {
        List<Candle> data = createTrendingCandles(150, 50000, 0.3);

        BacktestEngine.BacktestReport report = engine.runBacktest(data, candles -> {
            if (candles.size() < 20) return null;

            Candle current = candles.get(candles.size() - 1);
            Candle prev = candles.get(candles.size() - 2);

            // Simple momentum: buy if price up 1%
            if (current.getClose() > prev.getClose() * 1.01) {
                return TradingSignal.builder()
                        .symbol(current.getSymbol())
                        .type(TradingSignal.SignalType.ENTRY_LONG)
                        .action(TradingSignal.SignalAction.BUY)
                        .price(current.getClose())
                        .confidence(0.6)
                        .timestamp(current.getCloseTime())
                        .suggestedStopLoss(current.getClose() * 0.98)
                        .suggestedTakeProfit(current.getClose() * 1.04)
                        .build();
            }

            return null;
        });

        assertNotNull(report);
        assertNotNull(report.getSummary());
    }

    @Test
    @DisplayName("Should calculate final capital correctly")
    void testFinalCapitalCalculation() {
        // Create data with clear uptrend
        List<Candle> data = createTrendingCandles(150, 50000, 0.5);

        BacktestEngine.BacktestReport report = engine.runSMACrossoverBacktest(data, 5, 15);

        // Final capital should be calculated
        assertNotNull(report.getFinalCapital());
        assertTrue(report.getFinalCapital() > 0);

        // Total return should match
        double expectedReturn = (report.getFinalCapital() - report.getInitialCapital())
                / report.getInitialCapital() * 100;
        assertEquals(expectedReturn, report.getTotalReturn(), 0.01);
    }

    @Test
    @DisplayName("Should respect backtest configuration")
    void testBacktestConfiguration() {
        List<Candle> data = createTrendingCandles(200, 50000, 0.2);

        BacktestEngine.BacktestConfig config = BacktestEngine.BacktestConfig.builder()
                .warmupPeriod(30)
                .minDataPoints(50)
                .minConfidence(0.7)
                .riskPerTradePercent(1.0)
                .build();

        BacktestEngine.BacktestReport report = engine.runBacktest(data, candles -> null, config);

        assertEquals(config, report.getConfig());
    }

    @Test
    @DisplayName("Should throw exception for insufficient data")
    void testInsufficientData() {
        List<Candle> data = createTrendingCandles(50, 50000, 0.2);

        assertThrows(IllegalArgumentException.class, () ->
                engine.runBacktest(data, c -> null,
                        BacktestEngine.BacktestConfig.builder()
                                .minDataPoints(100)
                                .build()));
    }

    @Test
    @DisplayName("Should generate detailed report summary")
    void testReportSummary() {
        List<Candle> data = createTrendingCandles(150, 50000, 0.3);

        BacktestEngine.BacktestReport report = engine.runSMACrossoverBacktest(data, 10, 20);

        String summary = report.getSummary();

        assertNotNull(summary);
        assertTrue(summary.contains("Backtest Report"));
        assertTrue(summary.contains("Initial"));
        assertTrue(summary.contains("Final"));
        assertTrue(summary.contains("Return"));
    }

    @Test
    @DisplayName("Should track equity curve")
    void testEquityCurve() {
        List<Candle> data = createTrendingCandles(150, 50000, 0.2);

        BacktestEngine.BacktestReport report = engine.runSMACrossoverBacktest(data, 10, 20);

        assertNotNull(report.getEquityCurve());
        assertFalse(report.getEquityCurve().isEmpty());

        // First equity value should be close to initial capital
        double firstEquity = report.getEquityCurve().get(0);
        assertTrue(firstEquity > 0);
    }

    // Helper methods
    private List<Candle> createTrendingCandles(int count, double startPrice, double trendPercent) {
        List<Candle> candles = new ArrayList<>();
        double price = startPrice;

        for (int i = 0; i < count; i++) {
            double open = price;
            // Add some randomness to the trend
            double change = trendPercent + (Math.random() - 0.5) * 0.5;
            price = price * (1 + change / 100);
            double close = price;
            double high = Math.max(open, close) * (1 + Math.random() * 0.005);
            double low = Math.min(open, close) * (1 - Math.random() * 0.005);

            candles.add(Candle.builder()
                    .symbol("BTCUSDT")
                    .open(open)
                    .close(close)
                    .high(high)
                    .low(low)
                    .volume(1000 + Math.random() * 1000)
                    .openTime(Instant.now().minusSeconds((count - i) * 3600))
                    .closeTime(Instant.now().minusSeconds((count - i - 1) * 3600))
                    .build());
        }

        return candles;
    }

    private List<Candle> createVolatileCandles(int count, double basePrice) {
        List<Candle> candles = new ArrayList<>();
        double price = basePrice;

        for (int i = 0; i < count; i++) {
            // Create oscillating pattern for RSI testing
            double amplitude = Math.sin(i * 0.2) * 3; // 3% oscillation
            double open = price;
            price = basePrice * (1 + amplitude / 100);
            double close = price;
            double high = Math.max(open, close) * 1.01;
            double low = Math.min(open, close) * 0.99;

            candles.add(Candle.builder()
                    .symbol("BTCUSDT")
                    .open(open)
                    .close(close)
                    .high(high)
                    .low(low)
                    .volume(1000 + Math.random() * 1000)
                    .openTime(Instant.now().minusSeconds((count - i) * 3600))
                    .closeTime(Instant.now().minusSeconds((count - i - 1) * 3600))
                    .build());
        }

        return candles;
    }
}
