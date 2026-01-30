package com.trader.validation;

import com.trader.core.Exchange;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Manages network latency measurement and time synchronization for trading operations.
 */
public class LatencyManager {
    private static final Logger logger = LoggerFactory.getLogger(LatencyManager.class);

    private static final long MAX_ACCEPTABLE_LATENCY_MS = 200;
    private static final long CLOCK_SYNC_INTERVAL_MS = 60000;
    private static final String[] NTP_SERVERS = {
            "time.google.com",
            "time.cloudflare.com",
            "pool.ntp.org"
    };

    private final ConcurrentHashMap<Exchange, LatencyStats> latencyMap;
    private final ScheduledExecutorService scheduler;
    private volatile long clockOffset;
    private volatile Instant lastClockSync;

    public LatencyManager() {
        this.latencyMap = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(2, r -> {
            Thread t = new Thread(r, "latency-manager");
            t.setDaemon(true);
            return t;
        });
        this.clockOffset = 0;
        this.lastClockSync = null;

        // Start periodic clock synchronization
        scheduler.scheduleAtFixedRate(
                this::synchronizeSystemClock,
                0,
                CLOCK_SYNC_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * Synchronizes the local clock with NTP servers.
     */
    public void synchronizeSystemClock() {
        NTPUDPClient client = new NTPUDPClient();
        client.setDefaultTimeout(5000);

        for (String ntpServer : NTP_SERVERS) {
            try {
                InetAddress ntpAddress = InetAddress.getByName(ntpServer);
                TimeInfo timeInfo = client.getTime(ntpAddress);
                timeInfo.computeDetails();

                Long offset = timeInfo.getOffset();
                if (offset != null) {
                    clockOffset = offset;
                    lastClockSync = Instant.now();

                    if (Math.abs(offset) > 1000) {
                        logger.warn("Significant clock offset detected: {}ms from NTP server {}",
                                offset, ntpServer);
                    } else {
                        logger.debug("Clock synchronized with {}, offset: {}ms", ntpServer, offset);
                    }
                    return;
                }
            } catch (IOException e) {
                logger.warn("Failed to connect to NTP server {}: {}", ntpServer, e.getMessage());
            }
        }

        logger.error("Failed to synchronize clock with any NTP server");
        client.close();
    }

    /**
     * Measures latency to an exchange using a ping-like operation.
     *
     * @param exchange     The exchange to measure
     * @param pingFunction A function that performs a lightweight API call
     */
    public void measureLatency(Exchange exchange, Runnable pingFunction) {
        long start = System.nanoTime();

        try {
            pingFunction.run();
            long latencyMs = (System.nanoTime() - start) / 1_000_000;

            latencyMap.computeIfAbsent(exchange, k -> new LatencyStats())
                    .addMeasurement(latencyMs);

            if (latencyMs > MAX_ACCEPTABLE_LATENCY_MS) {
                logger.warn("High latency detected for {}: {}ms", exchange, latencyMs);
            } else {
                logger.debug("Latency for {}: {}ms", exchange, latencyMs);
            }
        } catch (Exception e) {
            logger.error("Failed to measure latency for {}: {}", exchange, e.getMessage());
        }
    }

    /**
     * Measures latency to an exchange asynchronously.
     */
    public CompletableFuture<Long> measureLatencyAsync(Exchange exchange, Callable<Void> pingFunction) {
        return CompletableFuture.supplyAsync(() -> {
            long start = System.nanoTime();
            try {
                pingFunction.call();
                long latencyMs = (System.nanoTime() - start) / 1_000_000;

                latencyMap.computeIfAbsent(exchange, k -> new LatencyStats())
                        .addMeasurement(latencyMs);

                return latencyMs;
            } catch (Exception e) {
                logger.error("Async latency measurement failed for {}: {}", exchange, e.getMessage());
                return -1L;
            }
        }, scheduler);
    }

    /**
     * Gets the latency statistics for an exchange.
     */
    public LatencyStats getLatencyStats(Exchange exchange) {
        return latencyMap.getOrDefault(exchange, new LatencyStats());
    }

    /**
     * Gets the average latency for an exchange.
     */
    public double getAverageLatency(Exchange exchange) {
        LatencyStats stats = latencyMap.get(exchange);
        return stats != null ? stats.getAverageLatency() : -1;
    }

    /**
     * Checks if an exchange has acceptable latency.
     */
    public boolean hasAcceptableLatency(Exchange exchange) {
        LatencyStats stats = latencyMap.get(exchange);
        if (stats == null) return true; // No data yet, assume OK
        return stats.getAverageLatency() <= MAX_ACCEPTABLE_LATENCY_MS;
    }

    /**
     * Gets the current clock offset from NTP servers.
     */
    public long getClockOffset() {
        return clockOffset;
    }

    /**
     * Gets the corrected current time (adjusted for clock offset).
     */
    public Instant getCorrectedTime() {
        return Instant.now().plusMillis(clockOffset);
    }

    /**
     * Gets time since last clock synchronization.
     */
    public Duration getTimeSinceLastSync() {
        if (lastClockSync == null) return Duration.ofDays(999);
        return Duration.between(lastClockSync, Instant.now());
    }

    /**
     * Gets all latency data.
     */
    public ConcurrentHashMap<Exchange, LatencyStats> getAllLatencyStats() {
        return new ConcurrentHashMap<>(latencyMap);
    }

    /**
     * Shuts down the latency manager.
     */
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Maintains latency statistics for an exchange.
     */
    public static class LatencyStats {
        private static final int MAX_SAMPLES = 100;
        private final List<Long> measurements;
        private long minLatency = Long.MAX_VALUE;
        private long maxLatency = Long.MIN_VALUE;
        private Instant lastMeasurement;

        public LatencyStats() {
            this.measurements = Collections.synchronizedList(new ArrayList<>());
        }

        public void addMeasurement(long latencyMs) {
            measurements.add(latencyMs);
            if (measurements.size() > MAX_SAMPLES) {
                measurements.remove(0);
            }

            minLatency = Math.min(minLatency, latencyMs);
            maxLatency = Math.max(maxLatency, latencyMs);
            lastMeasurement = Instant.now();
        }

        public double getAverageLatency() {
            if (measurements.isEmpty()) return 0;
            return measurements.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0);
        }

        public double getMedianLatency() {
            if (measurements.isEmpty()) return 0;
            List<Long> sorted = new ArrayList<>(measurements);
            Collections.sort(sorted);
            int mid = sorted.size() / 2;
            if (sorted.size() % 2 == 0) {
                return (sorted.get(mid - 1) + sorted.get(mid)) / 2.0;
            }
            return sorted.get(mid);
        }

        public double getP95Latency() {
            if (measurements.isEmpty()) return 0;
            List<Long> sorted = new ArrayList<>(measurements);
            Collections.sort(sorted);
            int index = (int) Math.ceil(0.95 * sorted.size()) - 1;
            return sorted.get(Math.max(0, index));
        }

        public long getMinLatency() {
            return minLatency == Long.MAX_VALUE ? 0 : minLatency;
        }

        public long getMaxLatency() {
            return maxLatency == Long.MIN_VALUE ? 0 : maxLatency;
        }

        public int getSampleCount() {
            return measurements.size();
        }

        public Instant getLastMeasurement() {
            return lastMeasurement;
        }

        public LatencyReport getReport() {
            return LatencyReport.builder()
                    .sampleCount(getSampleCount())
                    .averageLatency(getAverageLatency())
                    .medianLatency(getMedianLatency())
                    .p95Latency(getP95Latency())
                    .minLatency(getMinLatency())
                    .maxLatency(getMaxLatency())
                    .lastMeasurement(lastMeasurement)
                    .build();
        }
    }

    @Data
    @Builder
    public static class LatencyReport {
        private int sampleCount;
        private double averageLatency;
        private double medianLatency;
        private double p95Latency;
        private long minLatency;
        private long maxLatency;
        private Instant lastMeasurement;
    }
}
