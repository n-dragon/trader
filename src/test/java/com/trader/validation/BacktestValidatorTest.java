package com.trader.validation;

import com.trader.core.Candle;
import com.trader.core.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Backtest Validator Tests")
class BacktestValidatorTest {

    private BacktestValidator validator;

    @BeforeEach
    void setUp() {
        // Smaller parameters for testing
        validator = new BacktestValidator(100, 50, 25, 100);
    }

    @Test
    @DisplayName("Should validate Monte Carlo simulation with positive trades")
    void testMonteCarloWithProfitableTrades() {
        List<Trade> trades = createProfitableTrades(50);

        BacktestValidator.MonteCarloReport report = validator.monteCarloSimulation(trades);

        assertNotNull(report);
        assertEquals(100, report.getIterations());
        assertTrue(report.getMedian() > 0, "Median should be positive for profitable trades");
        assertTrue(report.getBestCase95Percent() > report.getMedian(), "95th percentile should be > median");
        assertTrue(report.getMedian() > report.getWorstCase5Percent(), "Median should be > 5th percentile");
    }

    @Test
    @DisplayName("Should calculate probability of loss correctly")
    void testMonteCarloLossProbability() {
        // Mix of winning and losing trades
        List<Trade> trades = createMixedTrades(100);

        BacktestValidator.MonteCarloReport report = validator.monteCarloSimulation(trades);

        assertTrue(report.getProbabilityOfLoss() >= 0 && report.getProbabilityOfLoss() <= 1,
                "Probability of loss should be between 0 and 1");
    }

    @Test
    @DisplayName("Should throw exception for empty trades in Monte Carlo")
    void testMonteCarloWithEmptyTrades() {
        assertThrows(IllegalArgumentException.class, () ->
                validator.monteCarloSimulation(new ArrayList<>()));
    }

    @Test
    @DisplayName("Should perform walk-forward analysis")
    void testWalkForwardAnalysis() {
        List<Candle> data = createTestCandles(200);

        BacktestValidator.ValidationReport report = validator.walkForwardAnalysis(
                data,
                // Simple rule extractor that always returns same rule
                candles -> List.of(
                        BacktestValidator.CombinedRule.builder()
                                .name("TestRule")
                                .description("Test rule")
                                .confidence(0.6)
                                .build()
                ),
                // Simple backtester
                input -> BacktestValidator.TestResult.builder()
                        .windowNumber(input.getWindowNumber())
                        .tradeCount(10)
                        .returnPercent(5.0)
                        .winRate(0.6)
                        .profitFactor(1.5)
                        .maxDrawdown(0.05)
                        .sharpeRatio(1.5)
                        .build()
        );

        assertNotNull(report);
        assertTrue(report.getWindowCount() > 0, "Should have at least one window");
        assertTrue(report.isValid(), "Should be valid with positive returns");
    }

    @Test
    @DisplayName("Should detect low win rate in validation")
    void testValidationDetectsLowWinRate() {
        List<Candle> data = createTestCandles(200);

        BacktestValidator.ValidationReport report = validator.walkForwardAnalysis(
                data,
                candles -> List.of(),
                input -> BacktestValidator.TestResult.builder()
                        .windowNumber(input.getWindowNumber())
                        .tradeCount(10)
                        .returnPercent(2.0)
                        .winRate(0.3) // Low win rate
                        .profitFactor(0.8)
                        .maxDrawdown(0.1)
                        .sharpeRatio(0.5)
                        .build()
        );

        assertFalse(report.isValid(), "Should be invalid with low win rate");
        assertTrue(report.getFailureReasons().stream()
                        .anyMatch(r -> r.toLowerCase().contains("win rate")),
                "Should include win rate in failure reasons");
    }

    @Test
    @DisplayName("Should require minimum data for walk-forward analysis")
    void testWalkForwardRequiresMinimumData() {
        List<Candle> insufficientData = createTestCandles(50);

        assertThrows(IllegalArgumentException.class, () ->
                validator.walkForwardAnalysis(
                        insufficientData,
                        c -> List.of(),
                        i -> null
                ));
    }

    @Test
    @DisplayName("Should generate Monte Carlo summary")
    void testMonteCarloReportSummary() {
        List<Trade> trades = createProfitableTrades(30);
        BacktestValidator.MonteCarloReport report = validator.monteCarloSimulation(trades);

        String summary = report.getSummary();

        assertNotNull(summary);
        assertTrue(summary.contains("Monte Carlo"));
        assertTrue(summary.contains("Iterations"));
        assertTrue(summary.contains("Median"));
    }

    // Helper methods
    private List<Trade> createProfitableTrades(int count) {
        List<Trade> trades = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            boolean win = i % 10 < 7; // 70% win rate
            double profit = win ? 100 + Math.random() * 50 : -(50 + Math.random() * 30);

            trades.add(Trade.builder()
                    .id("trade-" + i)
                    .symbol("BTCUSDT")
                    .type(Trade.TradeType.LONG)
                    .entryPrice(50000)
                    .exitPrice(win ? 50100 : 49900)
                    .quantity(1.0)
                    .profit(profit)
                    .profitPercent(profit / 5000)
                    .entryTime(Instant.now().minusSeconds(count - i))
                    .exitTime(Instant.now().minusSeconds(count - i - 1))
                    .build());
        }
        return trades;
    }

    private List<Trade> createMixedTrades(int count) {
        List<Trade> trades = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            boolean win = Math.random() > 0.5;
            double profit = win ? 50 + Math.random() * 100 : -(50 + Math.random() * 100);

            trades.add(Trade.builder()
                    .id("trade-" + i)
                    .symbol("BTCUSDT")
                    .type(Trade.TradeType.LONG)
                    .entryPrice(50000)
                    .exitPrice(win ? 50050 : 49950)
                    .quantity(1.0)
                    .profit(profit)
                    .profitPercent(profit / 5000)
                    .entryTime(Instant.now().minusSeconds(count - i))
                    .exitTime(Instant.now().minusSeconds(count - i - 1))
                    .build());
        }
        return trades;
    }

    private List<Candle> createTestCandles(int count) {
        List<Candle> candles = new ArrayList<>();
        double price = 50000;

        for (int i = 0; i < count; i++) {
            double change = (Math.random() - 0.48) * 100; // Slight upward bias
            double open = price;
            price += change;
            double close = price;
            double high = Math.max(open, close) + Math.random() * 50;
            double low = Math.min(open, close) - Math.random() * 50;

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
