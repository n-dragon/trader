package com.trader.engine;

import com.trader.core.*;
import com.trader.indicators.AdaptiveIndicators;
import com.trader.metrics.PerformanceMetrics;
import com.trader.patterns.AdvancedPatternDetector;
import com.trader.risk.RiskManagement;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * Backtesting engine for evaluating trading strategies on historical data.
 */
public class BacktestEngine {
    private static final Logger logger = LoggerFactory.getLogger(BacktestEngine.class);

    private final double initialCapital;
    private final double commissionPercent;
    private final double slippagePercent;
    private final AdaptiveIndicators indicators;
    private final AdvancedPatternDetector patternDetector;

    public BacktestEngine(double initialCapital) {
        this(initialCapital, 0.1, 0.05); // 0.1% commission, 0.05% slippage
    }

    public BacktestEngine(double initialCapital, double commissionPercent, double slippagePercent) {
        this.initialCapital = initialCapital;
        this.commissionPercent = commissionPercent;
        this.slippagePercent = slippagePercent;
        this.indicators = new AdaptiveIndicators();
        this.patternDetector = new AdvancedPatternDetector();
    }

    /**
     * Runs a backtest with a custom strategy function.
     *
     * @param historicalData List of historical candles
     * @param strategy       Function that takes candle history and returns a trading signal
     * @return Backtest report with detailed results
     */
    public BacktestReport runBacktest(
            List<Candle> historicalData,
            Function<List<Candle>, TradingSignal> strategy
    ) {
        return runBacktest(historicalData, strategy, BacktestConfig.builder().build());
    }

    /**
     * Runs a backtest with custom strategy and configuration.
     */
    public BacktestReport runBacktest(
            List<Candle> historicalData,
            Function<List<Candle>, TradingSignal> strategy,
            BacktestConfig config
    ) {
        if (historicalData == null || historicalData.size() < config.getMinDataPoints()) {
            throw new IllegalArgumentException(
                    String.format("Insufficient data: need at least %d candles", config.getMinDataPoints()));
        }

        logger.info("Starting backtest with {} candles, initial capital: {}",
                historicalData.size(), initialCapital);

        double capital = initialCapital;
        Position currentPosition = null;
        List<Trade> trades = new ArrayList<>();
        List<Double> equityCurve = new ArrayList<>();
        int warmupPeriod = config.getWarmupPeriod();

        for (int i = warmupPeriod; i < historicalData.size(); i++) {
            Candle currentCandle = historicalData.get(i);
            List<Candle> history = historicalData.subList(0, i + 1);
            double currentPrice = currentCandle.getClose();

            // Update equity curve
            double currentEquity = capital;
            if (currentPosition != null) {
                currentEquity += currentPosition.getUnrealizedPnL(currentPrice);
            }
            equityCurve.add(currentEquity);

            // Check position management first
            if (currentPosition != null) {
                // Check stop-loss
                if (currentPosition.isStopLossTriggered(currentCandle.getLow())) {
                    double exitPrice = applySlippage(currentPosition.getStopLossPrice(), false);
                    Trade trade = closePosition(currentPosition, exitPrice, "Stop-loss");
                    trades.add(trade);
                    capital += trade.getProfit();
                    currentPosition = null;
                    continue;
                }

                // Check take-profit
                if (currentPosition.isTakeProfitReached(currentCandle.getHigh())) {
                    double exitPrice = applySlippage(currentPosition.getTakeProfitPrice(), true);
                    Trade trade = closePosition(currentPosition, exitPrice, "Take-profit");
                    trades.add(trade);
                    capital += trade.getProfit();
                    currentPosition = null;
                    continue;
                }

                // Update trailing stop
                currentPosition.updateTrailingStop(currentPrice);
            }

            // Get strategy signal
            TradingSignal signal = strategy.apply(history);

            if (signal == null || signal.getType() == TradingSignal.SignalType.NEUTRAL) {
                continue;
            }

            // Process signal
            if (currentPosition == null && signal.isEntrySignal()) {
                // Open new position
                if (signal.getConfidence() >= config.getMinConfidence()) {
                    double entryPrice = applySlippage(currentPrice, true);
                    double positionSize = calculatePositionSize(capital, entryPrice,
                            signal.getSuggestedStopLoss(), config.getRiskPerTradePercent());

                    if (positionSize * entryPrice <= capital) {
                        currentPosition = Position.builder()
                                .id(UUID.randomUUID().toString())
                                .symbol(currentCandle.getSymbol())
                                .type(signal.getType() == TradingSignal.SignalType.ENTRY_LONG ?
                                        Trade.TradeType.LONG : Trade.TradeType.SHORT)
                                .entryPrice(entryPrice)
                                .quantity(positionSize)
                                .entryTime(currentCandle.getCloseTime())
                                .stopLossPrice(signal.getSuggestedStopLoss())
                                .takeProfitPrice(signal.getSuggestedTakeProfit())
                                .trailingStopActivation(config.getTrailingStopActivation())
                                .trailingStopDistance(config.getTrailingStopDistance())
                                .highestPrice(entryPrice)
                                .lowestPrice(entryPrice)
                                .status(Position.PositionStatus.OPEN)
                                .build();

                        logger.debug("Opened position at {} with size {}", entryPrice, positionSize);
                    }
                }
            } else if (currentPosition != null && signal.isExitSignal()) {
                // Close position on exit signal
                double exitPrice = applySlippage(currentPrice,
                        currentPosition.getType() == Trade.TradeType.LONG);
                Trade trade = closePosition(currentPosition, exitPrice, "Exit signal");
                trades.add(trade);
                capital += trade.getProfit();
                currentPosition = null;
            }
        }

        // Close any remaining position at the end
        if (currentPosition != null) {
            double lastPrice = historicalData.get(historicalData.size() - 1).getClose();
            double exitPrice = applySlippage(lastPrice, currentPosition.getType() == Trade.TradeType.LONG);
            Trade trade = closePosition(currentPosition, exitPrice, "End of backtest");
            trades.add(trade);
            capital += trade.getProfit();
        }

        // Calculate metrics
        PerformanceMetrics metricsCalculator = new PerformanceMetrics();
        PerformanceMetrics.Metrics metrics = metricsCalculator.calculateMetrics(trades, equityCurve, initialCapital);

        return BacktestReport.builder()
                .initialCapital(initialCapital)
                .finalCapital(capital)
                .totalReturn((capital - initialCapital) / initialCapital * 100)
                .trades(trades)
                .equityCurve(equityCurve)
                .metrics(metrics)
                .config(config)
                .dataPoints(historicalData.size())
                .startTime(historicalData.get(0).getOpenTime())
                .endTime(historicalData.get(historicalData.size() - 1).getCloseTime())
                .build();
    }

    /**
     * Runs a simple moving average crossover strategy backtest.
     */
    public BacktestReport runSMACrossoverBacktest(List<Candle> data, int fastPeriod, int slowPeriod) {
        Function<List<Candle>, TradingSignal> strategy = (history) -> {
            if (history.size() < slowPeriod + 1) {
                return null;
            }

            double fastSMA = indicators.calculateSMA(history, fastPeriod);
            double slowSMA = indicators.calculateSMA(history, slowPeriod);

            // Previous values
            List<Candle> prevHistory = history.subList(0, history.size() - 1);
            double prevFastSMA = indicators.calculateSMA(prevHistory, fastPeriod);
            double prevSlowSMA = indicators.calculateSMA(prevHistory, slowPeriod);

            Candle current = history.get(history.size() - 1);
            double price = current.getClose();

            // Bullish crossover
            if (prevFastSMA <= prevSlowSMA && fastSMA > slowSMA) {
                double stopLoss = price * 0.98; // 2% stop-loss
                double takeProfit = price * 1.04; // 4% take-profit

                return TradingSignal.builder()
                        .symbol(current.getSymbol())
                        .type(TradingSignal.SignalType.ENTRY_LONG)
                        .action(TradingSignal.SignalAction.BUY)
                        .price(price)
                        .confidence(0.7)
                        .timestamp(current.getCloseTime())
                        .suggestedStopLoss(stopLoss)
                        .suggestedTakeProfit(takeProfit)
                        .build();
            }

            // Bearish crossover
            if (prevFastSMA >= prevSlowSMA && fastSMA < slowSMA) {
                return TradingSignal.builder()
                        .symbol(current.getSymbol())
                        .type(TradingSignal.SignalType.EXIT_LONG)
                        .action(TradingSignal.SignalAction.SELL)
                        .price(price)
                        .confidence(0.7)
                        .timestamp(current.getCloseTime())
                        .build();
            }

            return null;
        };

        return runBacktest(data, strategy);
    }

    /**
     * Runs an RSI-based strategy backtest.
     */
    public BacktestReport runRSIBacktest(List<Candle> data, int period, double oversold, double overbought) {
        Function<List<Candle>, TradingSignal> strategy = (history) -> {
            if (history.size() < period + 1) {
                return null;
            }

            double rsi = indicators.calculateRSI(history, period);
            Candle current = history.get(history.size() - 1);
            double price = current.getClose();

            // Oversold - buy signal
            if (rsi < oversold) {
                double stopLoss = price * 0.97; // 3% stop-loss
                double takeProfit = price * 1.06; // 6% take-profit

                return TradingSignal.builder()
                        .symbol(current.getSymbol())
                        .type(TradingSignal.SignalType.ENTRY_LONG)
                        .action(TradingSignal.SignalAction.BUY)
                        .price(price)
                        .confidence(0.6 + (oversold - rsi) / 100)
                        .timestamp(current.getCloseTime())
                        .suggestedStopLoss(stopLoss)
                        .suggestedTakeProfit(takeProfit)
                        .build();
            }

            // Overbought - sell signal
            if (rsi > overbought) {
                return TradingSignal.builder()
                        .symbol(current.getSymbol())
                        .type(TradingSignal.SignalType.EXIT_LONG)
                        .action(TradingSignal.SignalAction.SELL)
                        .price(price)
                        .confidence(0.6 + (rsi - overbought) / 100)
                        .timestamp(current.getCloseTime())
                        .build();
            }

            return null;
        };

        return runBacktest(data, strategy);
    }

    private Trade closePosition(Position position, double exitPrice, String reason) {
        double commission = position.getQuantity() * exitPrice * (commissionPercent / 100);
        double profit = position.getUnrealizedPnL(exitPrice) - commission;
        double profitPercent = position.getUnrealizedPnLPercent(exitPrice);

        return Trade.builder()
                .id(UUID.randomUUID().toString())
                .symbol(position.getSymbol())
                .type(position.getType())
                .entryPrice(position.getEntryPrice())
                .exitPrice(exitPrice)
                .quantity(position.getQuantity())
                .entryTime(position.getEntryTime())
                .exitTime(Instant.now())
                .profit(profit)
                .profitPercent(profitPercent)
                .commission(commission)
                .exitReason(reason)
                .stopLossPrice(position.getStopLossPrice())
                .takeProfitPrice(position.getTakeProfitPrice())
                .build();
    }

    private double calculatePositionSize(double capital, double entryPrice, double stopLoss, double riskPercent) {
        double riskAmount = capital * (riskPercent / 100);
        double priceRisk = Math.abs(entryPrice - stopLoss);

        if (priceRisk <= 0) {
            priceRisk = entryPrice * 0.02; // Default 2% if no stop-loss
        }

        return riskAmount / priceRisk;
    }

    private double applySlippage(double price, boolean isBuy) {
        double slippageFactor = slippagePercent / 100;
        return isBuy ? price * (1 + slippageFactor) : price * (1 - slippageFactor);
    }

    @Data
    @Builder
    public static class BacktestConfig {
        @Builder.Default
        private int warmupPeriod = 50;

        @Builder.Default
        private int minDataPoints = 100;

        @Builder.Default
        private double minConfidence = 0.5;

        @Builder.Default
        private double riskPerTradePercent = 2.0;

        @Builder.Default
        private double trailingStopActivation = 2.0;

        @Builder.Default
        private double trailingStopDistance = 1.0;
    }

    @Data
    @Builder
    public static class BacktestReport {
        private double initialCapital;
        private double finalCapital;
        private double totalReturn;
        private List<Trade> trades;
        private List<Double> equityCurve;
        private PerformanceMetrics.Metrics metrics;
        private BacktestConfig config;
        private int dataPoints;
        private Instant startTime;
        private Instant endTime;

        public String getSummary() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Backtest Report ===\n\n");

            sb.append(String.format("Period: %s to %s\n", startTime, endTime));
            sb.append(String.format("Data points: %d\n\n", dataPoints));

            sb.append("CAPITAL\n");
            sb.append(String.format("  Initial: %.2f\n", initialCapital));
            sb.append(String.format("  Final: %.2f\n", finalCapital));
            sb.append(String.format("  Return: %.2f%%\n\n", totalReturn));

            if (metrics != null) {
                sb.append(metrics.getSummary());
            }

            return sb.toString();
        }
    }
}
