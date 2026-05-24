#!/usr/bin/env python3
"""
eToro Agent-Portfolio — Automated trader
Run from any environment with network access to public-api.etoro.com

Usage:
    python trade.py [--dry-run] [--scenario A|B|auto]

Requirements:
    pip install requests
"""

import argparse
import json
import os
import sys
import time
import uuid
from datetime import datetime, timezone

import requests

# ── Credentials ────────────────────────────────────────────────────────────────
# Load from environment. Never hard-code secrets in source.
#   export ETORO_API_KEY=...
#   export ETORO_AGENT_KEY=...
API_KEY = os.environ.get("ETORO_API_KEY")
AGENT_KEY = os.environ.get("ETORO_AGENT_KEY")
BASE_URL = "https://public-api.etoro.com/api/v1"

if not API_KEY or not AGENT_KEY:
    sys.exit("Missing credentials: set ETORO_API_KEY and ETORO_AGENT_KEY in the environment.")

# ── Strategy config ─────────────────────────────────────────────────────────────
# Weights must sum to <= 1.0 (remainder = cash)
SCENARIO_A = {
    "ASML":    {"weight": 0.08, "is_buy": True, "stop_loss_pct": 0.92},
    "SUFRP":   {"weight": 0.05, "is_buy": True, "stop_loss_pct": 0.93},  # Schneider Electric
    "BTC":     {"weight": 0.04, "is_buy": True, "stop_loss_pct": 0.88},
    # AI.PA (32%) and SAN.PA (26%) already held — do not touch
}

SCENARIO_B = {
    "XAUUSD":  {"weight": 0.10, "is_buy": True, "stop_loss_pct": 0.95},
    "SNY":     {"weight": 0.05, "is_buy": True, "stop_loss_pct": 0.94},  # Sanofi ADR
}

MIN_CASH_RESERVE_PCT = 0.03  # Always keep 3% cash as safety buffer


# ── HTTP helpers ────────────────────────────────────────────────────────────────
def headers():
    return {
        "x-request-id": str(uuid.uuid4()),
        "x-api-key": API_KEY,
        "x-user-key": AGENT_KEY,
        "Content-Type": "application/json",
    }


def get(path, params=None, retries=3):
    url = BASE_URL + path
    for attempt in range(retries):
        r = requests.get(url, headers=headers(), params=params, timeout=30)
        if r.status_code == 429:
            wait = 15 * (2 ** attempt)
            print(f"  [rate-limit] waiting {wait}s...")
            time.sleep(wait)
            continue
        r.raise_for_status()
        return r.json()
    raise RuntimeError(f"GET {path} failed after {retries} retries")


def post(path, body, dry_run=False, retries=3):
    url = BASE_URL + path
    if dry_run:
        print(f"  [DRY-RUN] POST {path}")
        print(f"           {json.dumps(body, indent=2)}")
        return {"dry_run": True}
    for attempt in range(retries):
        r = requests.post(url, headers=headers(), json=body, timeout=30)
        if r.status_code == 429:
            wait = 15 * (2 ** attempt)
            print(f"  [rate-limit] waiting {wait}s...")
            time.sleep(wait)
            continue
        r.raise_for_status()
        return r.json()
    raise RuntimeError(f"POST {path} failed after {retries} retries")


# ── Portfolio calculations ───────────────────────────────────────────────────────
def fetch_pnl():
    return get("/trading/info/real/pnl")


def calc_equity(pnl):
    cp = pnl.get("clientPortfolio", {})

    orders_for_open = cp.get("ordersForOpen", [])
    orders = cp.get("orders", [])
    positions = cp.get("positions", [])
    mirrors = cp.get("mirrors", [])

    # Available cash = credit minus pending orders
    credit = cp.get("credit", 0)
    pending_open_manual = sum(o.get("amount", 0) for o in orders_for_open if o.get("mirrorID", 0) == 0)
    pending_close = sum(o.get("amount", 0) for o in orders)
    available_cash = credit - pending_open_manual - pending_close

    # Total invested
    total_invested = (
        sum(p.get("amount", 0) for p in positions)
        + sum(pos.get("amount", 0) for m in mirrors for pos in m.get("positions", []))
        + sum((m.get("availableAmount", 0) - m.get("closedPositionsNetProfit", 0)) for m in mirrors)
        + pending_open_manual
        + pending_close
        + sum(o.get("totalExternalCosts", 0) for o in orders_for_open if o.get("mirrorID", 0) == 0)
    )

    # Unrealized PnL
    unrealized = (
        sum(p.get("unrealizedPnL", {}).get("pnL", 0) for p in positions)
        + sum(pos.get("unrealizedPnL", {}).get("pnL", 0) for m in mirrors for pos in m.get("positions", []))
        + sum(m.get("closedPositionsNetProfit", 0) for m in mirrors)
    )

    equity = available_cash + total_invested + unrealized
    return {
        "equity": equity,
        "available_cash": available_cash,
        "total_invested": total_invested,
        "unrealized_pnl": unrealized,
        "positions": positions,
        "orders_for_open": orders_for_open,
        "mirrors": mirrors,
    }


def pct(value, equity):
    return 0 if equity == 0 else (value / equity * 100)


# ── Instrument resolution ────────────────────────────────────────────────────────
_instrument_cache = {}

def resolve_instrument(symbol):
    if symbol in _instrument_cache:
        return _instrument_cache[symbol]
    data = get(f"/market-data/search", params={"internalSymbolFull": symbol})
    items = data.get("items", [])
    match = next((i for i in items if i.get("internalSymbolFull") == symbol), None)
    if not match:
        raise ValueError(f"Instrument not found: {symbol}")
    _instrument_cache[symbol] = match["instrumentId"]
    return match["instrumentId"]


# ── Trade execution ──────────────────────────────────────────────────────────────
def open_position(symbol, amount_usd, stop_loss_rate=None, dry_run=False):
    instrument_id = resolve_instrument(symbol)
    body = {
        "InstrumentID": instrument_id,
        "IsBuy": True,
        "Leverage": 1,
        "Amount": round(amount_usd, 2),
    }
    if stop_loss_rate:
        body["StopLossRate"] = round(stop_loss_rate, 6)
    print(f"  OPEN  {symbol:10s}  ${amount_usd:,.2f}", end="")
    if stop_loss_rate:
        print(f"  SL={stop_loss_rate:.4f}", end="")
    print()
    result = post("/trading/execution/market-open-orders/by-amount", body, dry_run=dry_run)
    time.sleep(3)  # Rate limit: 20 req/min
    return result


def close_position(position_id, instrument_id, units=None, dry_run=False):
    body = {
        "InstrumentId": instrument_id,
        "UnitsToDeduct": units,
    }
    print(f"  CLOSE positionId={position_id}  units={units or 'all'}")
    result = post(f"/trading/execution/market-close-orders/positions/{position_id}", body, dry_run=dry_run)
    time.sleep(3)
    return result


# ── Current price lookup ─────────────────────────────────────────────────────────
def get_current_price(instrument_id):
    data = get(f"/market-data/instruments", params={"instrumentIds": instrument_id})
    instruments = data.get("instruments", [])
    if instruments:
        return instruments[0].get("rates", {}).get("buy", None)
    return None


# ── Portfolio report ─────────────────────────────────────────────────────────────
def print_portfolio(state):
    eq = state["equity"]
    print(f"\n{'='*55}")
    print(f"  Portfolio — {datetime.now(timezone.utc).strftime('%Y-%m-%d %H:%M UTC')}")
    print(f"{'='*55}")
    print(f"  Equity      : {pct(eq, eq):5.1f}%  (internal ref)")
    print(f"  Cash        : {pct(state['available_cash'], eq):5.1f}%")
    print(f"  Invested    : {pct(state['total_invested'], eq):5.1f}%")
    print(f"  PnL unreal. : {pct(state['unrealized_pnl'], eq):+5.1f}%")
    if state["positions"]:
        print(f"\n  Open positions:")
        for p in state["positions"]:
            w = pct(p.get("amount", 0), eq)
            print(f"    {p.get('instrumentID', '?'):>8}  {w:5.1f}%")
    print(f"{'='*55}\n")


# ── Strategy executor ────────────────────────────────────────────────────────────
def execute_scenario(scenario_name, trades, state, dry_run=False):
    eq = state["equity"]
    cash = state["available_cash"]
    reserve = eq * MIN_CASH_RESERVE_PCT

    print(f"\n[Strategy] Executing Scenario {scenario_name}")
    print(f"  Equity: ~100%  |  Cash available: {pct(cash, eq):.1f}%  |  Reserve: {pct(reserve, eq):.1f}%")

    for symbol, cfg in trades.items():
        amount = eq * cfg["weight"]
        needed = amount
        if cash - needed < reserve:
            print(f"  [SKIP] {symbol}: insufficient cash (need {pct(needed, eq):.1f}%, have {pct(cash - reserve, eq):.1f}% usable)")
            continue

        # Get price for stop-loss calculation
        try:
            instrument_id = resolve_instrument(symbol)
            price = get_current_price(instrument_id)
        except Exception:
            price = None

        sl_rate = (price * cfg["stop_loss_pct"]) if price else None
        open_position(symbol, amount, stop_loss_rate=sl_rate, dry_run=dry_run)
        if not dry_run:
            cash -= amount


def run(scenario_override=None, dry_run=False):
    print("[1/4] Fetching portfolio state...")
    pnl = fetch_pnl()
    state = calc_equity(pnl)
    print_portfolio(state)

    eq = state["equity"]
    cash_pct = pct(state["available_cash"], eq)

    # Determine scenario
    if scenario_override and scenario_override.upper() in ("A", "B"):
        scenario = scenario_override.upper()
        print(f"[2/4] Scenario override: {scenario}")
    else:
        # Auto-detect: if no new positions and cash > 5%, assume Scénario A (post-Nvidia beat)
        scenario = "A" if cash_pct >= 5 else "B"
        print(f"[2/4] Auto-selected scenario {scenario} (cash={cash_pct:.1f}%)")

    print("[3/4] Resolving instruments and computing order sizes...")
    trades = SCENARIO_A if scenario == "A" else SCENARIO_B

    print("[4/4] Placing orders...")
    execute_scenario(scenario, trades, state, dry_run=dry_run)

    print("\n[Done] Waiting 60s for PnL cache to refresh...")
    if not dry_run:
        time.sleep(60)
        pnl2 = fetch_pnl()
        state2 = calc_equity(pnl2)
        print_portfolio(state2)
        write_md_update(state2, scenario)
    else:
        print("  [DRY-RUN] Skipping post-trade PnL check and MD update.")


# ── Markdown update ──────────────────────────────────────────────────────────────
def write_md_update(state, scenario):
    eq = state["equity"]
    now = datetime.now(timezone.utc).strftime("%Y-%m-%d %H:%M UTC")
    lines = [
        f"\n---\n",
        f"## Auto-trade — {now}\n\n",
        f"**Scénario exécuté :** {scenario}\n\n",
        f"### Portefeuille post-exécution\n\n",
        f"| Instrument | Poids |\n|------------|-------|\n",
    ]
    for p in state.get("positions", []):
        w = pct(p.get("amount", 0), eq)
        lines.append(f"| {p.get('instrumentID', '?')} | {w:.1f}% |\n")
    cash_pct = pct(state["available_cash"], eq)
    lines.append(f"| Liquidités | {cash_pct:.1f}% |\n")
    lines.append(f"\n**PnL non-réalisé :** {pct(state['unrealized_pnl'], eq):+.2f}%\n")

    with open("etoro_trades.md", "a", encoding="utf-8") as f:
        f.writelines(lines)
    print("[MD] etoro_trades.md updated.")


# ── Entry point ──────────────────────────────────────────────────────────────────
if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="eToro automated trader")
    parser.add_argument("--dry-run", action="store_true", help="Simulate without placing real orders")
    parser.add_argument("--scenario", choices=["A", "B", "auto"], default="auto",
                        help="Force scenario A (Nvidia beat), B (miss), or auto-detect")
    args = parser.parse_args()

    try:
        run(scenario_override=None if args.scenario == "auto" else args.scenario,
            dry_run=args.dry_run)
    except KeyboardInterrupt:
        print("\nAborted.")
        sys.exit(0)
    except Exception as e:
        print(f"\n[ERROR] {e}")
        sys.exit(1)
