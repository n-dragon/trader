package com.trader.risk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Circuit Breaker Tests")
class CircuitBreakerTest {

    private CircuitBreaker circuitBreaker;
    private AtomicBoolean alertReceived;
    private AtomicReference<String> lastAlertMessage;

    @BeforeEach
    void setUp() {
        alertReceived = new AtomicBoolean(false);
        lastAlertMessage = new AtomicReference<>("");

        circuitBreaker = new CircuitBreaker(
                5,      // Max 5 consecutive losses
                5.0,    // 5% max daily loss
                Duration.ofHours(1),  // Reset after 1 hour
                message -> {
                    alertReceived.set(true);
                    lastAlertMessage.set(message);
                }
        );

        circuitBreaker.resetDailyCounters(10000); // $10,000 starting capital
    }

    @Test
    @DisplayName("Should allow trading when circuit is closed")
    void testAllowTradingWhenClosed() {
        assertTrue(circuitBreaker.allowTrade());
        assertFalse(circuitBreaker.isCircuitOpen());
    }

    @Test
    @DisplayName("Should open circuit after max consecutive losses")
    void testOpensAfterConsecutiveLosses() {
        // Record 5 consecutive losses
        for (int i = 0; i < 5; i++) {
            circuitBreaker.recordTradeResult(-100, 10000);
        }

        assertTrue(circuitBreaker.isCircuitOpen());
        assertFalse(circuitBreaker.allowTrade());
        assertTrue(alertReceived.get());
        assertTrue(lastAlertMessage.get().contains("consecutive losses"));
    }

    @Test
    @DisplayName("Should reset consecutive losses on a win")
    void testResetsOnWin() {
        // Record 4 losses
        for (int i = 0; i < 4; i++) {
            circuitBreaker.recordTradeResult(-100, 10000);
        }

        assertEquals(4, circuitBreaker.getConsecutiveLosses());

        // Record a win
        circuitBreaker.recordTradeResult(100, 10000);

        assertEquals(0, circuitBreaker.getConsecutiveLosses());
        assertTrue(circuitBreaker.allowTrade());
    }

    @Test
    @DisplayName("Should open circuit when daily loss limit is reached")
    void testOpensOnDailyLossLimit() {
        // Record a single large loss that exceeds 5% of $10,000 = $500
        circuitBreaker.recordTradeResult(-600, 10000);

        assertTrue(circuitBreaker.isCircuitOpen());
        assertFalse(circuitBreaker.allowTrade());
        assertTrue(lastAlertMessage.get().contains("Daily loss limit"));
    }

    @Test
    @DisplayName("Should track cumulative daily loss")
    void testCumulativeDailyLoss() {
        // Record multiple small losses that add up
        circuitBreaker.recordTradeResult(-100, 10000);
        circuitBreaker.recordTradeResult(-100, 10000);
        circuitBreaker.recordTradeResult(-100, 10000);

        assertEquals(300, circuitBreaker.getDailyLoss());
        assertTrue(circuitBreaker.allowTrade()); // Still below 5%

        // Add more losses to exceed limit
        circuitBreaker.recordTradeResult(-100, 10000);
        circuitBreaker.recordTradeResult(-100, 10000);
        circuitBreaker.recordTradeResult(-100, 10000);

        assertTrue(circuitBreaker.isCircuitOpen());
    }

    @Test
    @DisplayName("Should allow manual circuit close")
    void testManualClose() {
        // Open the circuit
        for (int i = 0; i < 5; i++) {
            circuitBreaker.recordTradeResult(-100, 10000);
        }

        assertTrue(circuitBreaker.isCircuitOpen());

        // Manually close
        circuitBreaker.closeCircuit();

        assertFalse(circuitBreaker.isCircuitOpen());
        assertTrue(circuitBreaker.allowTrade());
        assertEquals(0, circuitBreaker.getConsecutiveLosses());
    }

    @Test
    @DisplayName("Should provide circuit breaker state")
    void testGetState() {
        circuitBreaker.recordTradeResult(-100, 10000);
        circuitBreaker.recordTradeResult(-100, 10000);

        CircuitBreaker.CircuitBreakerState state = circuitBreaker.getState();

        assertFalse(state.isCircuitOpen());
        assertEquals(2, state.getConsecutiveLosses());
        assertEquals(200, state.getDailyLoss());
        assertEquals(2.0, state.getDailyLossPercent(), 0.01);
    }

    @Test
    @DisplayName("Should reset daily counters")
    void testResetDailyCounters() {
        circuitBreaker.recordTradeResult(-200, 10000);

        assertEquals(200, circuitBreaker.getDailyLoss());

        circuitBreaker.resetDailyCounters(10000);

        assertEquals(0, circuitBreaker.getDailyLoss());
    }

    @Test
    @DisplayName("Should not double-open circuit")
    void testNoDoubleOpen() {
        // Open circuit with losses
        for (int i = 0; i < 5; i++) {
            circuitBreaker.recordTradeResult(-100, 10000);
        }

        assertTrue(circuitBreaker.isCircuitOpen());
        alertReceived.set(false);

        // Try to open again - should not trigger new alert
        circuitBreaker.openCircuit("Test reason");

        assertFalse(alertReceived.get()); // No new alert
    }

    @Test
    @DisplayName("Should handle zero profit trade")
    void testZeroProfitTrade() {
        circuitBreaker.recordTradeResult(0, 10000);

        // Zero profit is not a loss, so consecutive losses should reset
        circuitBreaker.recordTradeResult(-100, 10000);
        assertEquals(1, circuitBreaker.getConsecutiveLosses());

        circuitBreaker.recordTradeResult(0, 10000);
        // Zero is not positive, behavior depends on implementation
        // In our case, 0 is not < 0, so it resets
        assertEquals(0, circuitBreaker.getConsecutiveLosses());
    }
}
