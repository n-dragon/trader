# Binance Trading Bot

A comprehensive algorithmic trading bot for Binance with advanced risk management, multi-exchange validation, and backtesting capabilities.

## Features

### Core Trading
- **Multi-Exchange Price Validation**: Validates prices across 3+ exchanges to prevent manipulation
- **Adaptive Technical Indicators**: RSI, MACD, Bollinger Bands with auto-optimizing parameters
- **Advanced Pattern Detection**: Candlestick patterns, divergences, support/resistance levels
- **Event-Driven Architecture**: Decoupled components with publish-subscribe pattern

### Risk Management
- **Position Sizing**: Kelly Criterion and fixed-risk position sizing
- **Stop-Loss/Take-Profit**: Automatic calculation and trailing stops
- **Circuit Breaker**: Automatic trading suspension after consecutive losses
- **Daily Loss Limits**: Configurable maximum daily loss protection

### Validation & Testing
- **Walk-Forward Analysis**: Prevents overfitting by validating on unseen data
- **Monte Carlo Simulation**: Estimates range of possible outcomes
- **Backtesting Engine**: Test strategies on historical data

### Operations
- **Dynamic Configuration**: Hot-reload configuration without restart
- **Multi-Channel Notifications**: Telegram, Email alerts
- **Rate Limiting**: Intelligent API rate management
- **Latency Management**: NTP synchronization and latency monitoring

## Project Structure

```
src/main/java/com/trader/
├── core/           # Core models (Candle, Trade, Position, Order, etc.)
├── risk/           # Risk management and circuit breaker
├── validation/     # Backtest validation, latency management
├── exchange/       # Multi-exchange validation
├── indicators/     # Technical indicators (RSI, MACD, etc.)
├── patterns/       # Candlestick pattern detection
├── state/          # Trading state machine
├── events/         # Event-driven architecture
├── security/       # Order validation, rate limiting
├── config/         # Dynamic configuration
├── notifications/  # Alert services
├── metrics/        # Performance metrics
└── engine/         # Backtest engine
```

## Prerequisites

- Java 17 or higher
- Gradle 8.0+ (or use the wrapper)
- Binance API credentials (for live trading)

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd trader
```

2. Build the project:
```bash
./gradlew build
```

3. Configure the application:
```bash
cp src/main/resources/config.properties config.properties
# Edit config.properties with your settings
```

## Configuration

Edit `config.properties` to configure:

```properties
# Trading mode (paper or live)
trading.mode=paper

# Capital settings
total.capital=10000

# Symbol configuration
BTCUSDT.enabled=true
BTCUSDT.stop_loss=1.5
BTCUSDT.take_profit=3.0

# Risk management
risk.max_daily_loss=5.0
risk.circuit_breaker.enabled=true
risk.circuit_breaker.consecutive_losses=5

# Notifications (optional)
notifications.telegram.enabled=false
notifications.telegram.token=YOUR_BOT_TOKEN
notifications.telegram.chat_id=YOUR_CHAT_ID
```

## Usage

### Running the Bot

```bash
# Using Gradle
./gradlew run --args="config.properties"

# Or using the fat JAR
./gradlew shadowJar
java -jar build/libs/binance-trading-bot-1.0.0-SNAPSHOT.jar config.properties
```

### Running Tests

```bash
./gradlew test
```

### Running Backtest

```java
// In your code
TradingBot bot = new TradingBot("config.properties");
List<Candle> historicalData = loadHistoricalData();
BacktestEngine.BacktestReport report = bot.runBacktest(historicalData);
System.out.println(report.getSummary());
```

## Risk Warning

**Trading cryptocurrencies involves significant risk of loss.**

This software is provided for educational purposes. Before using in live trading:

1. **Backtest thoroughly** - Minimum 6 months of historical data
2. **Paper trade first** - At least 1 month of simulated trading
3. **Start with minimal capital** - Only risk what you can afford to lose
4. **Monitor continuously** - Never leave the bot unattended for long periods

## Safety Checklist

### Before Live Trading

- [ ] Backtested on 6+ months of data
- [ ] Walk-forward validation completed
- [ ] Paper traded for 1+ month
- [ ] Monte Carlo simulation run
- [ ] Stop-loss configured for every trade
- [ ] Daily loss limit configured
- [ ] Circuit breaker enabled
- [ ] Notifications configured

### Security

- [ ] API keys stored securely (not in code)
- [ ] API keys have IP restrictions
- [ ] Withdrawal disabled on API keys
- [ ] 2FA enabled on exchange account

## Architecture

### Event Flow

```
Price Feed → Event Bus → Signal Generator → State Machine → Order Executor
                ↑                               ↓
          Multi-Exchange          ← Risk Check ←
          Validator
```

### State Machine

```
IDLE → ANALYZING → WAITING_ENTRY → IN_POSITION → WAITING_EXIT → IDLE
                         ↓                              ↓
                    (rejected)                   EMERGENCY_EXIT
```

## Performance Metrics

The bot tracks comprehensive metrics including:

- **Returns**: Total, annualized, Sharpe ratio, Sortino ratio
- **Risk**: Max drawdown, VaR, volatility
- **Trades**: Win rate, profit factor, expectancy
- **Quality**: Consecutive losses, correlation with market

## Contributing

1. Fork the repository
2. Create a feature branch
3. Write tests for new features
4. Submit a pull request

## License

This project is licensed under the MIT License.

## Disclaimer

This software is for educational purposes only. The authors are not responsible for any financial losses incurred through the use of this software. Always do your own research and never invest more than you can afford to lose.
