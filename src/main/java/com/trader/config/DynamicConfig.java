package com.trader.config;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Dynamic configuration that supports hot-reloading without restart.
 */
public class DynamicConfig {
    private static final Logger logger = LoggerFactory.getLogger(DynamicConfig.class);

    private static final long DEFAULT_RELOAD_INTERVAL_MS = 60000; // 1 minute

    private final String configPath;
    private final long reloadIntervalMs;
    private final ConcurrentHashMap<String, String> cache;
    private final CopyOnWriteArrayList<Consumer<DynamicConfig>> changeListeners;
    private final ScheduledExecutorService scheduler;

    private volatile Properties properties;
    private volatile long lastReloadTime;
    private volatile long configFileLastModified;

    public DynamicConfig(String configPath) {
        this(configPath, DEFAULT_RELOAD_INTERVAL_MS);
    }

    public DynamicConfig(String configPath, long reloadIntervalMs) {
        this.configPath = configPath;
        this.reloadIntervalMs = reloadIntervalMs;
        this.properties = new Properties();
        this.cache = new ConcurrentHashMap<>();
        this.changeListeners = new CopyOnWriteArrayList<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "config-reloader");
            t.setDaemon(true);
            return t;
        });

        // Initial load
        loadConfig();

        // Schedule periodic reload checks
        scheduler.scheduleAtFixedRate(
                this::reloadIfChanged,
                reloadIntervalMs,
                reloadIntervalMs,
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * Loads the configuration file.
     */
    private synchronized void loadConfig() {
        try {
            File configFile = new File(configPath);
            if (!configFile.exists()) {
                logger.warn("Config file not found: {}", configPath);
                return;
            }

            Properties newProps = new Properties();
            try (FileInputStream fis = new FileInputStream(configFile)) {
                newProps.load(fis);
            }

            properties = newProps;
            cache.clear();
            lastReloadTime = System.currentTimeMillis();
            configFileLastModified = configFile.lastModified();

            logger.info("Configuration loaded from {}", configPath);

            // Notify listeners
            notifyChangeListeners();
        } catch (IOException e) {
            logger.error("Failed to load config from {}: {}", configPath, e.getMessage());
        }
    }

    /**
     * Reloads configuration if the file has been modified.
     */
    private void reloadIfChanged() {
        try {
            File configFile = new File(configPath);
            if (configFile.exists() && configFile.lastModified() > configFileLastModified) {
                logger.info("Config file changed, reloading...");
                loadConfig();
            }
        } catch (Exception e) {
            logger.error("Error checking config file: {}", e.getMessage());
        }
    }

    /**
     * Forces a configuration reload.
     */
    public void reload() {
        loadConfig();
    }

    /**
     * Gets a string property.
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * Gets a string property with default value.
     */
    public String getString(String key, String defaultValue) {
        String cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        String value = properties.getProperty(key, defaultValue);
        if (value != null) {
            cache.put(key, value);
        }
        return value;
    }

    /**
     * Gets an integer property.
     */
    public int getInt(String key, int defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer value for {}: {}", key, value);
            return defaultValue;
        }
    }

    /**
     * Gets a double property.
     */
    public double getDouble(String key, double defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            logger.warn("Invalid double value for {}: {}", key, value);
            return defaultValue;
        }
    }

    /**
     * Gets a boolean property.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }

    /**
     * Gets a long property.
     */
    public long getLong(String key, long defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            logger.warn("Invalid long value for {}: {}", key, value);
            return defaultValue;
        }
    }

    /**
     * Gets trading configuration for a specific symbol.
     */
    public TradingConfig getTradingConfig(String symbol) {
        String prefix = symbol + ".";

        return TradingConfig.builder()
                .symbol(symbol)
                .enabled(getBoolean(prefix + "enabled", true))
                .windowSize(getInt(prefix + "window_size", 10))
                .threshold(getDouble(prefix + "threshold", 0.5))
                .maxPositionSize(getDouble(prefix + "max_position", 2.0))
                .stopLossPercent(getDouble(prefix + "stop_loss", 1.5))
                .takeProfitPercent(getDouble(prefix + "take_profit", 3.0))
                .minVolume(getDouble(prefix + "min_volume", 0))
                .build();
    }

    /**
     * Gets risk management configuration.
     */
    public RiskConfig getRiskConfig() {
        return RiskConfig.builder()
                .maxDailyLossPercent(getDouble("risk.max_daily_loss", 5.0))
                .maxDrawdownPercent(getDouble("risk.max_drawdown", 15.0))
                .circuitBreakerEnabled(getBoolean("risk.circuit_breaker.enabled", true))
                .maxConsecutiveLosses(getInt("risk.circuit_breaker.consecutive_losses", 5))
                .maxPositionSizePercent(getDouble("risk.max_position_size", 2.0))
                .build();
    }

    /**
     * Gets notification configuration.
     */
    public NotificationConfig getNotificationConfig() {
        return NotificationConfig.builder()
                .enabled(getBoolean("notifications.enabled", false))
                .telegramEnabled(getBoolean("notifications.telegram.enabled", false))
                .telegramToken(getString("notifications.telegram.token", ""))
                .telegramChatId(getString("notifications.telegram.chat_id", ""))
                .emailEnabled(getBoolean("notifications.email.enabled", false))
                .emailSmtpHost(getString("notifications.email.smtp_host", ""))
                .emailSmtpPort(getInt("notifications.email.smtp_port", 587))
                .emailUsername(getString("notifications.email.username", ""))
                .emailPassword(getString("notifications.email.password", ""))
                .emailRecipient(getString("notifications.email.recipient", ""))
                .build();
    }

    /**
     * Checks if a symbol is enabled for trading.
     */
    public boolean isSymbolEnabled(String symbol) {
        return getBoolean(symbol + ".enabled", false);
    }

    /**
     * Gets the trading mode (paper or live).
     */
    public String getTradingMode() {
        return getString("trading.mode", "paper");
    }

    /**
     * Checks if in paper trading mode.
     */
    public boolean isPaperTrading() {
        return "paper".equalsIgnoreCase(getTradingMode());
    }

    /**
     * Gets the total trading capital.
     */
    public double getTotalCapital() {
        return getDouble("total.capital", 10000);
    }

    /**
     * Gets maximum concurrent trades allowed.
     */
    public int getMaxConcurrentTrades() {
        return getInt("max.concurrent.trades", 3);
    }

    /**
     * Adds a change listener.
     */
    public void addChangeListener(Consumer<DynamicConfig> listener) {
        changeListeners.add(listener);
    }

    /**
     * Removes a change listener.
     */
    public void removeChangeListener(Consumer<DynamicConfig> listener) {
        changeListeners.remove(listener);
    }

    private void notifyChangeListeners() {
        for (Consumer<DynamicConfig> listener : changeListeners) {
            try {
                listener.accept(this);
            } catch (Exception e) {
                logger.error("Error notifying config change listener: {}", e.getMessage());
            }
        }
    }

    /**
     * Shuts down the configuration manager.
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

    // Configuration data classes
    @Data
    @Builder
    public static class TradingConfig {
        private String symbol;
        private boolean enabled;
        private int windowSize;
        private double threshold;
        private double maxPositionSize;
        private double stopLossPercent;
        private double takeProfitPercent;
        private double minVolume;
    }

    @Data
    @Builder
    public static class RiskConfig {
        private double maxDailyLossPercent;
        private double maxDrawdownPercent;
        private boolean circuitBreakerEnabled;
        private int maxConsecutiveLosses;
        private double maxPositionSizePercent;
    }

    @Data
    @Builder
    public static class NotificationConfig {
        private boolean enabled;
        private boolean telegramEnabled;
        private String telegramToken;
        private String telegramChatId;
        private boolean emailEnabled;
        private String emailSmtpHost;
        private int emailSmtpPort;
        private String emailUsername;
        private String emailPassword;
        private String emailRecipient;
    }
}
