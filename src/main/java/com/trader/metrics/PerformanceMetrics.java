package com.trader.metrics;

import com.trader.core.Trade;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Calculates and tracks comprehensive trading performance metrics.
 */
public class PerformanceMetrics {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceMetrics.class);

    private static final double RISK_FREE_RATE = 0.02; // 2% annual risk-free rate
    private static final int TRADING_DAYS_PER_YEAR = 252;

    /**
     * Calculates all performance metrics from a list of trades.
     */
    public Metrics calculateMetrics(List<Trade> trades, List<Double> equityCurve, double initialCapital) {
        if (trades.isEmpty()) {
            return Metrics.builder()
                    .totalTrades(0)
                    .build();
        }

        // Separate wins and losses
        List<Trade> wins = trades.stream().filter(Trade::isWinningTrade).collect(Collectors.toList());
        List<Trade> losses = trades.stream().filter(t -> !t.isWinningTrade()).collect(Collectors.toList());

        // Calculate returns
        List<Double> returns = calculateReturns(equityCurve);
        double finalCapital = equityCurve.isEmpty() ? initialCapital : equityCurve.get(equityCurve.size() - 1);
        double totalReturn = ((finalCapital - initialCapital) / initialCapital) * 100;

        // Calculate trade stats
        double winRate = trades.isEmpty() ? 0 : (double) wins.size() / trades.size();
        double avgWin = wins.isEmpty() ? 0 : wins.stream().mapToDouble(Trade::getProfit).average().orElse(0);
        double avgLoss = losses.isEmpty() ? 0 : Math.abs(losses.stream().mapToDouble(Trade::getProfit).average().orElse(0));
        double totalWins = wins.stream().mapToDouble(Trade::getProfit).sum();
        double totalLosses = Math.abs(losses.stream().mapToDouble(Trade::getProfit).sum());
        double profitFactor = totalLosses > 0 ? totalWins / totalLosses : totalWins > 0 ? Double.MAX_VALUE : 0;

        // Calculate drawdown
        DrawdownResult drawdown = calculateMaxDrawdown(equityCurve);

        // Calculate ratios
        double volatility = calculateVolatility(returns);
        double sharpeRatio = calculateSharpeRatio(returns, RISK_FREE_RATE);
        double sortinoRatio = calculateSortinoRatio(returns, RISK_FREE_RATE);
        double calmarRatio = drawdown.maxDrawdownPercent > 0 ?
                (totalReturn / 100 * TRADING_DAYS_PER_YEAR / getTradingDays(trades)) / drawdown.maxDrawdownPercent : 0;

        // Calculate VaR
        double valueAtRisk95 = calculateVaR(returns, 0.95);

        // Calculate expectancy
        double expectancy = (winRate * avgWin) - ((1 - winRate) * avgLoss);

        // Calculate consecutive losses
        int maxConsecutiveLosses = calculateMaxConsecutiveLosses(trades);

        return Metrics.builder()
                // Returns
                .totalReturn(totalReturn)
                .annualizedReturn(annualizeReturn(totalReturn, getTradingDays(trades)))
                .sharpeRatio(sharpeRatio)
                .sortinoRatio(sortinoRatio)
                .calmarRatio(calmarRatio)
                // Risk
                .maxDrawdown(drawdown.maxDrawdownPercent)
                .maxDrawdownDuration(drawdown.maxDrawdownDuration)
                .volatility(volatility)
                .valueAtRisk95(valueAtRisk95)
                // Trades
                .totalTrades(trades.size())
                .winningTrades(wins.size())
                .losingTrades(losses.size())
                .winRate(winRate)
                .profitFactor(profitFactor)
                .avgWin(avgWin)
                .avgLoss(avgLoss)
                .avgWinLossRatio(avgLoss > 0 ? avgWin / avgLoss : 0)
                .expectancy(expectancy)
                // Quality
                .maxConsecutiveLosses(maxConsecutiveLosses)
                .avgTradeDuration(calculateAvgTradeDuration(trades))
                .build();
    }

    /**
     * Calculates the Sharpe Ratio (risk-adjusted return).
     */
    public double calculateSharpeRatio(List<Double> returns, double riskFreeRate) {
        if (returns.isEmpty()) return 0;

        double avgReturn = returns.stream().mapToDouble(d -> d).average().orElse(0);
        double stdDev = calculateStdDev(returns);

        if (stdDev == 0) return 0;

        // Annualized Sharpe Ratio
        double dailyRiskFreeRate = riskFreeRate / TRADING_DAYS_PER_YEAR;
        return ((avgReturn - dailyRiskFreeRate) / stdDev) * Math.sqrt(TRADING_DAYS_PER_YEAR);
    }

    /**
     * Calculates the Sortino Ratio (penalizes only downside volatility).
     */
    public double calculateSortinoRatio(List<Double> returns, double riskFreeRate) {
        if (returns.isEmpty()) return 0;

        double avgReturn = returns.stream().mapToDouble(d -> d).average().orElse(0);

        // Calculate downside deviation
        double sumSquaredNegative = returns.stream()
                .filter(r -> r < 0)
                .mapToDouble(r -> r * r)
                .sum();
        double downsideDeviation = Math.sqrt(sumSquaredNegative / returns.size());

        if (downsideDeviation == 0) return avgReturn > 0 ? Double.MAX_VALUE : 0;

        double dailyRiskFreeRate = riskFreeRate / TRADING_DAYS_PER_YEAR;
        return ((avgReturn - dailyRiskFreeRate) / downsideDeviation) * Math.sqrt(TRADING_DAYS_PER_YEAR);
    }

    /**
     * Calculates maximum drawdown and its duration.
     */
    public DrawdownResult calculateMaxDrawdown(List<Double> equityCurve) {
        if (equityCurve.isEmpty()) {
            return new DrawdownResult(0, 0);
        }

        double maxDrawdown = 0;
        double peak = equityCurve.get(0);
        int maxDrawdownDuration = 0;
        int currentDrawdownDuration = 0;
        int peakIndex = 0;

        for (int i = 0; i < equityCurve.size(); i++) {
            double value = equityCurve.get(i);

            if (value > peak) {
                peak = value;
                peakIndex = i;
                currentDrawdownDuration = 0;
            } else {
                currentDrawdownDuration = i - peakIndex;
            }

            double drawdown = (peak - value) / peak;
            if (drawdown > maxDrawdown) {
                maxDrawdown = drawdown;
            }

            if (currentDrawdownDuration > maxDrawdownDuration) {
                maxDrawdownDuration = currentDrawdownDuration;
            }
        }

        return new DrawdownResult(maxDrawdown * 100, maxDrawdownDuration);
    }

    /**
     * Calculates Value at Risk at the specified confidence level.
     */
    public double calculateVaR(List<Double> returns, double confidenceLevel) {
        if (returns.isEmpty()) return 0;

        List<Double> sortedReturns = new ArrayList<>(returns);
        sortedReturns.sort(Double::compareTo);

        int index = (int) Math.floor((1 - confidenceLevel) * sortedReturns.size());
        return Math.abs(sortedReturns.get(Math.max(0, index)));
    }

    /**
     * Calculates daily returns from equity curve.
     */
    private List<Double> calculateReturns(List<Double> equityCurve) {
        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < equityCurve.size(); i++) {
            double prevValue = equityCurve.get(i - 1);
            if (prevValue > 0) {
                double dailyReturn = (equityCurve.get(i) - prevValue) / prevValue;
                returns.add(dailyReturn);
            }
        }
        return returns;
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
     * Calculates volatility (annualized standard deviation of returns).
     */
    private double calculateVolatility(List<Double> returns) {
        return calculateStdDev(returns) * Math.sqrt(TRADING_DAYS_PER_YEAR);
    }

    /**
     * Annualizes a return over a given number of trading days.
     */
    private double annualizeReturn(double totalReturn, int tradingDays) {
        if (tradingDays <= 0) return 0;
        return totalReturn * (TRADING_DAYS_PER_YEAR / (double) tradingDays);
    }

    /**
     * Calculates the number of trading days from trades.
     */
    private int getTradingDays(List<Trade> trades) {
        if (trades.isEmpty()) return 0;

        Instant firstTrade = trades.stream()
                .filter(t -> t.getEntryTime() != null)
                .map(Trade::getEntryTime)
                .min(Instant::compareTo)
                .orElse(Instant.now());

        Instant lastTrade = trades.stream()
                .filter(t -> t.getExitTime() != null)
                .map(Trade::getExitTime)
                .max(Instant::compareTo)
                .orElse(Instant.now());

        long days = Duration.between(firstTrade, lastTrade).toDays();
        return (int) Math.max(1, days);
    }

    /**
     * Calculates maximum consecutive losing trades.
     */
    private int calculateMaxConsecutiveLosses(List<Trade> trades) {
        int maxConsecutive = 0;
        int current = 0;

        for (Trade trade : trades) {
            if (!trade.isWinningTrade()) {
                current++;
                maxConsecutive = Math.max(maxConsecutive, current);
            } else {
                current = 0;
            }
        }

        return maxConsecutive;
    }

    /**
     * Calculates average trade duration.
     */
    private Duration calculateAvgTradeDuration(List<Trade> trades) {
        if (trades.isEmpty()) return Duration.ZERO;

        long totalMs = trades.stream()
                .filter(t -> t.getEntryTime() != null && t.getExitTime() != null)
                .mapToLong(Trade::getDurationMs)
                .sum();

        long count = trades.stream()
                .filter(t -> t.getEntryTime() != null && t.getExitTime() != null)
                .count();

        if (count == 0) return Duration.ZERO;
        return Duration.ofMillis(totalMs / count);
    }

    /**
     * Performance metrics data class.
     */
    @Data
    @Builder
    public static class Metrics {
        // Returns
        private double totalReturn;
        private double annualizedReturn;
        private double sharpeRatio;
        private double sortinoRatio;
        private double calmarRatio;

        // Risk
        private double maxDrawdown;
        private int maxDrawdownDuration;
        private double volatility;
        private double valueAtRisk95;

        // Trades
        private int totalTrades;
        private int winningTrades;
        private int losingTrades;
        private double winRate;
        private double profitFactor;
        private double avgWin;
        private double avgLoss;
        private double avgWinLossRatio;
        private double expectancy;

        // Quality
        private int maxConsecutiveLosses;
        private Duration avgTradeDuration;

        public String getSummary() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Performance Metrics ===\n\n");

            sb.append("RETURNS\n");
            sb.append(String.format("  Total Return: %.2f%%\n", totalReturn));
            sb.append(String.format("  Annualized Return: %.2f%%\n", annualizedReturn));
            sb.append(String.format("  Sharpe Ratio: %.2f\n", sharpeRatio));
            sb.append(String.format("  Sortino Ratio: %.2f\n", sortinoRatio));
            sb.append(String.format("  Calmar Ratio: %.2f\n\n", calmarRatio));

            sb.append("RISK\n");
            sb.append(String.format("  Max Drawdown: %.2f%%\n", maxDrawdown));
            sb.append(String.format("  Max Drawdown Duration: %d days\n", maxDrawdownDuration));
            sb.append(String.format("  Volatility: %.2f%%\n", volatility * 100));
            sb.append(String.format("  VaR (95%%): %.2f%%\n\n", valueAtRisk95 * 100));

            sb.append("TRADES\n");
            sb.append(String.format("  Total Trades: %d\n", totalTrades));
            sb.append(String.format("  Winning Trades: %d\n", winningTrades));
            sb.append(String.format("  Losing Trades: %d\n", losingTrades));
            sb.append(String.format("  Win Rate: %.2f%%\n", winRate * 100));
            sb.append(String.format("  Profit Factor: %.2f\n", profitFactor));
            sb.append(String.format("  Avg Win: %.2f\n", avgWin));
            sb.append(String.format("  Avg Loss: %.2f\n", avgLoss));
            sb.append(String.format("  Win/Loss Ratio: %.2f\n", avgWinLossRatio));
            sb.append(String.format("  Expectancy: %.2f\n\n", expectancy));

            sb.append("QUALITY\n");
            sb.append(String.format("  Max Consecutive Losses: %d\n", maxConsecutiveLosses));
            sb.append(String.format("  Avg Trade Duration: %s\n", formatDuration(avgTradeDuration)));

            return sb.toString();
        }

        private String formatDuration(Duration duration) {
            if (duration == null) return "N/A";
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            return String.format("%dh %dm", hours, minutes);
        }
    }

    @Data
    public static class DrawdownResult {
        private final double maxDrawdownPercent;
        private final int maxDrawdownDuration;

        public DrawdownResult(double maxDrawdownPercent, int maxDrawdownDuration) {
            this.maxDrawdownPercent = maxDrawdownPercent;
            this.maxDrawdownDuration = maxDrawdownDuration;
        }
    }
}
