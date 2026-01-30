package com.trader.exchange;

import com.trader.core.Exchange;
import com.trader.core.PriceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Multi-Exchange Validator Tests")
class MultiExchangeValidatorTest {

    private MultiExchangeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new MultiExchangeValidator(0.01, 3); // 1% max deviation, 3 exchanges min
    }

    @Test
    @DisplayName("Should validate consistent prices across exchanges")
    void testValidateConsistentPrices() {
        Map<Exchange, PriceData> prices = new HashMap<>();

        prices.put(Exchange.BINANCE, createPriceData(Exchange.BINANCE, 46000.0, 100));
        prices.put(Exchange.COINBASE, createPriceData(Exchange.COINBASE, 46010.0, 80));
        prices.put(Exchange.KRAKEN, createPriceData(Exchange.KRAKEN, 45995.0, 60));

        MultiExchangeValidator.ValidationResult result = validator.validatePrices(prices);

        assertTrue(result.isValid());
        assertTrue(result.getCoefficientOfVariation() < 0.01);
    }

    @Test
    @DisplayName("Should reject when prices deviate too much")
    void testRejectHighDeviation() {
        Map<Exchange, PriceData> prices = new HashMap<>();

        prices.put(Exchange.BINANCE, createPriceData(Exchange.BINANCE, 46000.0, 100));
        prices.put(Exchange.COINBASE, createPriceData(Exchange.COINBASE, 47000.0, 80)); // 2.2% higher
        prices.put(Exchange.KRAKEN, createPriceData(Exchange.KRAKEN, 45000.0, 60));  // 2.2% lower

        MultiExchangeValidator.ValidationResult result = validator.validatePrices(prices);

        assertFalse(result.isValid());
        assertTrue(result.getCoefficientOfVariation() > 0.01);
    }

    @Test
    @DisplayName("Should reject when insufficient exchanges")
    void testRejectInsufficientExchanges() {
        Map<Exchange, PriceData> prices = new HashMap<>();

        prices.put(Exchange.BINANCE, createPriceData(Exchange.BINANCE, 46000.0, 100));
        prices.put(Exchange.COINBASE, createPriceData(Exchange.COINBASE, 46010.0, 80));
        // Only 2 exchanges, need 3

        MultiExchangeValidator.ValidationResult result = validator.validatePrices(prices);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream()
                .anyMatch(e -> e.contains("Insufficient exchanges")));
    }

    @Test
    @DisplayName("Should detect outliers")
    void testDetectOutliers() {
        Map<Exchange, PriceData> prices = new HashMap<>();

        prices.put(Exchange.BINANCE, createPriceData(Exchange.BINANCE, 46000.0, 100));
        prices.put(Exchange.COINBASE, createPriceData(Exchange.COINBASE, 46005.0, 80));
        prices.put(Exchange.KRAKEN, createPriceData(Exchange.KRAKEN, 46010.0, 60));
        prices.put(Exchange.KUCOIN, createPriceData(Exchange.KUCOIN, 50000.0, 40)); // Outlier

        MultiExchangeValidator.ValidationResult result = validator.validatePrices(prices);

        assertTrue(result.getOutlierExchanges().contains(Exchange.KUCOIN));
    }

    @Test
    @DisplayName("Should calculate VWAP correctly")
    void testCalculateVWAP() {
        Map<Exchange, PriceData> prices = new HashMap<>();

        // Volume weighted: (46000*100 + 46020*200 + 45980*100) / 400 = 46010
        prices.put(Exchange.BINANCE, createPriceData(Exchange.BINANCE, 46000.0, 100));
        prices.put(Exchange.COINBASE, createPriceData(Exchange.COINBASE, 46020.0, 200));
        prices.put(Exchange.KRAKEN, createPriceData(Exchange.KRAKEN, 45980.0, 100));

        MultiExchangeValidator.ValidationResult result = validator.validatePrices(prices);

        assertTrue(result.isValid());
        // Consensus price should be close to 46010
        assertTrue(Math.abs(result.getConsensusPrice() - 46010) < 5);
    }

    @Test
    @DisplayName("Should validate price movement")
    void testValidatePriceMovement() {
        Map<Exchange, PriceData> prices = new HashMap<>();

        prices.put(Exchange.BINANCE, createPriceData(Exchange.BINANCE, 46000.0, 100));
        prices.put(Exchange.COINBASE, createPriceData(Exchange.COINBASE, 46010.0, 80));
        prices.put(Exchange.KRAKEN, createPriceData(Exchange.KRAKEN, 45995.0, 60));

        boolean valid = validator.validatePriceMovement(prices);

        assertTrue(valid);
    }

    private PriceData createPriceData(Exchange exchange, double price, double volume) {
        return PriceData.builder()
                .exchange(exchange)
                .symbol("BTCUSDT")
                .price(price)
                .bidPrice(price - 1)
                .askPrice(price + 1)
                .volume(volume)
                .timestamp(Instant.now())
                .build();
    }
}
