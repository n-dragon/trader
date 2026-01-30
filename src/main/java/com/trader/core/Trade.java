package com.trader.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents a completed trade with entry and exit information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    private String id;
    private String symbol;
    private TradeType type;
    private double entryPrice;
    private double exitPrice;
    private double quantity;
    private Instant entryTime;
    private Instant exitTime;
    private double profit;
    private double profitPercent;
    private double commission;
    private String exitReason;
    private double stopLossPrice;
    private double takeProfitPrice;

    public enum TradeType {
        LONG,
        SHORT
    }

    /**
     * Calculates the profit/loss of the trade.
     */
    public double calculateProfit() {
        if (type == TradeType.LONG) {
            return (exitPrice - entryPrice) * quantity - commission;
        } else {
            return (entryPrice - exitPrice) * quantity - commission;
        }
    }

    /**
     * Calculates the profit percentage.
     */
    public double calculateProfitPercent() {
        if (entryPrice == 0) return 0;
        if (type == TradeType.LONG) {
            return ((exitPrice - entryPrice) / entryPrice) * 100;
        } else {
            return ((entryPrice - exitPrice) / entryPrice) * 100;
        }
    }

    /**
     * Returns true if the trade was profitable.
     */
    public boolean isWinningTrade() {
        return profit > 0;
    }

    /**
     * Returns the duration of the trade in milliseconds.
     */
    public long getDurationMs() {
        if (entryTime == null || exitTime == null) return 0;
        return exitTime.toEpochMilli() - entryTime.toEpochMilli();
    }

    /**
     * Returns the R-multiple (profit divided by initial risk).
     */
    public double getRMultiple() {
        if (stopLossPrice == 0 || entryPrice == 0) return 0;
        double initialRisk = Math.abs(entryPrice - stopLossPrice) * quantity;
        if (initialRisk == 0) return 0;
        return profit / initialRisk;
    }
}
