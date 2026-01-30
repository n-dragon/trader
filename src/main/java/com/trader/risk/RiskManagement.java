package com.trader.risk;

import com.trader.core.Position;
import com.trader.core.Trade;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core risk management module that enforces trading rules and position sizing.
 */
public class RiskManagement {
    private static final Logger logger = LoggerFactory.getLogger(RiskManagement.class);

    // Position sizing constraints
    private final double maxPositionSizePercent;    // Max % of capital per trade
    private final double maxDailyLossPercent;       // Max daily loss %
    private final double maxPortfolioRisk;          // Max total exposure %
    private final double stopLossPercent;           // Default stop-loss %
    private final double takeProfitPercent;         // Default take-profit %
    private final double trailingStopActivation;    // Trailing stop activation %
    private final double trailingStopDistance;      // Trailing stop distance %

    private double totalCapital;
    private double availableCapital;
    private final List<Trade> todayTrades;
    private final ConcurrentHashMap<String, Position> openPositions;

    @Builder
    public RiskManagement(
            double totalCapital,
            double maxPositionSizePercent,
            double maxDailyLossPercent,
            double maxPortfolioRisk,
            double stopLossPercent,
            double takeProfitPercent,
            double trailingStopActivation,
            double trailingStopDistance
    ) {
        this.totalCapital = totalCapital;
        this.availableCapital = totalCapital;
        this.maxPositionSizePercent = maxPositionSizePercent > 0 ? maxPositionSizePercent : 2.0;
        this.maxDailyLossPercent = maxDailyLossPercent > 0 ? maxDailyLossPercent : 5.0;
        this.maxPortfolioRisk = maxPortfolioRisk > 0 ? maxPortfolioRisk : 10.0;
        this.stopLossPercent = stopLossPercent > 0 ? stopLossPercent : 1.5;
        this.takeProfitPercent = takeProfitPercent > 0 ? takeProfitPercent : 3.0;
        this.trailingStopActivation = trailingStopActivation > 0 ? trailingStopActivation : 2.0;
        this.trailingStopDistance = trailingStopDistance > 0 ? trailingStopDistance : 1.0;
        this.todayTrades = new ArrayList<>();
        this.openPositions = new ConcurrentHashMap<>();
    }

    /**
     * Calculates the optimal position size using Kelly Criterion (half-Kelly for safety).
     *
     * @param winRate  Historical win rate (0-1)
     * @param avgWin   Average winning trade amount
     * @param avgLoss  Average losing trade amount (positive number)
     * @return Recommended position size as percentage of capital
     */
    public double calculateOptimalPositionSize(double winRate, double avgWin, double avgLoss) {
        if (avgLoss == 0 || winRate <= 0 || winRate >= 1) {
            return maxPositionSizePercent;
        }

        double winLossRatio = avgWin / avgLoss;
        double kellyPercent = (winRate * winLossRatio - (1 - winRate)) / winLossRatio;

        // Use half-Kelly for safety and cap at maxPositionSizePercent
        double halfKelly = kellyPercent * 0.5;
        double result = Math.max(0, Math.min(halfKelly * 100, maxPositionSizePercent));

        logger.debug("Kelly calculation: winRate={}, avgWin={}, avgLoss={}, kelly={}%, halfKelly={}%",
                winRate, avgWin, avgLoss, kellyPercent * 100, halfKelly * 100);

        return result;
    }

    /**
     * Calculates position size based on fixed risk percentage.
     *
     * @param entryPrice     Entry price
     * @param stopLossPrice  Stop loss price
     * @param riskPercent    Risk percentage of capital (e.g., 2.0 for 2%)
     * @return Quantity to trade
     */
    public double calculatePositionSizeByRisk(double entryPrice, double stopLossPrice, double riskPercent) {
        if (entryPrice <= 0 || stopLossPrice <= 0) {
            return 0;
        }

        double riskAmount = availableCapital * (riskPercent / 100);
        double priceRisk = Math.abs(entryPrice - stopLossPrice);

        if (priceRisk == 0) {
            return 0;
        }

        double quantity = riskAmount / priceRisk;
        double positionValue = quantity * entryPrice;

        // Ensure position doesn't exceed max position size
        double maxPositionValue = availableCapital * (maxPositionSizePercent / 100);
        if (positionValue > maxPositionValue) {
            quantity = maxPositionValue / entryPrice;
        }

        logger.debug("Position sizing: entry={}, stopLoss={}, risk={}%, quantity={}",
                entryPrice, stopLossPrice, riskPercent, quantity);

        return quantity;
    }

    /**
     * Calculates stop loss price for a given entry price.
     */
    public double calculateStopLoss(double entryPrice, Trade.TradeType type) {
        if (type == Trade.TradeType.LONG) {
            return entryPrice * (1 - stopLossPercent / 100);
        } else {
            return entryPrice * (1 + stopLossPercent / 100);
        }
    }

    /**
     * Calculates take profit price for a given entry price.
     */
    public double calculateTakeProfit(double entryPrice, Trade.TradeType type) {
        if (type == Trade.TradeType.LONG) {
            return entryPrice * (1 + takeProfitPercent / 100);
        } else {
            return entryPrice * (1 - takeProfitPercent / 100);
        }
    }

    /**
     * Checks if a new trade is allowed based on risk rules.
     */
    public RiskCheckResult checkRiskRules(double proposedPositionValue) {
        List<String> violations = new ArrayList<>();

        // Check daily loss limit
        double dailyPnL = calculateDailyPnL();
        double dailyLossLimit = totalCapital * (maxDailyLossPercent / 100);
        if (dailyPnL < -dailyLossLimit) {
            violations.add(String.format("Daily loss limit reached: %.2f (limit: %.2f)",
                    dailyPnL, -dailyLossLimit));
        }

        // Check portfolio exposure
        double currentExposure = calculateTotalExposure();
        double maxExposure = totalCapital * (maxPortfolioRisk / 100);
        if (currentExposure + proposedPositionValue > maxExposure) {
            violations.add(String.format("Max portfolio exposure would be exceeded: %.2f + %.2f > %.2f",
                    currentExposure, proposedPositionValue, maxExposure));
        }

        // Check position size limit
        double maxPositionValue = totalCapital * (maxPositionSizePercent / 100);
        if (proposedPositionValue > maxPositionValue) {
            violations.add(String.format("Position size exceeds limit: %.2f > %.2f",
                    proposedPositionValue, maxPositionValue));
        }

        // Check available capital
        if (proposedPositionValue > availableCapital) {
            violations.add(String.format("Insufficient capital: %.2f required, %.2f available",
                    proposedPositionValue, availableCapital));
        }

        return RiskCheckResult.builder()
                .allowed(violations.isEmpty())
                .violations(violations)
                .dailyPnL(dailyPnL)
                .currentExposure(currentExposure)
                .availableCapital(availableCapital)
                .build();
    }

    /**
     * Records a completed trade.
     */
    public void recordTrade(Trade trade) {
        todayTrades.add(trade);
        availableCapital += trade.getProfit();
        totalCapital += trade.getProfit();
        logger.info("Trade recorded: {} profit={:.2f}, new capital={:.2f}",
                trade.getSymbol(), trade.getProfit(), totalCapital);
    }

    /**
     * Registers an open position.
     */
    public void registerPosition(Position position) {
        openPositions.put(position.getId(), position);
        availableCapital -= position.getEntryValue();
        logger.info("Position registered: {} value={:.2f}, available capital={:.2f}",
                position.getSymbol(), position.getEntryValue(), availableCapital);
    }

    /**
     * Removes a closed position.
     */
    public void closePosition(String positionId, double exitValue) {
        Position position = openPositions.remove(positionId);
        if (position != null) {
            availableCapital += exitValue;
            logger.info("Position closed: {}, exit value={:.2f}", positionId, exitValue);
        }
    }

    /**
     * Calculates today's P&L.
     */
    public double calculateDailyPnL() {
        LocalDate today = LocalDate.now();
        return todayTrades.stream()
                .filter(t -> t.getExitTime() != null &&
                        LocalDate.ofInstant(t.getExitTime(), ZoneId.systemDefault()).equals(today))
                .mapToDouble(Trade::getProfit)
                .sum();
    }

    /**
     * Calculates total current exposure (value of all open positions).
     */
    public double calculateTotalExposure() {
        return openPositions.values().stream()
                .mapToDouble(Position::getEntryValue)
                .sum();
    }

    /**
     * Resets daily counters (should be called at the start of each trading day).
     */
    public void resetDailyCounters() {
        todayTrades.clear();
        logger.info("Daily counters reset");
    }

    /**
     * Gets the current risk metrics.
     */
    public RiskMetrics getRiskMetrics() {
        return RiskMetrics.builder()
                .totalCapital(totalCapital)
                .availableCapital(availableCapital)
                .currentExposure(calculateTotalExposure())
                .exposurePercent(calculateTotalExposure() / totalCapital * 100)
                .dailyPnL(calculateDailyPnL())
                .dailyPnLPercent(calculateDailyPnL() / totalCapital * 100)
                .openPositionCount(openPositions.size())
                .todayTradeCount(todayTrades.size())
                .build();
    }

    // Getters
    public double getTotalCapital() {
        return totalCapital;
    }

    public double getAvailableCapital() {
        return availableCapital;
    }

    public double getStopLossPercent() {
        return stopLossPercent;
    }

    public double getTakeProfitPercent() {
        return takeProfitPercent;
    }

    public double getTrailingStopActivation() {
        return trailingStopActivation;
    }

    public double getTrailingStopDistance() {
        return trailingStopDistance;
    }

    @Data
    @Builder
    public static class RiskCheckResult {
        private boolean allowed;
        private List<String> violations;
        private double dailyPnL;
        private double currentExposure;
        private double availableCapital;
    }

    @Data
    @Builder
    public static class RiskMetrics {
        private double totalCapital;
        private double availableCapital;
        private double currentExposure;
        private double exposurePercent;
        private double dailyPnL;
        private double dailyPnLPercent;
        private int openPositionCount;
        private int todayTradeCount;
    }
}
