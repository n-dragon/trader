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

## Historique des sessions

| Session | Date | Action | Résultat |
|---------|------|--------|---------|
| 1 | 2026-05-19 | Ouverture : AI.PA (32%), SAN.PA (26%), SU.PA (22%), Cash (20%) | En cours |
| 2 | 2026-05-19 | Analyse stratégique, plan pré/post-Nvidia | En attente d'exécution |
