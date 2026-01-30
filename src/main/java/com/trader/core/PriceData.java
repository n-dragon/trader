package com.trader.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents price data from an exchange.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceData {
    private Exchange exchange;
    private String symbol;
    private double price;
    private double bidPrice;
    private double askPrice;
    private double volume;
    private double volume24h;
    private Instant timestamp;
    private long latencyMs;

    /**
     * Returns the bid-ask spread.
     */
    public double getSpread() {
        return askPrice - bidPrice;
    }

    /**
     * Returns the bid-ask spread as a percentage.
     */
    public double getSpreadPercent() {
        if (bidPrice == 0) return 0;
        return (getSpread() / bidPrice) * 100;
    }

    /**
     * Returns the mid price (average of bid and ask).
     */
    public double getMidPrice() {
        return (bidPrice + askPrice) / 2;
    }

    /**
     * Checks if this price data is stale (older than maxAge milliseconds).
     */
    public boolean isStale(long maxAgeMs) {
        if (timestamp == null) return true;
        return Instant.now().toEpochMilli() - timestamp.toEpochMilli() > maxAgeMs;
    }
}
