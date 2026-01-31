package com.trader.metrics;

import com.trader.core.Position;
import com.trader.core.Trade;
import com.trader.core.TradingSignal;
import com.trader.risk.CircuitBreaker;
import com.trader.risk.RiskManagement;
import com.trader.validation.LatencyManager;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/**
 * Real-time dashboard metrics for monitoring trading bot health and performance.
 */
public class DashboardMetrics {
    private static final Logger logger = LoggerFactory.getLogger(DashboardMetrics.class);

    // Component references
    private Supplier<Double> capitalSupplier;
    private Supplier<List<Position>> openPositionsSupplier;
    private Supplier<List<Trade>> tradeHistorySupplier;
    private Supplier<List<TradingSignal>> activeSignalsSupplier;
    private Supplier<RiskManagement.RiskMetrics> riskMetricsSupplier;
    private Supplier<CircuitBreaker.CircuitBreakerState> circuitBreakerSupplier;
    private Supplier<Map<String, LatencyManager.LatencyStats>> latencySupplier;

    // Internal tracking
    private final List<Double> equityHistory;
    private final Map<String, Double> customMetrics;
    private final Instant startTime;
    private volatile boolean websocketConnected;
    private volatile Instant lastDataUpdate;
    private volatile Instant lastTradeTime;

    public DashboardMetrics() {
        this.equityHistory = new CopyOnWriteArrayList<>();
        this.customMetrics = new ConcurrentHashMap<>();
        this.startTime = Instant.now();
        this.websocketConnected = false;
        this.lastDataUpdate = Instant.now();
    }

    /**
     * Configures the data suppliers for the dashboard.
     */
    public void configure(
            Supplier<Double> capitalSupplier,
            Supplier<List<Position>> openPositionsSupplier,
            Supplier<List<Trade>> tradeHistorySupplier,
            Supplier<List<TradingSignal>> activeSignalsSupplier,
            Supplier<RiskManagement.RiskMetrics> riskMetricsSupplier,
            Supplier<CircuitBreaker.CircuitBreakerState> circuitBreakerSupplier,
            Supplier<Map<String, LatencyManager.LatencyStats>> latencySupplier
    ) {
        this.capitalSupplier = capitalSupplier;
        this.openPositionsSupplier = openPositionsSupplier;
        this.tradeHistorySupplier = tradeHistorySupplier;
        this.activeSignalsSupplier = activeSignalsSupplier;
        this.riskMetricsSupplier = riskMetricsSupplier;
        this.circuitBreakerSupplier = circuitBreakerSupplier;
        this.latencySupplier = latencySupplier;
    }

    /**
     * Records the current equity value.
     */
    public void recordEquity(double equity) {
        equityHistory.add(equity);
        // Keep only last 1000 data points
        while (equityHistory.size() > 1000) {
            equityHistory.remove(0);
        }
    }

    /**
     * Updates websocket connection status.
     */
    public void setWebsocketConnected(boolean connected) {
        this.websocketConnected = connected;
    }

    /**
     * Updates the last data update timestamp.
     */
    public void recordDataUpdate() {
        this.lastDataUpdate = Instant.now();
    }

    /**
     * Records a trade execution.
     */
    public void recordTrade() {
        this.lastTradeTime = Instant.now();
    }

    /**
     * Sets a custom metric value.
     */
    public void setCustomMetric(String name, double value) {
        customMetrics.put(name, value);
    }

    /**
     * Gets the current dashboard data.
     */
    public DashboardData getCurrentMetrics() {
        Double currentCapital = capitalSupplier != null ? capitalSupplier.get() : 0.0;
        List<Position> openPositions = openPositionsSupplier != null ?
                openPositionsSupplier.get() : Collections.emptyList();
        List<Trade> tradeHistory = tradeHistorySupplier != null ?
                tradeHistorySupplier.get() : Collections.emptyList();
        List<TradingSignal> activeSignals = activeSignalsSupplier != null ?
                activeSignalsSupplier.get() : Collections.emptyList();
        RiskManagement.RiskMetrics riskMetrics = riskMetricsSupplier != null ?
                riskMetricsSupplier.get() : null;
        CircuitBreaker.CircuitBreakerState cbState = circuitBreakerSupplier != null ?
                circuitBreakerSupplier.get() : null;

        // Calculate metrics
        double dailyPnL = calculateDailyPnL(tradeHistory);
        double winRate = calculateWinRate(tradeHistory);
        int todayTrades = countTodayTrades(tradeHistory);

        return DashboardData.builder()
                .currentCapital(currentCapital)
                .dailyPnL(dailyPnL)
                .openPositions(openPositions)
                .openPositionCount(openPositions.size())
                .todayTrades(todayTrades)
                .totalTrades(tradeHistory.size())
                .winRate(winRate)
                .activeSignals(activeSignals)
                .activeSignalCount(activeSignals.size())
                .riskMetrics(riskMetrics)
                .circuitBreakerState(cbState)
                .systemHealth(getSystemHealth())
                .equityHistory(new ArrayList<>(equityHistory))
                .customMetrics(new HashMap<>(customMetrics))
                .timestamp(Instant.now())
                .build();
    }

    /**
     * Gets the system health status.
     */
    public SystemHealth getSystemHealth() {
        Duration uptime = Duration.between(startTime, Instant.now());
        Duration timeSinceLastUpdate = Duration.between(lastDataUpdate, Instant.now());

        // Determine overall health
        boolean healthy = websocketConnected &&
                timeSinceLastUpdate.toSeconds() < 60;

        String status;
        if (!websocketConnected) {
            status = "DISCONNECTED";
        } else if (timeSinceLastUpdate.toSeconds() > 30) {
            status = "STALE";
        } else {
            status = "HEALTHY";
        }

        // Get memory usage
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        double memoryUsage = (double) usedMemory / maxMemory * 100;

        // Get average latency
        double avgLatency = 0;
        if (latencySupplier != null) {
            Map<String, LatencyManager.LatencyStats> latencyStats = latencySupplier.get();
            if (latencyStats != null && !latencyStats.isEmpty()) {
                avgLatency = latencyStats.values().stream()
                        .mapToDouble(LatencyManager.LatencyStats::getAverageLatency)
                        .average()
                        .orElse(0);
            }
        }

        return SystemHealth.builder()
                .healthy(healthy)
                .status(status)
                .websocketConnected(websocketConnected)
                .apiLatency(avgLatency)
                .lastDataUpdate(lastDataUpdate)
                .lastTradeTime(lastTradeTime)
                .uptime(uptime)
                .memoryUsage(memoryUsage)
                .usedMemoryMB(usedMemory / 1024 / 1024)
                .maxMemoryMB(maxMemory / 1024 / 1024)
                .build();
    }

    /**
     * Gets real-time performance metrics.
     */
    public RealTimePerformance getRealTimePerformance() {
        List<Trade> trades = tradeHistorySupplier != null ?
                tradeHistorySupplier.get() : Collections.emptyList();

        // Calculate rolling metrics
        double last24hPnL = calculatePnLForPeriod(trades, Duration.ofHours(24));
        double last7dPnL = calculatePnLForPeriod(trades, Duration.ofDays(7));
        double last30dPnL = calculatePnLForPeriod(trades, Duration.ofDays(30));

        // Calculate trade frequency
        int tradesLast24h = countTradesForPeriod(trades, Duration.ofHours(24));
        int tradesLast7d = countTradesForPeriod(trades, Duration.ofDays(7));

        // Calculate current drawdown
        double currentDrawdown = calculateCurrentDrawdown();

        return RealTimePerformance.builder()
                .pnlLast24h(last24hPnL)
                .pnlLast7d(last7dPnL)
                .pnlLast30d(last30dPnL)
                .tradesLast24h(tradesLast24h)
                .tradesLast7d(tradesLast7d)
                .currentDrawdown(currentDrawdown)
                .equityCurvePoints(getRecentEquityPoints(100))
                .timestamp(Instant.now())
                .build();
    }

    /**
     * Calculates daily P&L from trades.
     */
    private double calculateDailyPnL(List<Trade> trades) {
        Instant todayStart = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS);
        return trades.stream()
                .filter(t -> t.getExitTime() != null && t.getExitTime().isAfter(todayStart))
                .mapToDouble(Trade::getProfit)
                .sum();
    }

    /**
     * Calculates win rate from trades.
     */
    private double calculateWinRate(List<Trade> trades) {
        if (trades.isEmpty()) return 0;
        long wins = trades.stream().filter(t -> t.getProfit() > 0).count();
        return (double) wins / trades.size();
    }

    /**
     * Counts today's trades.
     */
    private int countTodayTrades(List<Trade> trades) {
        Instant todayStart = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS);
        return (int) trades.stream()
                .filter(t -> t.getExitTime() != null && t.getExitTime().isAfter(todayStart))
                .count();
    }

    /**
     * Calculates P&L for a given period.
     */
    private double calculatePnLForPeriod(List<Trade> trades, Duration period) {
        Instant cutoff = Instant.now().minus(period);
        return trades.stream()
                .filter(t -> t.getExitTime() != null && t.getExitTime().isAfter(cutoff))
                .mapToDouble(Trade::getProfit)
                .sum();
    }

    /**
     * Counts trades for a given period.
     */
    private int countTradesForPeriod(List<Trade> trades, Duration period) {
        Instant cutoff = Instant.now().minus(period);
        return (int) trades.stream()
                .filter(t -> t.getExitTime() != null && t.getExitTime().isAfter(cutoff))
                .count();
    }

    /**
     * Calculates current drawdown from equity history.
     */
    private double calculateCurrentDrawdown() {
        if (equityHistory.isEmpty()) return 0;

        double peak = equityHistory.stream()
                .mapToDouble(d -> d)
                .max()
                .orElse(0);

        double current = equityHistory.get(equityHistory.size() - 1);

        return peak > 0 ? (peak - current) / peak * 100 : 0;
    }

    /**
     * Gets recent equity curve points.
     */
    private List<Double> getRecentEquityPoints(int count) {
        if (equityHistory.size() <= count) {
            return new ArrayList<>(equityHistory);
        }
        return new ArrayList<>(
                equityHistory.subList(equityHistory.size() - count, equityHistory.size())
        );
    }

    /**
     * Generates a text summary for logging.
     */
    public String getSummary() {
        DashboardData data = getCurrentMetrics();
        SystemHealth health = data.getSystemHealth();

        StringBuilder sb = new StringBuilder();
        sb.append("\n========== DASHBOARD SUMMARY ==========\n\n");

        sb.append("SYSTEM STATUS\n");
        sb.append(String.format("  Status: %s\n", health.getStatus()));
        sb.append(String.format("  Uptime: %s\n", formatDuration(health.getUptime())));
        sb.append(String.format("  Memory: %.1f%% (%.0fMB / %.0fMB)\n",
                health.getMemoryUsage(), health.getUsedMemoryMB(), health.getMaxMemoryMB()));
        sb.append(String.format("  API Latency: %.1fms\n", health.getApiLatency()));
        sb.append("\n");

        sb.append("CAPITAL\n");
        sb.append(String.format("  Current: $%.2f\n", data.getCurrentCapital()));
        sb.append(String.format("  Daily P&L: $%.2f\n", data.getDailyPnL()));
        sb.append("\n");

        sb.append("TRADING\n");
        sb.append(String.format("  Open Positions: %d\n", data.getOpenPositionCount()));
        sb.append(String.format("  Today's Trades: %d\n", data.getTodayTrades()));
        sb.append(String.format("  Total Trades: %d\n", data.getTotalTrades()));
        sb.append(String.format("  Win Rate: %.1f%%\n", data.getWinRate() * 100));
        sb.append("\n");

        sb.append("SIGNALS\n");
        sb.append(String.format("  Active Signals: %d\n", data.getActiveSignalCount()));
        sb.append("\n");

        if (data.getCircuitBreakerState() != null) {
            sb.append("RISK\n");
            sb.append(String.format("  Circuit Breaker: %s\n",
                    data.getCircuitBreakerState().isCircuitOpen() ? "OPEN" : "CLOSED"));
            sb.append(String.format("  Consecutive Losses: %d\n",
                    data.getCircuitBreakerState().getConsecutiveLosses()));
            sb.append(String.format("  Daily Loss: $%.2f (%.1f%%)\n",
                    data.getCircuitBreakerState().getDailyLoss(),
                    data.getCircuitBreakerState().getDailyLossPercent()));
        }

        sb.append("\n==========================================\n");

        return sb.toString();
    }

    /**
     * Formats duration for display.
     */
    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();

        if (days > 0) {
            return String.format("%dd %dh %dm", days, hours, minutes);
        } else if (hours > 0) {
            return String.format("%dh %dm", hours, minutes);
        } else {
            return String.format("%dm", minutes);
        }
    }

    @Data
    @Builder
    public static class DashboardData {
        private double currentCapital;
        private double dailyPnL;
        private List<Position> openPositions;
        private int openPositionCount;
        private int todayTrades;
        private int totalTrades;
        private double winRate;
        private List<TradingSignal> activeSignals;
        private int activeSignalCount;
        private RiskManagement.RiskMetrics riskMetrics;
        private CircuitBreaker.CircuitBreakerState circuitBreakerState;
        private SystemHealth systemHealth;
        private List<Double> equityHistory;
        private Map<String, Double> customMetrics;
        private Instant timestamp;
    }

    @Data
    @Builder
    public static class SystemHealth {
        private boolean healthy;
        private String status;
        private boolean websocketConnected;
        private double apiLatency;
        private Instant lastDataUpdate;
        private Instant lastTradeTime;
        private Duration uptime;
        private double memoryUsage;
        private double usedMemoryMB;
        private double maxMemoryMB;
    }

    @Data
    @Builder
    public static class RealTimePerformance {
        private double pnlLast24h;
        private double pnlLast7d;
        private double pnlLast30d;
        private int tradesLast24h;
        private int tradesLast7d;
        private double currentDrawdown;
        private List<Double> equityCurvePoints;
        private Instant timestamp;
    }
}
