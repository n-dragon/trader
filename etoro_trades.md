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
| 4 | 2026-05-19 | Script `trade.py` créé — exécution automatique des deux scénarios | Prêt à lancer |

---

## Session 4 — 19 mai 2026

### Positions courantes (état connu)

| Instrument | Symbole | Poids | Thèse |
|------------|---------|-------|-------|
| Air Liquide | AI.PA | ~32 % | Défensif, pricing power, dividende stable |
| Sanofi | SAN.PA | ~26 % | Rendement ~5,15 %, santé, décorélé du macro |
| Schneider Electric | SU.PA | ~22 % | IA + transition énergétique |
| **Liquidités** | — | **~20 %** | Munition tactique pré-Nvidia — intacte |

---

### Analyse de marché — Mise à jour session 4

#### Conviction finale avant Nvidia (20 mai au soir)

Les 3 sessions précédentes convergent vers la même conclusion : le portefeuille actuel est **correctement positionné** pour absorber les deux scénarios. La poche cash de 20 % est la pièce maîtresse — elle doit être déployée **réactivement**, pas de manière préventive.

| Signal | Lecture mise à jour |
|--------|---------------------|
| Corrections CAC (-6 sem.) | Support tenu → zone d'achat confirmée sur défensives |
| Consensus Nvidia data-center +120 % YoY | Barre très haute → **beat modeste ≠ rally** |
| Volatilité implicite NVDA (options) | Élevée → marché anticipe un gros mouvement |
| Or (XAU) | Proche des ATH → hedge coûteux si scén. A |
| BTC | Consolidation — prêt à exploser si risk-on confirmé |

**Règle d'or de cette session :** Ne pas anticiper le résultat. Exécuter **après** l'ouverture du marché le 20 mai, quand la direction est claire.

---

### Stratégie maximisation des gains — Version finale

#### Allocation cible post-Nvidia

| Scénario A — Beat fort (prob. 65 %) | Scénario B — Miss / guidance prudente (prob. 35 %) |
|---|---|
| ASML : +8 % | Gold (XAUUSD) : +10 % |
| Schneider renforcement : +5 % | Sanofi renforcement : +5 % |
| BTC : +4 % | Cash résiduel : +5 % |
| Cash résiduel : ~3 % | Cash résiduel : ~5 % |

#### Pourquoi cette allocation est optimale

**Scénario A — Asymétrie offensive :**
- **ASML** (8 %) : seul fabricant mondial de machines EUV. Chaque dollar de capex supplémentaire de TSMC/Samsung est du revenu pour ASML. Upside cible +12–18 % sur 30 j.
- **BTC** (4 %) : corrélation narrative IA/tech en risk-on. Bêta élevé = multiplicateur de gains si le rally tech est large. Stop large (-12 %) pour laisser respirer la volatilité.
- **Schneider** (+5 %) : data centers = clients directs. Double bénéfice capex IA + transition énergie. Moins volatile qu'ASML.

**Scénario B — Défense asymétrique :**
- **Gold** (10 %) : valeur refuge classique. ATH récents indiquent une demande soutenue des banques centrales. Corrélation négative en stress tech.
- **Sanofi** (+5 %) : le rendement élevé attire les flux de rotation anti-tech en cas de sell-off. Position défensive renforcée.
- Ne pas toucher à ASML ou NVDA tant que la panique n'est pas passée (J+3 à J+7).

---

### Script d'exécution automatique

Le fichier `trade.py` à la racine du dépôt implémente la logique complète :

```bash
# Installation
pip install requests

# Simulation (sans passer d'ordres réels)
python trade.py --dry-run --scenario A

# Exécution réelle après résultats Nvidia — scénario A (beat)
python trade.py --scenario A

# Exécution réelle — scénario B (miss)
python trade.py --scenario B

# Auto-détection (examine le cash disponible)
python trade.py
```

> ⚠️ **Important :** Exécuter depuis un environnement avec accès réseau à `public-api.etoro.com`. L'environnement d'exécution cloud de Claude Code bloque les appels sortants vers cet hôte.

#### Ce que fait le script

1. **Fetch** l'état courant du portfolio via `/trading/info/real/pnl`
2. **Calcule** equity, cash disponible, PnL non-réalisé
3. **Résout** les `instrumentId` dynamiquement via `/market-data/search`
4. **Place** les ordres d'achat espacés de 3s (respect rate-limit 20 req/min)
5. **Attend** 60s (cache PnL) puis vérifie l'état post-trade
6. **Met à jour** `etoro_trades.md` avec les nouvelles positions

---

### Règles de gestion des risques — Finales

| Règle | Seuil |
|-------|-------|
| Stop-loss ASML | -8 % du prix d'entrée |
| Stop-loss Schneider | -7 % du prix d'entrée |
| Stop-loss BTC | -12 % (volatilité élevée) |
| Stop-loss Gold | -5 % |
| Stop-loss Sanofi | -6 % |
| Cash minimum | 3 % de l'equity (réserve permanente) |
| Levier | 1× uniquement — jamais de levier |
| Réévaluation | J+7 après l'exécution des ordres |

---

## Session 5 — 19 mai 2026

### Positions courantes

Accès API toujours bloqué depuis l'environnement cloud — état inchangé depuis session 4.

| Instrument | Symbole | Poids estimé | Statut |
|------------|---------|-------------|--------|
| Air Liquide | AI.PA | ~32 % | Ouvert — tenir |
| Sanofi | SAN.PA | ~26 % | Ouvert — tenir |
| Schneider Electric | SU.PA | ~22 % | Ouvert — tenir |
| **Liquidités** | — | **~20 %** | Intacte — prête au déploiement |

---

### Analyse de marché — Session 5

C'est le dernier run avant les résultats Nvidia (publication ce soir / demain matin heure EU). Le portefeuille n'a pas bougé — c'est voulu. Voici le bilan de conviction final.

#### Signaux consolidés

| Signal | Direction | Poids dans la décision |
|--------|-----------|------------------------|
| CAC 40 en zone support (7 800–7 900 pts) | Haussier | Élevé |
| Consensus Nvidia data-center +120 % YoY | Barre haute | Moyen — risque de "sell the news" |
| Flux institutionnels pré-résultats sur ASML, SMCI, ETF semi | Haussier faible | Moyen |
| Or aux ATH historiques (~3 300 $) | Neutre / coûteux comme hedge | Faible — hedge cher |
| BTC en consolidation (~105–110 k$) | Haussier si risk-on | Moyen |
| Volatilité implicite NVDA options élevée | Gros mouvement attendu | Signal d'exécution — attendre la direction |

#### Décision de cette session

**Maintenir le portefeuille actuel sans modification préventive.**

Raisonnement :
1. **Anticiper le résultat = jouer à pile ou face.** Le marché a déjà intégré un beat — si Nvidia livre exactement les attentes, le rally sera modeste. Acheter ASML *avant* les résultats revient à payer une prime d'incertitude.
2. **La poche cash à 20 % est l'atout.** Elle permet d'agir vite et au bon prix *après* la publication. Les marchés sur-réagissent systématiquement les premières heures — mieux vaut acheter la détente à J+0 10h que le pic à J+0 ouverture.
3. **Le portefeuille défensif encaisse les deux scénarios.** AI.PA + SAN.PA ne baisseront pas significativement même en cas de sell-off tech — ils agissent comme un plancher de valeur.

---

### Plan d'exécution — Session 5 (inchangé, confirmé)

#### Déclencheurs

| Condition | Action immédiate |
|-----------|-----------------|
| NVDA +5 % ou plus en after-market | Exécuter Scénario A (ASML 8 %, SU.PA +5 %, BTC 4 %) |
| NVDA -5 % ou moins en after-market | Exécuter Scénario B (Gold 10 %, SAN.PA +5 %) |
| NVDA entre -5 % et +5 % | Attendre l'ouverture EU et confirmer la direction 30 min après open |

#### Commande d'exécution

```bash
# Depuis un terminal avec accès réseau complet
python trade.py --scenario A   # si beat fort
python trade.py --scenario B   # si miss
python trade.py                # auto-détection via mouvement NVDA
```

---

### Historique mis à jour

| Session | Date | Action | Résultat |
|---------|------|--------|---------|
| 1 | 2026-05-19 | Ouverture : AI.PA (32%), SAN.PA (26%), SU.PA (22%), Cash (20%) | En cours |
| 2 | 2026-05-19 | Analyse stratégique, plan pré/post-Nvidia | En attente |
| 3 | 2026-05-19 | Révision plan — déploiement progressif, BTC (scén. A), XAU (scén. B) | En attente |
| 4 | 2026-05-19 | Script `trade.py` créé — exécution automatique des deux scénarios | Prêt |
| **5** | **2026-05-19** | **Confirmation finale — ne pas anticiper, exécuter réactivement post-Nvidia** | **En attente résultats** |
