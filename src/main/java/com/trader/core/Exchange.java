package com.trader.core;

/**
 * Enumeration of supported exchanges.
 */
public enum Exchange {
    BINANCE("Binance", "https://api.binance.com"),
    COINBASE("Coinbase", "https://api.exchange.coinbase.com"),
    KRAKEN("Kraken", "https://api.kraken.com"),
    KUCOIN("KuCoin", "https://api.kucoin.com"),
    BYBIT("Bybit", "https://api.bybit.com"),
    OKX("OKX", "https://www.okx.com");

    private final String displayName;
    private final String apiBaseUrl;

    Exchange(String displayName, String apiBaseUrl) {
        this.displayName = displayName;
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }
}
