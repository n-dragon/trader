package com.trader.risk;

import com.trader.core.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Risk Management Tests")
class RiskManagementTest {

    private RiskManagement riskManagement;

    @BeforeEach
    void setUp() {
        riskManagement = RiskManagement.builder()
                .totalCapital(10000)
                .maxPositionSizePercent(2.0)
                .maxDailyLossPercent(5.0)
                .maxPortfolioRisk(10.0)
                .stopLossPercent(1.5)
                .takeProfitPercent(3.0)
                .trailingStopActivation(2.0)
                .trailingStopDistance(1.0)
                .build();
    }

    @Test
    @DisplayName("Should calculate position size correctly with 2% risk")
    void testCalculatePositionSizeByRisk() {
        double entryPrice = 100.0;
        double stopLoss = 98.0;
        double riskPercent = 2.0;

        double quantity = riskManagement.calculatePositionSizeByRisk(entryPrice, stopLoss, riskPercent);

        // Risk amount = 10000 * 0.02 = 200
        // Price risk = 100 - 98 = 2
        // Quantity = 200 / 2 = 100
        assertEquals(100.0, quantity, 0.01);
    }

    @Test
    @DisplayName("Should calculate Kelly criterion position size")
    void testCalculateOptimalPositionSize() {
        double winRate = 0.6;
        double avgWin = 150.0;
        double avgLoss = 100.0;

        double positionSize = riskManagement.calculateOptimalPositionSize(winRate, avgWin, avgLoss);

        // Kelly = (0.6 * 1.5 - 0.4) / 1.5 = (0.9 - 0.4) / 1.5 = 0.333
        // Half Kelly = 0.1667 = 16.67%
        // Capped at maxPositionSizePercent = 2%
        assertTrue(positionSize > 0);
        assertTrue(positionSize <= 2.0);
    }

    @Test
    @DisplayName("Should calculate stop loss for long position")
    void testCalculateStopLossLong() {
        double entryPrice = 100.0;

        double stopLoss = riskManagement.calculateStopLoss(entryPrice, Trade.TradeType.LONG);

        // Stop loss = 100 * (1 - 0.015) = 98.5
        assertEquals(98.5, stopLoss, 0.01);
    }

    @Test
    @DisplayName("Should calculate take profit for long position")
    void testCalculateTakeProfitLong() {
        double entryPrice = 100.0;

        double takeProfit = riskManagement.calculateTakeProfit(entryPrice, Trade.TradeType.LONG);

        // Take profit = 100 * (1 + 0.03) = 103
        assertEquals(103.0, takeProfit, 0.01);
    }

    @Test
    @DisplayName("Should allow trade when risk rules are met")
    void testCheckRiskRulesAllowed() {
        double proposedPosition = 100.0; // Within 2% of 10000

        RiskManagement.RiskCheckResult result = riskManagement.checkRiskRules(proposedPosition);

        assertTrue(result.isAllowed());
        assertTrue(result.getViolations().isEmpty());
    }

    @Test
    @DisplayName("Should block trade when position size exceeds limit")
    void testCheckRiskRulesExceedsLimit() {
        double proposedPosition = 500.0; // 5% of 10000, exceeds 2% limit

        RiskManagement.RiskCheckResult result = riskManagement.checkRiskRules(proposedPosition);

        assertFalse(result.isAllowed());
        assertFalse(result.getViolations().isEmpty());
    }

    @Test
    @DisplayName("Should track risk metrics correctly")
    void testGetRiskMetrics() {
        RiskManagement.RiskMetrics metrics = riskManagement.getRiskMetrics();

        assertEquals(10000.0, metrics.getTotalCapital(), 0.01);
        assertEquals(10000.0, metrics.getAvailableCapital(), 0.01);
        assertEquals(0.0, metrics.getCurrentExposure(), 0.01);
        assertEquals(0, metrics.getOpenPositionCount());
    }
}
