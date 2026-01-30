package com.trader.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents an open trading position.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private String id;
    private String symbol;
    private Trade.TradeType type;
    private double entryPrice;
    private double quantity;
    private Instant entryTime;
    private double stopLossPrice;
    private double takeProfitPrice;
    private double trailingStopDistance;
    private double trailingStopActivation;
    private double highestPrice;
    private double lowestPrice;
    private PositionStatus status;

    public enum PositionStatus {
        PENDING,
        OPEN,
        CLOSING,
        CLOSED
    }

    /**
     * Calculates unrealized P&L at current price.
     */
    public double getUnrealizedPnL(double currentPrice) {
        if (type == Trade.TradeType.LONG) {
            return (currentPrice - entryPrice) * quantity;
        } else {
            return (entryPrice - currentPrice) * quantity;
        }
    }

    /**
     * Calculates unrealized P&L percentage.
     */
    public double getUnrealizedPnLPercent(double currentPrice) {
        if (entryPrice == 0) return 0;
        if (type == Trade.TradeType.LONG) {
            return ((currentPrice - entryPrice) / entryPrice) * 100;
        } else {
            return ((entryPrice - currentPrice) / entryPrice) * 100;
        }
    }

    /**
     * Updates the trailing stop based on current price.
     * Returns true if trailing stop should be updated.
     */
    public boolean updateTrailingStop(double currentPrice) {
        if (trailingStopDistance <= 0) return false;

        double pnlPercent = getUnrealizedPnLPercent(currentPrice);

        // Only activate trailing stop if activation threshold is reached
        if (pnlPercent < trailingStopActivation) return false;

        if (type == Trade.TradeType.LONG) {
            if (currentPrice > highestPrice) {
                highestPrice = currentPrice;
                double newStopLoss = currentPrice * (1 - trailingStopDistance / 100);
                if (newStopLoss > stopLossPrice) {
                    stopLossPrice = newStopLoss;
                    return true;
                }
            }
        } else {
            if (currentPrice < lowestPrice || lowestPrice == 0) {
                lowestPrice = currentPrice;
                double newStopLoss = currentPrice * (1 + trailingStopDistance / 100);
                if (newStopLoss < stopLossPrice || stopLossPrice == 0) {
                    stopLossPrice = newStopLoss;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if stop loss has been triggered.
     */
    public boolean isStopLossTriggered(double currentPrice) {
        if (stopLossPrice <= 0) return false;

        if (type == Trade.TradeType.LONG) {
            return currentPrice <= stopLossPrice;
        } else {
            return currentPrice >= stopLossPrice;
        }
    }

    /**
     * Checks if take profit has been reached.
     */
    public boolean isTakeProfitReached(double currentPrice) {
        if (takeProfitPrice <= 0) return false;

        if (type == Trade.TradeType.LONG) {
            return currentPrice >= takeProfitPrice;
        } else {
            return currentPrice <= takeProfitPrice;
        }
    }

    /**
     * Gets the position value at entry.
     */
    public double getEntryValue() {
        return entryPrice * quantity;
    }

    /**
     * Gets the current position value.
     */
    public double getCurrentValue(double currentPrice) {
        return currentPrice * quantity;
    }

    /**
     * Converts this position to a closed trade.
     */
    public Trade closePosition(double exitPrice, String exitReason) {
        double profit = getUnrealizedPnL(exitPrice);
        double profitPercent = getUnrealizedPnLPercent(exitPrice);

        return Trade.builder()
                .symbol(symbol)
                .type(type)
                .entryPrice(entryPrice)
                .exitPrice(exitPrice)
                .quantity(quantity)
                .entryTime(entryTime)
                .exitTime(Instant.now())
                .profit(profit)
                .profitPercent(profitPercent)
                .exitReason(exitReason)
                .stopLossPrice(stopLossPrice)
                .takeProfitPrice(takeProfitPrice)
                .build();
    }
}
