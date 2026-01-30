package com.trader.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents a candlestick (OHLCV) data point.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candle {
    private String symbol;
    private Instant openTime;
    private Instant closeTime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private double quoteVolume;
    private int numberOfTrades;

    /**
     * Returns the body size of the candle (absolute difference between open and close).
     */
    public double getBodySize() {
        return Math.abs(close - open);
    }

    /**
     * Returns the upper shadow size.
     */
    public double getUpperShadow() {
        return high - Math.max(open, close);
    }

    /**
     * Returns the lower shadow size.
     */
    public double getLowerShadow() {
        return Math.min(open, close) - low;
    }

    /**
     * Returns true if this is a bullish (green) candle.
     */
    public boolean isBullish() {
        return close > open;
    }

    /**
     * Returns true if this is a bearish (red) candle.
     */
    public boolean isBearish() {
        return close < open;
    }

    /**
     * Returns the price range (high - low).
     */
    public double getRange() {
        return high - low;
    }

    /**
     * Returns the typical price ((high + low + close) / 3).
     */
    public double getTypicalPrice() {
        return (high + low + close) / 3.0;
    }

    /**
     * Returns the change percentage from open to close.
     */
    public double getChangePercent() {
        if (open == 0) return 0;
        return ((close - open) / open) * 100;
    }
}
