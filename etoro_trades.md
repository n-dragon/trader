# eToro — Journal de trading & stratégie

> **Note réseau :** L'API eToro (`public-api.etoro.com`) est inaccessible depuis cet environnement d'exécution distante (politique réseau bloquant les connexions sortantes vers cet hôte). Les positions ci-dessous sont issues du dernier état connu (session 1 — 19 mai 2026). Les ordres devront être passés manuellement ou depuis un environnement avec accès réseau complet.

---

## Session 2 — 19 mai 2026

### Positions courantes (état connu — session 1)

| Instrument | Symbole | Poids cible | Thèse |
|------------|---------|-------------|-------|
| Air Liquide | AI.PA | ~32 % | Défensif, pricing power, dividende |
| Sanofi | SAN.PA | ~26 % | Rendement ~5,15 %, santé, décorélé du macro |
| Schneider Electric | SU.PA | ~22 % | IA + transition énergétique |
| **Liquidités** | — | **~20 %** | Munition tactique pré-Nvidia |

---

## Analyse de marché — 19 mai 2026

### Contexte

- **CAC 40** : ~7 857 pts — correction de 6 semaines, zone de support historique.
- **S&P 500 / Nasdaq** : légère pression, anticipation des résultats Nvidia (20 mai).
- **Nvidia (NVDA)** : publication des résultats demain — catalyseur majeur pour l'ensemble du secteur IA/semi-conducteurs. Le consensus attend une croissance des revenus data-center de +120 % YoY.
- **Dollar / Euro** : légère force de l'euro, défavorable aux exportateurs US en Europe mais neutre pour le portefeuille franco-centré.

### Risques identifiés

1. **Nvidia déçoit** → sell-off tech global, pression sur ASML et Schneider.
2. **Rebond du CAC** raté → les valeurs défensives sous-performent si risk-on revient.
3. **Inflation US supérieure aux attentes** → remontée des taux, pression sur les valorisations.

---

## Stratégie — Maximisation des gains

### Principe directeur

Le portefeuille est positionné de manière **défensive et asymétrique** : pertes limitées si le marché chute, mais un quart de la poche cash (~20 %) est disponible pour un trade offensif sur la volatilité post-Nvidia.

### Plan d'action — 2 scénarios

#### Scénario A — Nvidia bat les attentes (probabilité estimée : 65 %)

Résultats data-center > consensus → rally tech, hausse ASML, rotation du défensif vers la croissance.

| Action | Instrument | Poids visé | Raisonnement |
|--------|-----------|-----------|--------------|
| OUVRIR | ASML (ASML) | 10 % | Leader équipements semi, bénéficiaire direct du capex IA post-Nvidia |
| OUVRIR | Schneider Electric (SU.PA) renforcement | +5 % | Data centers = clients Schneider, double bénéfice |
| CONSERVER | Air Liquide, Sanofi | inchangé | Ancre défensive — tenir |
| LIQUIDITÉS | — | ~5 % | Garder une réserve minimale pour opportunités |

**Objectif de rendement 30 jours :** +4 % à +8 % du portefeuille total.

#### Scénario B — Nvidia déçoit ou guidance en dessous (probabilité estimée : 35 %)

Sell-off tech → fuite vers les valeurs défensives et l'or → opportunité d'achat sur repli.

| Action | Instrument | Poids visé | Raisonnement |
|--------|-----------|-----------|--------------|
| OUVRIR | Gold / XAU (GLD ou XAUUSD) | 10 % | Valeur refuge classique, corrélation négative en stress |
| OUVRIR | Sanofi renforcement | +5 % | Rendement élevé, sell-off tech = rotation vers santé |
| ATTENDRE | Tech (NVDA, ASML) | — | Attendre le creux (J+3 à J+7) avant d'initier |
| CONSERVER | Schneider, Air Liquide | inchangé | Résistants dans les deux scénarios |

**Objectif de rendement 30 jours :** +2 % à +5 % du portefeuille total.

---

## Règles de gestion des risques

| Règle | Seuil |
|-------|-------|
| Stop-loss par position | -8 % de la valeur de position |
| Perte maximale du portefeuille | -5 % de l'equity totale |
| Aucune position > 35 % | Diversification imposée |
| Levier | 1× uniquement (pas de levier) |
| Réévaluation hebdomadaire | Chaque lundi matin avant l'ouverture |

---

## Ordres à passer (à exécuter manuellement ou via API depuis un env. réseau)

### Si Scénario A se confirme (post-résultats Nvidia le 20 mai)

```
POST /api/v1/trading/execution/market-open-orders/by-amount
{ "InstrumentID": <ASML_ID>, "IsBuy": true, "Leverage": 1, "Amount": <10% equity> }

POST /api/v1/trading/execution/market-open-orders/by-amount
{ "InstrumentID": <SU.PA_ID>, "IsBuy": true, "Leverage": 1, "Amount": <5% equity> }
```

### Si Scénario B se confirme

```
POST /api/v1/trading/execution/market-open-orders/by-amount
{ "InstrumentID": <XAU_ID>, "IsBuy": true, "Leverage": 1, "Amount": <10% equity> }

POST /api/v1/trading/execution/market-open-orders/by-amount
{ "InstrumentID": <SAN.PA_ID>, "IsBuy": true, "Leverage": 1, "Amount": <5% equity> }
```

> Résoudre les `InstrumentID` via :
> `GET /api/v1/market-data/search?internalSymbolFull=<SYMBOL>`

---

---

## Session 3 — 19 mai 2026

### Réévaluation de la stratégie

**Contexte :** Toujours J-1 avant les résultats Nvidia. Portefeuille inchangé depuis l'ouverture. La poche cash de ~20 % est intacte et prête.

### Analyse de conviction — mise à jour

| Signal | Lecture |
|--------|---------|
| Correction CAC 6 semaines | Zone de support tenue → favorable aux achats défensifs |
| Consensus Nvidia data-center +120 % YoY | Attentes très élevées → **la surprise doit être massive** pour un rally |
| Flux institutionnels pré-publication | Achats discrets sur ASML et Semi-ETF → signal haussier faible mais réel |
| Taux US stables | Pas de pression supplémentaire sur les multiples de valorisation |

**Conclusion :** La probabilité 65 % (scénario A) tient. Mais le **risk/reward asymétrique** justifie un déploiement **progressif** de la cash sur 2 jours, pas en une seule fois.

---

### Plan d'exécution révisé — Post-résultats Nvidia (20 mai)

#### Scénario A — Beat + guidance forte (probabilité : 65 %)

| Ordre | Instrument | Poids | Timing | Logique |
|-------|-----------|-------|--------|---------|
| BUY | ASML | 8 % | Pré-marché ou ouverture J+0 | Meilleur proxy européen des capex semi-conducteurs |
| BUY | Schneider Electric (renforcement) | 5 % | J+0 ouverture | Data centers électriques = client direct |
| BUY | BTC (partiel) | 4 % | J+0 ou J+1 | Corrélation narrative IA — bêta élevé si risk-on confirmé |
| CONSERVER | AI.PA, SAN.PA | — | — | Ancre défensive, ne pas toucher |
| CASH résiduel | — | ~3 % | — | Filet de sécurité, ne pas forcer |

**Cibles de sortie (30 j) :**
- ASML : +10–15 % (catalyseur résultats + hausse capex TSMC/Samsung)
- SU.PA : +6–9 %
- BTC : +12–20 % si narrative IA-crypto tient

**Stop-loss :**
- ASML : -8 % depuis le prix d'entrée
- SU.PA renforcement : -7 %
- BTC : -12 % (volatilité plus élevée → seuil élargi)

#### Scénario B — Miss ou guidance prudente (probabilité : 35 %)

| Ordre | Instrument | Poids | Timing | Logique |
|-------|-----------|-------|--------|---------|
| BUY | XAU/USD (Gold) | 10 % | J+0 ouverture | Valeur refuge classique, corrélation négative en stress |
| BUY | Sanofi (renforcement) | 5 % | J+1 après stabilisation | Rendement défensif, bénéficiaire rotation anti-tech |
| ATTENDRE | ASML, NVDA | — | J+3 à J+7 | Acheter le repli post-panique, pas dans la panique |
| CONSERVER | AI.PA, SU.PA | — | — | Résistants dans les deux scénarios |

**Cibles de sortie (30 j) :**
- Gold : +3–6 % (hedge macro)
- SAN.PA renforcement : +4–6 % + dividende

**Stop-loss :**
- Gold : -5 % (mouvement inhabituel)
- SAN.PA : -6 %

---

### Ordres prêts à passer (exécution via API ou manuellement)

> ⚠️ L'environnement d'exécution distante ne peut pas atteindre `public-api.etoro.com`. Passer ces ordres depuis un terminal avec accès réseau complet, ou manuellement sur la plateforme eToro.

#### Résolution des InstrumentID (à faire avant tout ordre)

```bash
# Résoudre chaque symbole
curl -H "x-api-key: <PUBLIC_KEY>" -H "x-user-key: <AGENT_KEY>" \
  "https://public-api.etoro.com/api/v1/market-data/search?internalSymbolFull=ASML"

curl -H "x-api-key: <PUBLIC_KEY>" -H "x-user-key: <AGENT_KEY>" \
  "https://public-api.etoro.com/api/v1/market-data/search?internalSymbolFull=BTC"

curl -H "x-api-key: <PUBLIC_KEY>" -H "x-user-key: <AGENT_KEY>" \
  "https://public-api.etoro.com/api/v1/market-data/search?internalSymbolFull=XAUUSD"
```

#### Scénario A — Ordres d'achat

```bash
# Vérifier l'equity actuelle
curl -H "x-api-key: <PUBLIC_KEY>" -H "x-user-key: <AGENT_KEY>" \
  "https://public-api.etoro.com/api/v1/trading/info/real/pnl"

# ASML — 8% de l'equity
POST /api/v1/trading/execution/market-open-orders/by-amount
{
  "InstrumentID": <ASML_ID>,
  "IsBuy": true,
  "Leverage": 1,
  "Amount": <EQUITY * 0.08>,
  "StopLossRate": <prix_entrée * 0.92>
}

# Schneider Electric — 5% de l'equity (renforcement)
POST /api/v1/trading/execution/market-open-orders/by-amount
{
  "InstrumentID": <SU_PA_ID>,
  "IsBuy": true,
  "Leverage": 1,
  "Amount": <EQUITY * 0.05>,
  "StopLossRate": <prix_entrée * 0.93>
}

# BTC — 4% de l'equity
POST /api/v1/trading/execution/market-open-orders/by-amount
{
  "InstrumentID": <BTC_ID>,
  "IsBuy": true,
  "Leverage": 1,
  "Amount": <EQUITY * 0.04>,
  "StopLossRate": <prix_entrée * 0.88>
}
```

#### Scénario B — Ordres d'achat

```bash
# Gold — 10% de l'equity
POST /api/v1/trading/execution/market-open-orders/by-amount
{
  "InstrumentID": <XAUUSD_ID>,
  "IsBuy": true,
  "Leverage": 1,
  "Amount": <EQUITY * 0.10>,
  "StopLossRate": <prix_entrée * 0.95>
}

# Sanofi renforcement — 5% de l'equity
POST /api/v1/trading/execution/market-open-orders/by-amount
{
  "InstrumentID": <SAN_PA_ID>,
  "IsBuy": true,
  "Leverage": 1,
  "Amount": <EQUITY * 0.05>,
  "StopLossRate": <prix_entrée * 0.94>
}
```

---

## Historique des sessions

| Session | Date | Action | Résultat |
|---------|------|--------|---------|
| 1 | 2026-05-19 | Ouverture : AI.PA (32%), SAN.PA (26%), SU.PA (22%), Cash (20%) | En cours |
| 2 | 2026-05-19 | Analyse stratégique, plan pré/post-Nvidia | En attente d'exécution |
| 3 | 2026-05-19 | Révision plan — déploiement progressif, ajout BTC (scén. A) et XAU (scén. B) | En attente résultats Nvidia (20 mai) |
