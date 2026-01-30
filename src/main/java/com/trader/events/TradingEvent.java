package com.trader.events;

import lombok.Data;

import java.time.Instant;

/**
 * Base class for all trading events.
 */
@Data
public abstract class TradingEvent {
    private String eventId;
    private Instant publishedAt;
    private String source;

    protected TradingEvent() {
        this.eventId = java.util.UUID.randomUUID().toString();
    }

    protected TradingEvent(String source) {
        this();
        this.source = source;
    }
}
