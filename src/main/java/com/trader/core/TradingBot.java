package com.trader.core;

import com.trader.config.DynamicConfig;
import com.trader.engine.BacktestEngine;
import com.trader.events.EventBus;
import com.trader.events.TradingEvents;
import com.trader.exchange.MultiExchangeValidator;
import com.trader.indicators.AdaptiveIndicators;
import com.trader.metrics.PerformanceMetrics;
import com.trader.notifications.NotificationService;
import com.trader.patterns.AdvancedPatternDetector;
import com.trader.risk.CircuitBreaker;
import com.trader.risk.RiskManagement;
import com.trader.security.RateLimiter;
import com.trader.state.TradingStateMachine;
import com.trader.validation.BacktestValidator;
import com.trader.validation.LatencyManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

/**
 * Main trading bot orchestrator that coordinates all components.
 */
public class TradingBot {
    private static final Logger logger = LoggerFactory.getLogger(TradingBot.class);

    // Core components
    @Getter private final DynamicConfig config;
    @Getter private final EventBus eventBus;
    @Getter private final RiskManagement riskManagement;
    @Getter private final CircuitBreaker circuitBreaker;
    @Getter private final TradingStateMachine stateMachine;

    // Analysis components
    @Getter private final AdaptiveIndicators indicators;
    @Getter private final AdvancedPatternDetector patternDetector;
    @Getter private final MultiExchangeValidator multiExchangeValidator;
    @Getter private final LatencyManager latencyManager;

    // Security components
    @Getter private final RateLimiter rateLimiter;

    // Notification
    @Getter private final NotificationService notificationService;

    // Metrics
    @Getter private final PerformanceMetrics performanceMetrics;

    // State
    private volatile boolean running;
    private final List<Trade> tradeHistory;
    private final Map<String, List<Candle>> candleCache;
    private final ScheduledExecutorService scheduler;

    public TradingBot(String configPath) {
        logger.info("Initializing Trading Bot...");

        // Load configuration
        this.config = new DynamicConfig(configPath);

        // Initialize event bus
        this.eventBus = new EventBus();

        // Initialize risk management
        DynamicConfig.RiskConfig riskConfig = config.getRiskConfig();
        this.riskManagement = RiskManagement.builder()
                .totalCapital(config.getTotalCapital())
                .maxPositionSizePercent(riskConfig.getMaxPositionSizePercent())
                .maxDailyLossPercent(riskConfig.getMaxDailyLossPercent())
                .maxPortfolioRisk(10.0)
                .stopLossPercent(1.5)
                .takeProfitPercent(3.0)
                .trailingStopActivation(2.0)
                .trailingStopDistance(1.0)
                .build();

        // Initialize circuit breaker
        this.circuitBreaker = new CircuitBreaker(
                riskConfig.getMaxConsecutiveLosses(),
                riskConfig.getMaxDailyLossPercent(),
                Duration.ofHours(24),
                this::handleCircuitBreakerAlert
        );

        // Initialize state machine
        this.stateMachine = new TradingStateMachine(riskManagement, circuitBreaker);

        // Initialize analysis components
        this.indicators = new AdaptiveIndicators();
        this.patternDetector = new AdvancedPatternDetector();
        this.multiExchangeValidator = new MultiExchangeValidator();
        this.latencyManager = new LatencyManager();

        // Initialize security components
        this.rateLimiter = new RateLimiter(1200);

        // Initialize notification service
        this.notificationService = new NotificationService(config.getNotificationConfig());

        // Initialize metrics
        this.performanceMetrics = new PerformanceMetrics();

        // Initialize state
        this.running = false;
        this.tradeHistory = new CopyOnWriteArrayList<>();
        this.candleCache = new ConcurrentHashMap<>();

        // Initialize scheduler
        this.scheduler = Executors.newScheduledThreadPool(4, r -> {
            Thread t = new Thread(r, "trading-bot-scheduler");
            t.setDaemon(true);
            return t;
        });

        // Setup event handlers
        setupEventHandlers();

        // Setup config change listener
        config.addChangeListener(c -> {
            logger.info("Configuration reloaded, updating components...");
            // Update components with new configuration
        });

        logger.info("Trading Bot initialized successfully");
        logger.info("Mode: {}", config.isPaperTrading() ? "PAPER TRADING" : "LIVE TRADING");
        logger.info("Initial capital: {}", config.getTotalCapital());
    }

    /**
     * Starts the trading bot.
     */
    public void start() {
        if (running) {
            logger.warn("Trading bot is already running");
            return;
        }

        running = true;
        logger.info("Starting Trading Bot...");

        // Reset daily counters
        circuitBreaker.resetDailyCounters(config.getTotalCapital());
        riskManagement.resetDailyCounters();

        // Schedule periodic tasks
        schedulePeriodicTasks();

        logger.info("Trading Bot started successfully");
        notificationService.sendCustomMessage("Trading Bot started in " +
                        (config.isPaperTrading() ? "PAPER" : "LIVE") + " mode",
                NotificationService.NotificationPriority.HIGH);
    }

    /**
     * Stops the trading bot.
     */
    public void stop() {
        if (!running) {
            logger.warn("Trading bot is not running");
            return;
        }

        running = false;
        logger.info("Stopping Trading Bot...");

        // Close any open positions (in paper mode, just log)
        if (stateMachine.isInPosition()) {
            logger.warn("Bot stopping with open position - consider closing manually");
        }

        notificationService.sendCustomMessage("Trading Bot stopped",
                NotificationService.NotificationPriority.HIGH);

        logger.info("Trading Bot stopped");
    }

    /**
     * Shuts down all components.
     */
    public void shutdown() {
        stop();

        logger.info("Shutting down Trading Bot components...");

        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }

        eventBus.shutdown();
        circuitBreaker.shutdown();
        latencyManager.shutdown();
        notificationService.shutdown();
        config.shutdown();

        logger.info("Trading Bot shutdown complete");
    }

    /**
     * Processes a new price update.
     */
    public void onPriceUpdate(PriceData priceData) {
        if (!running) return;

        // Publish price update event
        eventBus.publishAsync(TradingEvents.PriceUpdateEvent.builder()
                .symbol(priceData.getSymbol())
                .price(priceData.getPrice())
                .bidPrice(priceData.getBidPrice())
                .askPrice(priceData.getAskPrice())
                .volume(priceData.getVolume())
                .exchange(priceData.getExchange())
                .timestamp(priceData.getTimestamp())
                .build());

        // Check if in position and needs to update stop-loss/take-profit
        if (stateMachine.isInPosition()) {
            TradingSignal priceSignal = TradingSignal.builder()
                    .symbol(priceData.getSymbol())
                    .price(priceData.getPrice())
                    .type(TradingSignal.SignalType.NEUTRAL)
                    .timestamp(Instant.now())
                    .build();

            stateMachine.handleSignal(priceSignal);
        }
    }

    /**
     * Processes a new candle.
     */
    public void onCandleClosed(Candle candle) {
        if (!running) return;

        // Cache candle
        candleCache.computeIfAbsent(candle.getSymbol(), k -> new CopyOnWriteArrayList<>())
                .add(candle);

        // Trim cache to last 1000 candles
        List<Candle> candles = candleCache.get(candle.getSymbol());
        while (candles.size() > 1000) {
            candles.remove(0);
        }

        // Publish event
        eventBus.publishAsync(TradingEvents.CandleClosedEvent.builder()
                .candle(candle)
                .build());

        // Analyze if we have enough data
        if (candles.size() >= 50) {
            analyzeAndGenerateSignal(candle.getSymbol(), candles);
        }
    }

    /**
     * Analyzes candle data and generates trading signals.
     */
    private void analyzeAndGenerateSignal(String symbol, List<Candle> candles) {
        // Check if trading is allowed
        if (!circuitBreaker.allowTrade()) {
            return;
        }

        if (!config.isSymbolEnabled(symbol)) {
            return;
        }

        DynamicConfig.TradingConfig tradingConfig = config.getTradingConfig(symbol);
        Candle currentCandle = candles.get(candles.size() - 1);

        // Run analysis
        AdvancedPatternDetector.PatternAnalysisResult patternResult = patternDetector.analyzePatterns(candles);
        AdaptiveIndicators.RSIResult rsi = indicators.calculateAdaptiveRSI(candles);
        AdaptiveIndicators.MACDResult macd = indicators.calculateCryptoMACD(candles);

        // Generate signal based on analysis
        TradingSignal signal = generateSignal(symbol, currentCandle, patternResult, rsi, macd, tradingConfig);

        if (signal != null && signal.getType() != TradingSignal.SignalType.NEUTRAL) {
            // Publish signal event
            eventBus.publish(TradingEvents.SignalDetectedEvent.builder()
                    .signal(signal)
                    .build());

            // Notify
            notificationService.sendSignalAlert(signal);

            // Handle signal in state machine
            TradingStateMachine.StateTransitionResult result = stateMachine.handleSignal(signal);

            logger.info("Signal processed: {} -> {}", signal.getType(), result.getMessage());
        }
    }

    /**
     * Generates a trading signal based on multiple analyses.
     */
    private TradingSignal generateSignal(
            String symbol,
            Candle currentCandle,
            AdvancedPatternDetector.PatternAnalysisResult patternResult,
            AdaptiveIndicators.RSIResult rsi,
            AdaptiveIndicators.MACDResult macd,
            DynamicConfig.TradingConfig tradingConfig
    ) {
        double price = currentCandle.getClose();
        List<String> triggeredRules = new ArrayList<>();
        double confidenceScore = 0;

        // Pattern analysis
        if (patternResult.isPatternsDetected()) {
            if (patternResult.getOverallSignal() == AdvancedPatternDetector.PatternType.BULLISH) {
                confidenceScore += patternResult.getBullishScore();
                patternResult.getPatterns().forEach(p -> triggeredRules.add("Pattern: " + p.getName()));
            } else if (patternResult.getOverallSignal() == AdvancedPatternDetector.PatternType.BEARISH) {
                confidenceScore -= patternResult.getBearishScore();
            }
        }

        // RSI analysis
        if (rsi.isOversold()) {
            confidenceScore += 0.3;
            triggeredRules.add("RSI Oversold: " + String.format("%.1f", rsi.getValue()));
        } else if (rsi.isOverbought()) {
            confidenceScore -= 0.3;
            triggeredRules.add("RSI Overbought: " + String.format("%.1f", rsi.getValue()));
        }

        // MACD analysis
        if (macd.isBullishCrossover()) {
            confidenceScore += 0.4;
            triggeredRules.add("MACD Bullish Crossover");
        } else if (macd.isBearishCrossover()) {
            confidenceScore -= 0.4;
            triggeredRules.add("MACD Bearish Crossover");
        }

        // Divergence analysis
        if (patternResult.getDivergence() != null && patternResult.getDivergence().isDetected()) {
            if (patternResult.getDivergence().isBullish()) {
                confidenceScore += 0.5;
                triggeredRules.add("Bullish Divergence");
            } else {
                confidenceScore -= 0.5;
                triggeredRules.add("Bearish Divergence");
            }
        }

        // Determine signal type
        TradingSignal.SignalType signalType = TradingSignal.SignalType.NEUTRAL;
        TradingSignal.SignalAction action = TradingSignal.SignalAction.HOLD;

        double threshold = tradingConfig.getThreshold();

        if (!stateMachine.isInPosition()) {
            if (confidenceScore >= threshold) {
                signalType = TradingSignal.SignalType.ENTRY_LONG;
                action = TradingSignal.SignalAction.BUY;
            }
        } else {
            if (confidenceScore <= -threshold) {
                signalType = TradingSignal.SignalType.EXIT_LONG;
                action = TradingSignal.SignalAction.SELL;
            }
        }

        if (signalType == TradingSignal.SignalType.NEUTRAL) {
            return null;
        }

        // Calculate stop-loss and take-profit
        double stopLoss = riskManagement.calculateStopLoss(price, Trade.TradeType.LONG);
        double takeProfit = riskManagement.calculateTakeProfit(price, Trade.TradeType.LONG);

        // Calculate position size
        double quantity = riskManagement.calculatePositionSizeByRisk(price, stopLoss, 2.0);

        return TradingSignal.builder()
                .symbol(symbol)
                .type(signalType)
                .action(action)
                .price(price)
                .confidence(Math.abs(confidenceScore))
                .strength(TradingSignal.SignalStrength.fromConfidence(Math.abs(confidenceScore)))
                .timestamp(Instant.now())
                .triggeredRules(triggeredRules)
                .suggestedStopLoss(stopLoss)
                .suggestedTakeProfit(takeProfit)
                .suggestedQuantity(quantity)
                .build();
    }

    /**
     * Runs a backtest with the current strategy.
     */
    public BacktestEngine.BacktestReport runBacktest(List<Candle> historicalData) {
        BacktestEngine engine = new BacktestEngine(config.getTotalCapital());

        return engine.runBacktest(historicalData, (candles) -> {
            if (candles.size() < 50) return null;

            String symbol = candles.get(0).getSymbol();
            DynamicConfig.TradingConfig tradingConfig = config.getTradingConfig(symbol);
            Candle currentCandle = candles.get(candles.size() - 1);

            AdvancedPatternDetector.PatternAnalysisResult patternResult = patternDetector.analyzePatterns(candles);
            AdaptiveIndicators.RSIResult rsi = indicators.calculateAdaptiveRSI(candles);
            AdaptiveIndicators.MACDResult macd = indicators.calculateCryptoMACD(candles);

            return generateSignal(symbol, currentCandle, patternResult, rsi, macd, tradingConfig);
        });
    }

    /**
     * Gets current bot status.
     */
    public BotStatus getStatus() {
        return BotStatus.builder()
                .running(running)
                .mode(config.isPaperTrading() ? "PAPER" : "LIVE")
                .currentState(stateMachine.getCurrentState())
                .inPosition(stateMachine.isInPosition())
                .openPosition(stateMachine.getOpenPosition())
                .riskMetrics(riskManagement.getRiskMetrics())
                .circuitBreakerState(circuitBreaker.getState())
                .tradeCount(tradeHistory.size())
                .uptime(Duration.between(Instant.now().minusSeconds(3600), Instant.now())) // Placeholder
                .build();
    }

    private void setupEventHandlers() {
        // Handle position closed events
        eventBus.subscribe(TradingEvents.PositionClosedEvent.class, event -> {
            tradeHistory.add(event.getTrade());
            notificationService.sendTradeAlert(event.getTrade(), "CLOSED");
        });

        // Handle circuit breaker events
        eventBus.subscribe(TradingEvents.CircuitBreakerActivatedEvent.class, event -> {
            notificationService.sendCircuitBreakerAlert(circuitBreaker.getState());
        });

        // Handle error events
        eventBus.subscribe(TradingEvents.ErrorEvent.class, event -> {
            if (event.isCritical()) {
                notificationService.sendErrorAlert(event.getErrorType(), event.getMessage());
            }
        });
    }

    private void schedulePeriodicTasks() {
        // Daily reset at midnight
        scheduler.scheduleAtFixedRate(() -> {
            logger.info("Performing daily reset...");
            circuitBreaker.resetDailyCounters(riskManagement.getTotalCapital());
            riskManagement.resetDailyCounters();
        }, 24, 24, TimeUnit.HOURS);

        // Health check every minute
        scheduler.scheduleAtFixedRate(() -> {
            if (running) {
                // Log current status
                logger.debug("Bot status: {}", stateMachine.getCurrentState());
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    private void handleCircuitBreakerAlert(String message) {
        logger.error("CIRCUIT BREAKER: {}", message);
        eventBus.publish(TradingEvents.CircuitBreakerActivatedEvent.builder()
                .reason(message)
                .consecutiveLosses(circuitBreaker.getConsecutiveLosses())
                .dailyLoss(circuitBreaker.getDailyLoss())
                .build());
    }

    /**
     * Main entry point.
     */
    public static void main(String[] args) {
        String configPath = args.length > 0 ? args[0] : "config.properties";

        TradingBot bot = new TradingBot(configPath);

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutdown signal received...");
            bot.shutdown();
        }));

        // Start the bot
        bot.start();

        // Keep main thread alive
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            bot.shutdown();
        }
    }

    @lombok.Data
    @lombok.Builder
    public static class BotStatus {
        private boolean running;
        private String mode;
        private TradingStateMachine.TradingState currentState;
        private boolean inPosition;
        private Position openPosition;
        private RiskManagement.RiskMetrics riskMetrics;
        private CircuitBreaker.CircuitBreakerState circuitBreakerState;
        private int tradeCount;
        private Duration uptime;
    }
}
