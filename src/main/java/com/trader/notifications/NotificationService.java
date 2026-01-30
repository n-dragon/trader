package com.trader.notifications;

import com.trader.config.DynamicConfig;
import com.trader.core.Trade;
import com.trader.core.TradingSignal;
import com.trader.risk.CircuitBreaker;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

/**
 * Notification service for sending trading alerts via multiple channels.
 */
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot%s/sendMessage";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    private final DynamicConfig.NotificationConfig config;
    private final ExecutorService executor;
    private final BlockingQueue<NotificationTask> notificationQueue;
    private final Thread notificationWorker;
    private volatile boolean running;

    public NotificationService(DynamicConfig.NotificationConfig config) {
        this.config = config;
        this.executor = Executors.newFixedThreadPool(3, r -> {
            Thread t = new Thread(r, "notification-sender");
            t.setDaemon(true);
            return t;
        });
        this.notificationQueue = new LinkedBlockingQueue<>();
        this.running = true;

        // Start notification worker
        this.notificationWorker = new Thread(this::processNotifications, "notification-worker");
        this.notificationWorker.setDaemon(true);
        this.notificationWorker.start();

        logger.info("NotificationService initialized (telegram={}, email={})",
                config.isTelegramEnabled(), config.isEmailEnabled());
    }

    /**
     * Sends a trading signal alert.
     */
    public void sendSignalAlert(TradingSignal signal) {
        if (!config.isEnabled()) return;

        String message = formatSignalAlert(signal);
        queueNotification(message, NotificationPriority.HIGH);
    }

    /**
     * Sends a trade execution alert.
     */
    public void sendTradeAlert(Trade trade, String action) {
        if (!config.isEnabled()) return;

        String message = formatTradeAlert(trade, action);
        queueNotification(message, NotificationPriority.HIGH);
    }

    /**
     * Sends a circuit breaker activation alert.
     */
    public void sendCircuitBreakerAlert(CircuitBreaker.CircuitBreakerState state) {
        if (!config.isEnabled()) return;

        String message = formatCircuitBreakerAlert(state);
        queueNotification(message, NotificationPriority.CRITICAL);
    }

    /**
     * Sends an error alert.
     */
    public void sendErrorAlert(String errorType, String errorMessage) {
        if (!config.isEnabled()) return;

        String message = formatErrorAlert(errorType, errorMessage);
        queueNotification(message, NotificationPriority.CRITICAL);
    }

    /**
     * Sends a daily summary.
     */
    public void sendDailySummary(DailySummary summary) {
        if (!config.isEnabled()) return;

        String message = formatDailySummary(summary);
        queueNotification(message, NotificationPriority.NORMAL);
    }

    /**
     * Sends a custom message.
     */
    public void sendCustomMessage(String message, NotificationPriority priority) {
        if (!config.isEnabled()) return;

        queueNotification(message, priority);
    }

    private void queueNotification(String message, NotificationPriority priority) {
        notificationQueue.offer(new NotificationTask(message, priority, Instant.now()));
    }

    private void processNotifications() {
        while (running) {
            try {
                NotificationTask task = notificationQueue.poll(100, TimeUnit.MILLISECONDS);
                if (task != null) {
                    sendNotification(task);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void sendNotification(NotificationTask task) {
        if (config.isTelegramEnabled()) {
            executor.submit(() -> sendTelegram(task.message));
        }

        if (config.isEmailEnabled() && task.priority == NotificationPriority.CRITICAL) {
            executor.submit(() -> sendEmail("Trading Bot Alert", task.message));
        }
    }

    /**
     * Sends a message via Telegram.
     */
    private void sendTelegram(String message) {
        try {
            String apiUrl = String.format(TELEGRAM_API_URL, config.getTelegramToken());
            String payload = String.format("{\"chat_id\":\"%s\",\"text\":\"%s\",\"parse_mode\":\"HTML\"}",
                    config.getTelegramChatId(),
                    escapeJson(message));

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                logger.error("Telegram API error: {}", responseCode);
            } else {
                logger.debug("Telegram notification sent");
            }

            conn.disconnect();
        } catch (Exception e) {
            logger.error("Failed to send Telegram notification: {}", e.getMessage());
        }
    }

    /**
     * Sends an email notification.
     */
    private void sendEmail(String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", config.getEmailSmtpHost());
            props.put("mail.smtp.port", String.valueOf(config.getEmailSmtpPort()));

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            config.getEmailUsername(),
                            config.getEmailPassword()
                    );
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getEmailUsername()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(config.getEmailRecipient()));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            logger.debug("Email notification sent");
        } catch (Exception e) {
            logger.error("Failed to send email notification: {}", e.getMessage());
        }
    }

    // Message formatting methods
    private String formatSignalAlert(TradingSignal signal) {
        return String.format("""
                        <b>SIGNAL DETECTED</b>

                        Pair: %s
                        Type: %s
                        Action: %s
                        Price: %.2f
                        Confidence: %.1f%%
                        Strength: %s

                        Triggered Rules:
                        %s

                        Stop Loss: %.2f
                        Take Profit: %.2f

                        Time: %s""",
                signal.getSymbol(),
                signal.getType(),
                signal.getAction(),
                signal.getPrice(),
                signal.getConfidence() * 100,
                signal.getStrength(),
                formatRules(signal.getTriggeredRules()),
                signal.getSuggestedStopLoss(),
                signal.getSuggestedTakeProfit(),
                TIME_FORMATTER.format(Instant.now())
        );
    }

    private String formatTradeAlert(Trade trade, String action) {
        String emoji = trade.isWinningTrade() ? "+" : "";
        return String.format("""
                        <b>TRADE %s</b>

                        Pair: %s
                        Type: %s
                        Entry: %.2f
                        Exit: %.2f
                        Quantity: %.8f

                        P&L: %s%.2f (%.2f%%)
                        Exit Reason: %s

                        Time: %s""",
                action.toUpperCase(),
                trade.getSymbol(),
                trade.getType(),
                trade.getEntryPrice(),
                trade.getExitPrice(),
                trade.getQuantity(),
                emoji,
                trade.getProfit(),
                trade.getProfitPercent(),
                trade.getExitReason(),
                TIME_FORMATTER.format(Instant.now())
        );
    }

    private String formatCircuitBreakerAlert(CircuitBreaker.CircuitBreakerState state) {
        return String.format("""
                        <b>CIRCUIT BREAKER ACTIVATED</b>

                        Reason: %s
                        Consecutive Losses: %d
                        Daily Loss: %.2f (%.2f%%)

                        Trading is SUSPENDED until manual intervention or automatic reset.

                        Time: %s""",
                state.getCircuitOpenReason(),
                state.getConsecutiveLosses(),
                state.getDailyLoss(),
                state.getDailyLossPercent(),
                TIME_FORMATTER.format(Instant.now())
        );
    }

    private String formatErrorAlert(String errorType, String errorMessage) {
        return String.format("""
                        <b>ERROR ALERT</b>

                        Type: %s
                        Message: %s

                        Time: %s""",
                errorType,
                errorMessage,
                TIME_FORMATTER.format(Instant.now())
        );
    }

    private String formatDailySummary(DailySummary summary) {
        return String.format("""
                        <b>DAILY SUMMARY</b>

                        Date: %s

                        <b>Performance</b>
                        Total Trades: %d
                        Winning Trades: %d
                        Losing Trades: %d
                        Win Rate: %.2f%%

                        <b>P&L</b>
                        Gross Profit: %.2f
                        Gross Loss: %.2f
                        Net P&L: %.2f (%.2f%%)

                        <b>Capital</b>
                        Starting: %.2f
                        Ending: %.2f

                        <b>Risk</b>
                        Max Drawdown: %.2f%%
                        Consecutive Losses: %d""",
                summary.getDate(),
                summary.getTotalTrades(),
                summary.getWinningTrades(),
                summary.getLosingTrades(),
                summary.getWinRate(),
                summary.getGrossProfit(),
                summary.getGrossLoss(),
                summary.getNetPnL(),
                summary.getNetPnLPercent(),
                summary.getStartingCapital(),
                summary.getEndingCapital(),
                summary.getMaxDrawdown(),
                summary.getMaxConsecutiveLosses()
        );
    }

    private String formatRules(java.util.List<String> rules) {
        if (rules == null || rules.isEmpty()) {
            return "  - None";
        }
        StringBuilder sb = new StringBuilder();
        for (String rule : rules) {
            sb.append("  - ").append(rule).append("\n");
        }
        return sb.toString().trim();
    }

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * Shuts down the notification service.
     */
    public void shutdown() {
        running = false;
        notificationWorker.interrupt();
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public enum NotificationPriority {
        LOW, NORMAL, HIGH, CRITICAL
    }

    private static class NotificationTask {
        final String message;
        final NotificationPriority priority;
        final Instant timestamp;

        NotificationTask(String message, NotificationPriority priority, Instant timestamp) {
            this.message = message;
            this.priority = priority;
            this.timestamp = timestamp;
        }
    }

    @Data
    @Builder
    public static class DailySummary {
        private String date;
        private int totalTrades;
        private int winningTrades;
        private int losingTrades;
        private double winRate;
        private double grossProfit;
        private double grossLoss;
        private double netPnL;
        private double netPnLPercent;
        private double startingCapital;
        private double endingCapital;
        private double maxDrawdown;
        private int maxConsecutiveLosses;
    }
}
