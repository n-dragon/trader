package com.trader.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Intelligent rate limiter for API requests to avoid hitting exchange limits.
 */
public class RateLimiter {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiter.class);

    private final int maxRequestsPerMinute;
    private final int safetyMargin;
    private final Queue<Long> requestTimestamps;
    private final ConcurrentHashMap<String, AtomicInteger> endpointCounters;
    private final ConcurrentHashMap<String, Integer> endpointLimits;

    public RateLimiter(int maxRequestsPerMinute) {
        this(maxRequestsPerMinute, 100);
    }

    public RateLimiter(int maxRequestsPerMinute, int safetyMargin) {
        this.maxRequestsPerMinute = maxRequestsPerMinute > 0 ? maxRequestsPerMinute : 1200;
        this.safetyMargin = safetyMargin;
        this.requestTimestamps = new ConcurrentLinkedQueue<>();
        this.endpointCounters = new ConcurrentHashMap<>();
        this.endpointLimits = new ConcurrentHashMap<>();
    }

    /**
     * Checks if a request is allowed based on rate limits.
     */
    public boolean allowRequest() {
        return allowRequest("default");
    }

    /**
     * Checks if a request to a specific endpoint is allowed.
     */
    public boolean allowRequest(String endpoint) {
        long now = System.currentTimeMillis();
        cleanupOldTimestamps(now);

        // Check global limit
        int currentCount = requestTimestamps.size();
        int effectiveLimit = maxRequestsPerMinute - safetyMargin;

        if (currentCount >= effectiveLimit) {
            logger.warn("Global rate limit approaching: {}/{} (limit: {})",
                    currentCount, maxRequestsPerMinute, effectiveLimit);
            return false;
        }

        // Check endpoint-specific limit
        Integer endpointLimit = endpointLimits.get(endpoint);
        if (endpointLimit != null) {
            AtomicInteger endpointCount = endpointCounters.computeIfAbsent(endpoint, k -> new AtomicInteger(0));
            if (endpointCount.get() >= endpointLimit) {
                logger.warn("Endpoint rate limit reached for {}: {}/{}",
                        endpoint, endpointCount.get(), endpointLimit);
                return false;
            }
        }

        return true;
    }

    /**
     * Records a request being made.
     */
    public void recordRequest() {
        recordRequest("default");
    }

    /**
     * Records a request to a specific endpoint.
     */
    public void recordRequest(String endpoint) {
        long now = System.currentTimeMillis();
        requestTimestamps.offer(now);

        AtomicInteger endpointCount = endpointCounters.computeIfAbsent(endpoint, k -> new AtomicInteger(0));
        endpointCount.incrementAndGet();
    }

    /**
     * Waits until a request is allowed (blocking).
     */
    public void waitForPermission() throws InterruptedException {
        waitForPermission("default");
    }

    /**
     * Waits until a request to a specific endpoint is allowed (blocking).
     */
    public void waitForPermission(String endpoint) throws InterruptedException {
        while (!allowRequest(endpoint)) {
            long waitTime = calculateWaitTime();
            logger.debug("Rate limited, waiting {}ms", waitTime);
            Thread.sleep(waitTime);
        }
    }

    /**
     * Tries to acquire permission, with timeout.
     */
    public boolean tryAcquire(long timeoutMs) {
        return tryAcquire("default", timeoutMs);
    }

    /**
     * Tries to acquire permission for endpoint, with timeout.
     */
    public boolean tryAcquire(String endpoint, long timeoutMs) {
        long deadline = System.currentTimeMillis() + timeoutMs;

        while (!allowRequest(endpoint)) {
            if (System.currentTimeMillis() >= deadline) {
                return false;
            }
            try {
                Thread.sleep(Math.min(100, deadline - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        recordRequest(endpoint);
        return true;
    }

    /**
     * Sets a specific limit for an endpoint.
     */
    public void setEndpointLimit(String endpoint, int limit) {
        endpointLimits.put(endpoint, limit);
    }

    /**
     * Gets the current request count.
     */
    public int getCurrentRequestCount() {
        cleanupOldTimestamps(System.currentTimeMillis());
        return requestTimestamps.size();
    }

    /**
     * Gets the remaining requests before limit.
     */
    public int getRemainingRequests() {
        return Math.max(0, maxRequestsPerMinute - safetyMargin - getCurrentRequestCount());
    }

    /**
     * Gets the usage percentage.
     */
    public double getUsagePercent() {
        return (double) getCurrentRequestCount() / maxRequestsPerMinute * 100;
    }

    /**
     * Resets the rate limiter.
     */
    public void reset() {
        requestTimestamps.clear();
        endpointCounters.clear();
    }

    /**
     * Resets counters for a specific endpoint.
     */
    public void resetEndpoint(String endpoint) {
        endpointCounters.remove(endpoint);
    }

    /**
     * Calculates how long to wait before making another request.
     */
    private long calculateWaitTime() {
        if (requestTimestamps.isEmpty()) {
            return 0;
        }

        Long oldestTimestamp = requestTimestamps.peek();
        if (oldestTimestamp == null) {
            return 0;
        }

        // Wait until oldest request expires (>1 minute old)
        long waitTime = 60000 - (System.currentTimeMillis() - oldestTimestamp);
        return Math.max(100, Math.min(waitTime, 5000)); // Wait at least 100ms, at most 5s
    }

    /**
     * Removes timestamps older than 1 minute.
     */
    private void cleanupOldTimestamps(long now) {
        while (!requestTimestamps.isEmpty()) {
            Long oldest = requestTimestamps.peek();
            if (oldest != null && now - oldest > 60000) {
                requestTimestamps.poll();
            } else {
                break;
            }
        }

        // Reset endpoint counters periodically
        if (requestTimestamps.isEmpty()) {
            endpointCounters.clear();
        }
    }

    /**
     * Gets rate limiter statistics.
     */
    public RateLimiterStats getStats() {
        return RateLimiterStats.builder()
                .maxRequestsPerMinute(maxRequestsPerMinute)
                .currentRequestCount(getCurrentRequestCount())
                .remainingRequests(getRemainingRequests())
                .usagePercent(getUsagePercent())
                .safetyMargin(safetyMargin)
                .timestamp(Instant.now())
                .build();
    }

    @lombok.Data
    @lombok.Builder
    public static class RateLimiterStats {
        private int maxRequestsPerMinute;
        private int currentRequestCount;
        private int remainingRequests;
        private double usagePercent;
        private int safetyMargin;
        private Instant timestamp;
    }
}
