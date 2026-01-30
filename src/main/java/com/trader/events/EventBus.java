package com.trader.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Event-driven architecture implementation for the trading system.
 * Provides publish-subscribe pattern for decoupled components.
 */
public class EventBus {
    private static final Logger logger = LoggerFactory.getLogger(EventBus.class);

    private final Map<Class<? extends TradingEvent>, List<EventSubscription<?>>> subscribers;
    private final ExecutorService asyncExecutor;
    private final BlockingQueue<EventTask> eventQueue;
    private final Thread eventDispatcher;
    private volatile boolean running;

    public EventBus() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public EventBus(int threadPoolSize) {
        this.subscribers = new ConcurrentHashMap<>();
        this.asyncExecutor = Executors.newFixedThreadPool(threadPoolSize, r -> {
            Thread t = new Thread(r, "event-bus-worker");
            t.setDaemon(true);
            return t;
        });
        this.eventQueue = new LinkedBlockingQueue<>();
        this.running = true;

        // Start event dispatcher thread
        this.eventDispatcher = new Thread(this::dispatchEvents, "event-bus-dispatcher");
        this.eventDispatcher.setDaemon(true);
        this.eventDispatcher.start();
    }

    /**
     * Subscribes to events of a specific type.
     */
    @SuppressWarnings("unchecked")
    public <T extends TradingEvent> String subscribe(Class<T> eventType, Consumer<T> handler) {
        return subscribe(eventType, handler, false);
    }

    /**
     * Subscribes to events with option for async handling.
     */
    @SuppressWarnings("unchecked")
    public <T extends TradingEvent> String subscribe(Class<T> eventType, Consumer<T> handler, boolean async) {
        String subscriptionId = UUID.randomUUID().toString();

        EventSubscription<T> subscription = new EventSubscription<>(
                subscriptionId,
                eventType,
                handler,
                async
        );

        subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                .add(subscription);

        logger.debug("Subscribed to {} (id: {}, async: {})", eventType.getSimpleName(), subscriptionId, async);

        return subscriptionId;
    }

    /**
     * Unsubscribes from events.
     */
    public void unsubscribe(String subscriptionId) {
        for (List<EventSubscription<?>> subs : subscribers.values()) {
            subs.removeIf(s -> s.getId().equals(subscriptionId));
        }
        logger.debug("Unsubscribed: {}", subscriptionId);
    }

    /**
     * Publishes an event synchronously.
     */
    public void publish(TradingEvent event) {
        publish(event, false);
    }

    /**
     * Publishes an event with option for async delivery.
     */
    @SuppressWarnings("unchecked")
    public void publish(TradingEvent event, boolean async) {
        if (event == null) {
            logger.warn("Attempted to publish null event");
            return;
        }

        event.setPublishedAt(Instant.now());

        List<EventSubscription<?>> eventSubscribers = subscribers.get(event.getClass());
        if (eventSubscribers == null || eventSubscribers.isEmpty()) {
            logger.trace("No subscribers for event: {}", event.getClass().getSimpleName());
            return;
        }

        logger.debug("Publishing event: {} to {} subscribers", event.getClass().getSimpleName(), eventSubscribers.size());

        if (async) {
            eventQueue.offer(new EventTask(event, eventSubscribers));
        } else {
            deliverEvent(event, eventSubscribers);
        }
    }

    /**
     * Publishes an event asynchronously.
     */
    public void publishAsync(TradingEvent event) {
        publish(event, true);
    }

    /**
     * Delivers event to subscribers.
     */
    @SuppressWarnings("unchecked")
    private void deliverEvent(TradingEvent event, List<EventSubscription<?>> eventSubscribers) {
        for (EventSubscription subscription : eventSubscribers) {
            try {
                if (subscription.isAsync()) {
                    asyncExecutor.submit(() -> {
                        try {
                            subscription.getHandler().accept(event);
                        } catch (Exception e) {
                            logger.error("Async handler error for {}: {}", event.getClass().getSimpleName(), e.getMessage(), e);
                        }
                    });
                } else {
                    subscription.getHandler().accept(event);
                }
            } catch (Exception e) {
                logger.error("Error delivering event {}: {}", event.getClass().getSimpleName(), e.getMessage(), e);
            }
        }
    }

    /**
     * Background event dispatcher.
     */
    private void dispatchEvents() {
        while (running) {
            try {
                EventTask task = eventQueue.poll(100, TimeUnit.MILLISECONDS);
                if (task != null) {
                    deliverEvent(task.event, task.subscribers);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Shuts down the event bus.
     */
    public void shutdown() {
        running = false;
        eventDispatcher.interrupt();
        asyncExecutor.shutdown();
        try {
            if (!asyncExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                asyncExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            asyncExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        logger.info("EventBus shutdown complete");
    }

    /**
     * Gets subscriber count for an event type.
     */
    public int getSubscriberCount(Class<? extends TradingEvent> eventType) {
        List<EventSubscription<?>> subs = subscribers.get(eventType);
        return subs != null ? subs.size() : 0;
    }

    /**
     * Gets total subscriber count.
     */
    public int getTotalSubscriberCount() {
        return subscribers.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    /**
     * Internal subscription holder.
     */
    private static class EventSubscription<T extends TradingEvent> {
        private final String id;
        private final Class<T> eventType;
        private final Consumer<T> handler;
        private final boolean async;

        public EventSubscription(String id, Class<T> eventType, Consumer<T> handler, boolean async) {
            this.id = id;
            this.eventType = eventType;
            this.handler = handler;
            this.async = async;
        }

        public String getId() {
            return id;
        }

        public Class<T> getEventType() {
            return eventType;
        }

        public Consumer<T> getHandler() {
            return handler;
        }

        public boolean isAsync() {
            return async;
        }
    }

    /**
     * Internal event task for async processing.
     */
    private static class EventTask {
        final TradingEvent event;
        final List<EventSubscription<?>> subscribers;

        EventTask(TradingEvent event, List<EventSubscription<?>> subscribers) {
            this.event = event;
            this.subscribers = subscribers;
        }
    }
}
