package com.trader.events;

import com.trader.core.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.Map;

/**
 * Collection of concrete trading event types.
 */
public class TradingEvents {

    /**
     * Event published when a new price update is received.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class PriceUpdateEvent extends TradingEvent {
        private String symbol;
        private double price;
        private double bidPrice;
        private double askPrice;
        private double volume;
        private Exchange exchange;
        private Instant timestamp;

        @Builder
        public PriceUpdateEvent(String symbol, double price, double bidPrice, double askPrice,
                                double volume, Exchange exchange, Instant timestamp) {
            super("price-feed");
            this.symbol = symbol;
            this.price = price;
            this.bidPrice = bidPrice;
            this.askPrice = askPrice;
            this.volume = volume;
            this.exchange = exchange;
            this.timestamp = timestamp;
        }
    }

    /**
     * Event published when a new candle is formed.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CandleClosedEvent extends TradingEvent {
        private Candle candle;

        @Builder
        public CandleClosedEvent(Candle candle) {
            super("candle-aggregator");
            this.candle = candle;
        }
    }

    /**
     * Event published when a trading signal is detected.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SignalDetectedEvent extends TradingEvent {
        private TradingSignal signal;

        @Builder
        public SignalDetectedEvent(TradingSignal signal) {
            super("signal-generator");
            this.signal = signal;
        }
    }

    /**
     * Event published when a position is opened.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class PositionOpenedEvent extends TradingEvent {
        private Position position;

        @Builder
        public PositionOpenedEvent(Position position) {
            super("position-manager");
            this.position = position;
        }
    }

    /**
     * Event published when a position is closed.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class PositionClosedEvent extends TradingEvent {
        private Trade trade;
        private String closeReason;

        @Builder
        public PositionClosedEvent(Trade trade, String closeReason) {
            super("position-manager");
            this.trade = trade;
            this.closeReason = closeReason;
        }
    }

    /**
     * Event published when a stop-loss is triggered.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class StopLossTriggeredEvent extends TradingEvent {
        private Position position;
        private double triggerPrice;

        @Builder
        public StopLossTriggeredEvent(Position position, double triggerPrice) {
            super("risk-manager");
            this.position = position;
            this.triggerPrice = triggerPrice;
        }
    }

    /**
     * Event published when circuit breaker is activated.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CircuitBreakerActivatedEvent extends TradingEvent {
        private String reason;
        private int consecutiveLosses;
        private double dailyLoss;

        @Builder
        public CircuitBreakerActivatedEvent(String reason, int consecutiveLosses, double dailyLoss) {
            super("circuit-breaker");
            this.reason = reason;
            this.consecutiveLosses = consecutiveLosses;
            this.dailyLoss = dailyLoss;
        }
    }

    /**
     * Event published when prices are validated across exchanges.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class PriceConsensusEvent extends TradingEvent {
        private String symbol;
        private double consensusPrice;
        private Map<Exchange, Double> exchangePrices;
        private boolean valid;

        @Builder
        public PriceConsensusEvent(String symbol, double consensusPrice,
                                   Map<Exchange, Double> exchangePrices, boolean valid) {
            super("multi-exchange-validator");
            this.symbol = symbol;
            this.consensusPrice = consensusPrice;
            this.exchangePrices = exchangePrices;
            this.valid = valid;
        }
    }

    /**
     * Event published for system health updates.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SystemHealthEvent extends TradingEvent {
        private boolean healthy;
        private Map<String, Object> metrics;
        private String message;

        @Builder
        public SystemHealthEvent(boolean healthy, Map<String, Object> metrics, String message) {
            super("health-monitor");
            this.healthy = healthy;
            this.metrics = metrics;
            this.message = message;
        }
    }

    /**
     * Event published for errors.
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ErrorEvent extends TradingEvent {
        private String errorType;
        private String message;
        private Throwable exception;
        private boolean critical;

        @Builder
        public ErrorEvent(String errorType, String message, Throwable exception, boolean critical) {
            super("error-handler");
            this.errorType = errorType;
            this.message = message;
            this.exception = exception;
            this.critical = critical;
        }
    }
}
