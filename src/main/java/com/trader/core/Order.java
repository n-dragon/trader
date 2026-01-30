package com.trader.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents a trading order to be executed.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private String clientOrderId;
    private String symbol;
    private OrderType type;
    private OrderSide side;
    private double price;
    private double quantity;
    private double stopPrice;
    private double stopLoss;
    private double takeProfit;
    private TimeInForce timeInForce;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private double executedQuantity;
    private double executedPrice;
    private double commission;
    private String commissionAsset;

    public enum OrderType {
        MARKET,
        LIMIT,
        STOP_LOSS,
        STOP_LOSS_LIMIT,
        TAKE_PROFIT,
        TAKE_PROFIT_LIMIT,
        LIMIT_MAKER
    }

    public enum OrderSide {
        BUY,
        SELL
    }

    public enum TimeInForce {
        GTC,  // Good Till Cancel
        IOC,  // Immediate Or Cancel
        FOK   // Fill Or Kill
    }

    public enum OrderStatus {
        NEW,
        PARTIALLY_FILLED,
        FILLED,
        CANCELED,
        PENDING_CANCEL,
        REJECTED,
        EXPIRED
    }

    /**
     * Returns true if the order is still active.
     */
    public boolean isActive() {
        return status == OrderStatus.NEW || status == OrderStatus.PARTIALLY_FILLED;
    }

    /**
     * Returns true if the order has been completely filled.
     */
    public boolean isFilled() {
        return status == OrderStatus.FILLED;
    }

    /**
     * Returns the remaining quantity to fill.
     */
    public double getRemainingQuantity() {
        return quantity - executedQuantity;
    }

    /**
     * Returns the fill percentage.
     */
    public double getFillPercent() {
        if (quantity == 0) return 0;
        return (executedQuantity / quantity) * 100;
    }

    /**
     * Returns the total value of the order.
     */
    public double getTotalValue() {
        if (type == OrderType.MARKET) {
            return executedPrice * executedQuantity;
        }
        return price * quantity;
    }
}
