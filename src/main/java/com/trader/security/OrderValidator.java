package com.trader.security;

import com.trader.core.Order;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Validates orders before execution to prevent invalid or dangerous trades.
 */
public class OrderValidator {
    private static final Logger logger = LoggerFactory.getLogger(OrderValidator.class);

    private final Map<String, SymbolConfig> symbolConfigs;
    private final double maxPriceDeviationPercent;
    private final boolean requireStopLoss;

    private double availableBalance;
    private volatile double currentMarketPrice;

    public OrderValidator(double maxPriceDeviationPercent, boolean requireStopLoss) {
        this.maxPriceDeviationPercent = maxPriceDeviationPercent > 0 ? maxPriceDeviationPercent : 5.0;
        this.requireStopLoss = requireStopLoss;
        this.symbolConfigs = new ConcurrentHashMap<>();
        this.availableBalance = 0;
    }

    /**
     * Validates an order before execution.
     */
    public ValidationResult validateOrder(Order order) {
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        // Basic validation
        if (order == null) {
            errors.add("Order is null");
            return ValidationResult.builder()
                    .valid(false)
                    .errors(errors)
                    .build();
        }

        if (order.getSymbol() == null || order.getSymbol().isEmpty()) {
            errors.add("Symbol is required");
        }

        if (order.getQuantity() <= 0) {
            errors.add("Quantity must be positive");
        }

        // Get symbol config
        SymbolConfig config = symbolConfigs.get(order.getSymbol());

        // Validate minimum order size
        if (config != null && order.getQuantity() < config.getMinOrderSize()) {
            errors.add(String.format("Quantity %.8f below minimum %.8f",
                    order.getQuantity(), config.getMinOrderSize()));
        }

        // Validate maximum order size
        if (config != null && order.getQuantity() > config.getMaxOrderSize()) {
            errors.add(String.format("Quantity %.8f exceeds maximum %.8f",
                    order.getQuantity(), config.getMaxOrderSize()));
        }

        // Validate price for limit orders
        if (order.getType() == Order.OrderType.LIMIT ||
                order.getType() == Order.OrderType.STOP_LOSS_LIMIT ||
                order.getType() == Order.OrderType.TAKE_PROFIT_LIMIT) {

            if (order.getPrice() <= 0) {
                errors.add("Price must be positive for limit orders");
            }

            // Check price deviation from market
            if (currentMarketPrice > 0 && order.getPrice() > 0) {
                double deviation = Math.abs(order.getPrice() - currentMarketPrice) / currentMarketPrice * 100;
                if (deviation > maxPriceDeviationPercent) {
                    errors.add(String.format("Price %.2f deviates %.2f%% from market price %.2f (max: %.2f%%)",
                            order.getPrice(), deviation, currentMarketPrice, maxPriceDeviationPercent));
                } else if (deviation > maxPriceDeviationPercent / 2) {
                    warnings.add(String.format("Price deviates %.2f%% from market", deviation));
                }
            }

            // Validate price precision
            if (config != null && config.getPricePrecision() > 0) {
                double precision = Math.pow(10, config.getPricePrecision());
                double rounded = Math.round(order.getPrice() * precision) / precision;
                if (Math.abs(order.getPrice() - rounded) > 0.0000001) {
                    warnings.add(String.format("Price will be rounded from %.8f to %.8f",
                            order.getPrice(), rounded));
                }
            }
        }

        // Validate stop-loss is set
        if (requireStopLoss && order.getStopLoss() <= 0) {
            errors.add("Stop-loss is required but not set");
        }

        // Validate stop-loss is on correct side
        if (order.getStopLoss() > 0) {
            if (order.getSide() == Order.OrderSide.BUY && order.getStopLoss() >= order.getPrice()) {
                errors.add("Stop-loss must be below entry price for BUY orders");
            }
            if (order.getSide() == Order.OrderSide.SELL && order.getStopLoss() <= order.getPrice()) {
                errors.add("Stop-loss must be above entry price for SELL orders");
            }
        }

        // Validate take-profit is on correct side
        if (order.getTakeProfit() > 0) {
            if (order.getSide() == Order.OrderSide.BUY && order.getTakeProfit() <= order.getPrice()) {
                errors.add("Take-profit must be above entry price for BUY orders");
            }
            if (order.getSide() == Order.OrderSide.SELL && order.getTakeProfit() >= order.getPrice()) {
                errors.add("Take-profit must be below entry price for SELL orders");
            }
        }

        // Validate sufficient balance
        double requiredCapital = order.getQuantity() * (order.getPrice() > 0 ? order.getPrice() : currentMarketPrice);
        if (requiredCapital > availableBalance) {
            errors.add(String.format("Insufficient balance: %.2f required, %.2f available",
                    requiredCapital, availableBalance));
        }

        // Check for potential fat finger error (order too large)
        if (availableBalance > 0 && requiredCapital > availableBalance * 0.5) {
            warnings.add(String.format("Large order: using %.1f%% of available balance",
                    requiredCapital / availableBalance * 100));
        }

        boolean valid = errors.isEmpty();

        if (!valid) {
            logger.warn("Order validation failed: {}", errors);
        } else if (!warnings.isEmpty()) {
            logger.info("Order validation warnings: {}", warnings);
        }

        return ValidationResult.builder()
                .valid(valid)
                .errors(errors)
                .warnings(warnings)
                .requiredCapital(requiredCapital)
                .priceDeviation(currentMarketPrice > 0 ?
                        Math.abs(order.getPrice() - currentMarketPrice) / currentMarketPrice * 100 : 0)
                .build();
    }

    /**
     * Updates the current market price for validation.
     */
    public void updateMarketPrice(double price) {
        this.currentMarketPrice = price;
    }

    /**
     * Updates the available balance for validation.
     */
    public void updateAvailableBalance(double balance) {
        this.availableBalance = balance;
    }

    /**
     * Registers symbol-specific configuration.
     */
    public void registerSymbolConfig(String symbol, SymbolConfig config) {
        symbolConfigs.put(symbol, config);
    }

    @Data
    @Builder
    public static class ValidationResult {
        private boolean valid;
        private List<String> errors;
        private List<String> warnings;
        private double requiredCapital;
        private double priceDeviation;
    }

    @Data
    @Builder
    public static class SymbolConfig {
        private String symbol;
        private double minOrderSize;
        private double maxOrderSize;
        private int pricePrecision;
        private int quantityPrecision;
        private double minNotional;
    }
}
