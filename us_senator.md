# Suivi des positions des sénateurs américains — Achats (`txType=buy`)

> **Source visée :** [CapitolTrades — trades (buy)](https://www.capitoltrades.com/trades?txType=buy)
> **Note réseau :** CapitolTrades et l'ensemble des agrégateurs de trades du Congrès (Quiver Quantitative, Trendlyne, InsiderFinance, StockCircle, MarketBeat, CREW…) renvoient une erreur **HTTP 403** depuis cet environnement d'exécution distant (politique réseau + protection anti-bot). Trendlyne expose bien une fenêtre récente (divulgations du **11 au 20 juin 2026**) mais son contenu détaillé n'est pas accessible ici. Les données ci-dessous ont donc été **reconstituées via recherche web** à partir des mêmes divulgations publiques (STOCK Act / PTR du Sénat) relayées par la presse financière. Pour un suivi en temps réel, à rafraîchir depuis un environnement avec accès réseau complet.
> **Dernière mise à jour :** 2026-08-03 (rafraîchissement #7 — **fenêtre calme côté `buy` sénateurs (27 juil.–3 août 2026)** : aucun nouvel achat d'action de sénateur isolable de façon fiable ; l'actualité reste dominée par la **violation STOCK Act d'Alan Armstrong (R-OK)**, dont le **chiffrage est affiné** par la presse (Oklahoma Watch / KGOU / NOTUS, 27 juil.) à **3,24 M$–16,05 M$** de transactions déclarées hors délai ; côté réglementaire, le **Stop Insider Trading Act (H.R. 7008)** — adopté par la Chambre le 22 juil. — est désormais **transmis au Sénat** (aucun vote programmé). Divulgations tardives de la fenêtre = **membres de la Chambre** (Crenshaw, Laurel Lee, Cleo Fields/TSMC) → **hors périmètre sénateurs**. Précédent : rafraîchissement #6, 2026-07-27.)

---

## 🆕 Nouveautés de ce rafraîchissement (2026-08-03)

CapitolTrades et l'ensemble des agrégateurs (Quiver, Trendlyne, StockCircle, MarketBeat, Benzinga, InsiderFinance, CongressStock, GovTrades…) restent en **HTTP 403** depuis cet environnement d'exécution distant (politique réseau + anti-bot). Les éléments ci-dessous ont été **reconstitués via recherche web** à partir des divulgations STOCK Act / PTR du Sénat relayées par la presse (Oklahoma Watch, KGOU, NOTUS, Benzinga, GovTrack, Congress.gov).

**Constat de la fenêtre 27 juillet – 3 août 2026 :** c'est une **fenêtre calme pour les *achats* de sénateurs**. Aucun **nouvel achat d'action de sénateur** postérieur aux positions déjà tabulées (Armstrong fin mars ; Peters/AT&T 29 juin ; Mullin/PH 12 juin) n'est **isolable de façon fiable** dans cette fenêtre — cohérent avec l'approche de la **pause estivale du Sénat** et le **momentum politique anti-conflit** à son plus haut (voir H.R. 7008 ci-dessous). Les divulgations tardives de la fenêtre (Trendlyne 21 juil.–1er août) concernent des **membres de la Chambre** — **Dan Crenshaw (R-TX)**, **Laurel Lee (R-FL)**, **Cleo Fields (D-LA)** achat **TSMC** le 10 juil. — donc **hors périmètre « sénateurs »**. **Ashley Moody (R-FL)** : toujours **aucune transaction depuis avril 2025**.

Ce rafraîchissement apporte donc **(a)** un **affinage chiffré** de l'affaire Armstrong (donnée, pas nouvel achat) et **(b)** une **mise à jour du calendrier réglementaire** (H.R. 7008 transmis au Sénat), tous deux directement pertinents pour la colonne « délit d'initié ».

### (a) Recap `buy` — aucun nouvel achat sénateur ; affinage de la divulgation Armstrong (R-OK)

> ℹ️ **Aucune nouvelle ligne d'achat** ce rafraîchissement. Le tableau ci-dessous **reconduit l'achat sénateur le plus récent déjà suivi** (Armstrong, fin mars 2026), avec le **chiffrage total affiné** par la presse du 27 juillet. Les colonnes « probabilité de gain » et « probabilité de délit d'initié » sont **inchangées** par rapport au raisonnement du rafraîchissement #6.

| Sénateur | Parti / État | Commission sensible | Société | Ticker | Date d'achat | Montant | Probabilité de gain | **Probabilité de délit d'initié** |
|----------|--------------|---------------------|---------|--------|--------------|---------|---------------------|-----------------------------------|
| **Alan Armstrong** (reconduit) | R — Oklahoma | Profil **énergie** (ex-PDG Williams) ; siège d'intérim | Apple / Alphabet / Nvidia / Berkshire | AAPL · GOOGL · NVDA · BRK | **~2026-03-25** | AAPL ≥ 250 k$ ; autres ≥ 50 k$ | Modérée-Élevée (~60 %) — méga-caps ; NVDA momentum IA le plus fort | **Très faible-Faible** (panier *direct indexing* diversifié, méga-cap, ordre gérant tiers J+1 de la prise de fonction ; financé par la sortie de son propre titre WMB) |

> 🔎 **Affinage Armstrong (Oklahoma Watch / KGOU / NOTUS, 27 juil. 2026)** : la presse chiffre désormais les transactions **déclarées hors délai** à **3,24 M$–16,05 M$** (fourchettes réglementaires), sur **~700+ opérations** réalisées dans les jours suivant sa prestation de serment du **24 mars 2026** et divulguées **fin juillet**, soit **> 2 mois après** le délai de 45 jours du STOCK Act. Côté **`buy`**, il s'agit toujours d'une **rotation « Magnificent Seven »** via *direct indexing* (gérant tiers), **financée par la liquidation de son ancien groupe Williams (WMB)**. ➡️ **Probabilité de délit d'initié : Très faible-Faible** (panier algorithmique de méga-caps, avant tout accès à une info de commission ; désengagement de l'émetteur où il détiendrait le plus d'info privilégiée) ; **la gravité réelle porte sur la *transparence* (Élevée)** — violation caractérisée du STOCK Act, la plus importante de 2026. **Légal** sur le fond ; **infraction de déclaration** avérée.

### (b) 🏛️ Contexte réglementaire — H.R. 7008 transmis au Sénat (statut au 3 août 2026)

- Le **Stop Insider Trading Act (H.R. 7008**, Steil R-WI) — adopté par la **Chambre le 22 juillet 2026** (232-198) — est désormais **transmis au Sénat** (*received in the Senate*). **Aucun vote de la chambre haute n'est programmé** à ce jour.
- **Dispositions clés (rappel)** : interdiction pour les **membres, conjoints et enfants à charge** d'**acheter des actions cotées** ; **préavis public de 7 à 14 jours** avant toute **vente** ; **pénalité 2 000 $ ou 10 %** de la valeur (le plus élevé) ; **confiscation des plus-values**. Titres **déjà détenus** et **privés** restent autorisés. Le texte comporte des **dispositions annexes controversées** (voter ID) qui compliquent son adoption au Sénat (règle des 60 voix).
- ➡️ **Impact sur ce suivi** : tant que le Sénat n'a pas agi, le suivi « achats de sénateurs » reste pertinent ; mais le **coût réputationnel** des achats récents (et des retards de déclaration type Armstrong) est **au plus haut**, ce qui **atténue mécaniquement le volume d'achats** — cohérent avec la fenêtre calme observée.

> 🔁 **Hors périmètre `buy` / hors « sénateurs » ce mois-ci** : divulgations tardives de la fenêtre = **Chambre** (Crenshaw R-TX ; Laurel Lee R-FL ; **Cleo Fields D-LA** — achat **TSMC** 1–15 k$ le 10 juil., divulgué le 30 juil.). Côté **sénateurs**, aucun **achat** nouveau isolable ; **Moody (R-FL)** sans transaction depuis avril 2025 ; pas de nouvel achat confirmé pour Mullin/Hickenlooper/Peters postérieur aux lignes déjà tabulées.

---

## 🆕 Nouveautés de ce rafraîchissement (2026-07-27)

CapitolTrades et l'ensemble des agrégateurs (Quiver, Trendlyne, StockCircle, MarketBeat, Benzinga, InsiderFinance, CongressStock…) restent en **HTTP 403** depuis cet environnement (politique réseau + anti-bot ; NOTUS et Quiver renvoient également 403 sur les pages détaillées). Les éléments ci-dessous ont été **reconstitués via recherche web** à partir des divulgations STOCK Act / PTR du Sénat relayées par la presse financière (Benzinga, NOTUS, Washington Post, Fortune, Quiver, moomoo).

**Constat de la fenêtre 14–27 juillet 2026 :** l'évènement dominant est l'entrée d'un **nouveau sénateur**, **Alan Armstrong (R-OK)**, nommé le 24 mars 2026 par le gouverneur Stitt pour occuper le siège laissé vacant par **Markwayne Mullin** (parti diriger le **DHS**). Armstrong, ex-PDG (2011-2025) du groupe gazier **Williams (WMB)**, a réalisé **~703 transactions** dans les jours suivant sa prestation de serment (fin mars 2026) et ne les a divulguées qu'en **juillet 2026 — plus de deux mois après le délai de 45 jours** : c'est la **plus grosse divulgation tardive de l'année** (violation du STOCK Act). Côté **`buy`**, il s'agit d'une **rotation « Magnificent Seven »** exécutée par un gérant tiers via une stratégie de ***direct indexing*** (panier large et algorithmique), financée par la **liquidation de sa participation historique dans Williams (WMB)**.

### (a) Achats — Alan Armstrong (R — Oklahoma) — rotation *direct indexing* (fin mars 2026)

| Sénateur | Parti / État | Commission sensible | Société | Ticker | Date d'achat | Montant | Probabilité de gain | **Probabilité de délit d'initié** |
|----------|--------------|---------------------|---------|--------|--------------|---------|---------------------|-----------------------------------|
| **Alan Armstrong** 🆕 | R — Oklahoma | Profil **énergie** (ex-PDG Williams) ; commissions en cours d'attribution (siège d'intérim) | Apple | AAPL | **~2026-03-25** (fin mars) | **≥ 250 k$** | Modérée-Élevée (~60 %) — méga-cap, plus gros achat du lot | **Très faible-Faible** (panier *direct indexing* diversifié, méga-cap, ordre gérant tiers J+1 de l'entrée en fonction, avant tout accès à une info de commission) |
| **Alan Armstrong** 🆕 | R — Oklahoma | idem | Alphabet | GOOGL | **~2026-03-25** (fin mars) | **≥ 50 k$** | Modérée-Élevée (~60 %) — méga-cap tech | **Très faible-Faible** (idem — basket algorithmique) |
| **Alan Armstrong** 🆕 | R — Oklahoma | idem | Nvidia | NVDA | **~2026-03-25** (fin mars) | **≥ 50 k$** | Élevée (~65 %) — momentum IA soutenu | **Faible** (méga-cap, basket ; pas de pari isolé) |
| **Alan Armstrong** 🆕 | R — Oklahoma | idem | Berkshire Hathaway | BRK | **~2026-03-25** (fin mars) | **≥ 50 k$** | Modérée (~55 %) — holding diversifié défensif | **Très faible** (holding diversifié) |

> 🔎 **Contexte Armstrong (700+ trades, fin mars 2026)** : total estimé **≥ 25 M$** sur **703 transactions**, dont **≥ 7,66 M$ d'achats** et **≥ 17,37 M$ de ventes**. La plus grosse opération est une **vente** : liquidation de **5–25 M$** d'actions **Williams (WMB)** (son ancien groupe) + **250–500 k$** d'options WMB → **désengagement** de l'émetteur où il pourrait détenir une information privilégiée (facteur **atténuant** net). Ses cinq plus gros mouvements incluent **Apple, Alphabet et Nvidia** ; ses plus gros **achats** sont **AAPL (≥ 250 k$)**, puis **GOOGL / BRK / NVDA (≥ 50 k$ chacun)**. Une **note jointe au dépôt** précise : « *les transactions de mars 2026 ont été initiées par un conseiller tiers pour mettre en œuvre une stratégie de* ***direct indexing*** ». ➡️ **Probabilité de délit d'initié : Très faible-Faible** — panier large et algorithmique de méga-caps, exécuté par un gérant tiers **le lendemain de la prise de fonction** (avant tout accès à une information de commission), et **financé par la sortie de son propre ancien titre**. **En revanche, la gravité côté *transparence* est Élevée** : ~700 déclarations **hors délai** de plus de deux mois = **violation caractérisée du STOCK Act** (la plus importante de 2026), qui plus est la **semaine même où la Chambre vote l'interdiction** (voir ci-dessous). Trades **légaux** sur le fond ; la faute est la **déclaration tardive**.

### (b) 🏛️ Contexte réglementaire majeur — la Chambre adopte le *Stop Insider Trading Act* (22 juillet 2026)

- La **Chambre des représentants** a adopté le **22 juillet 2026**, par **232-198** (vote bipartite), le **Stop Insider Trading Act** (**H.R. 7008**), porté par **Bryan Steil (R-WI)**.
- **Dispositions clés** : interdiction pour les **membres du Congrès, leur conjoint et leurs enfants à charge** d'**acheter des actions cotées** ; obligation de **préavis public de 7 jours** avant toute **vente** ; **pénalité de 2 000 $ ou 10 % de la valeur** de l'investissement concerné (le plus élevé) ; **confiscation des plus-values** réalisées. Les titres **déjà détenus** et les **titres privés** restent autorisés.
- **Perspective Sénat : incertaine.** Le texte devrait **buter sur la règle des 60 voix** (filibuster) et sur des dispositions ajoutées (voter ID) ; les précédentes propositions d'interdiction ont **calé à la chambre haute**. À noter (Fortune) : le texte **exempterait les ~3 600 trades du T1 de Donald Trump**.
- ➡️ **Impact sur ce suivi** : si (et seulement si) le Sénat suivait, la colonne « achats de sénateurs » deviendrait **structurellement vide** pour les titres cotés. En l'état (juillet 2026), le suivi reste pertinent — mais le **momentum politique anti-conflit** est à son plus haut, ce qui accroît le **coût réputationnel** des cas comme Armstrong (retard) ou Mullin (chevauchement défense).

> 🔁 **Hors périmètre `buy` / non attribuable ce mois-ci** : **Angus King (I-ME)** apparaît dans les trackers (gains mensuels estimés par Quiver ~120 k$–515 k$ sur son portefeuille) mais ses **achats datés isolables sont de 2025** (XOM, NVDA, GOOGL, JPM, BAC, AMD, AMAT, UBER — juillet 2025) ; **aucun nouvel achat 2026 clairement daté** n'est isolable de façon fiable ici → non repris dans le tableau. Les **ventes** d'Armstrong (WMB en tête) sont hors périmètre `buy`. Côté **Chambre** (hors « sénateurs »), l'actualité reste dominée par les débats sur H.R. 7008.

---

## 🆕 Nouveautés de ce rafraîchissement (2026-07-13)

CapitolTrades et les agrégateurs (Quiver, Trendlyne, StockCircle, MarketBeat, Benzinga, Finviz, Barchart, GovTrades…) restent en **HTTP 403** depuis cet environnement (politique réseau + anti-bot). Les éléments ci-dessous proviennent de divulgations STOCK Act relayées par la presse (NOTUS, Benzinga, Colorado Newsline, Hoodline, Kavout).

**Constat de la fenêtre 7–13 juillet 2026 :** côté **sénateurs**, l'activité de divulgation récente est **dominée par des ventes** (Sheldon Whitehouse : Apple/AAPL le 24/06 divulgué le 08/07, Crown Castle/CCI le 25/06 divulgué le 08/07 → **hors périmètre `buy`**). **Aucun nouvel achat d'action de sénateur** n'apparaît dans cette fenêtre **au-delà du Peters/AT&T (T)** déjà listé au rafraîchissement #4. Les achats médiatisés de la semaine (**SpaceX** par Meuser & Cisneros ; **J&J** par Doggett/McCormick/McClain ; **Brookfield Renewables** par Salazar) concernent des membres de la **Chambre des représentants** → **hors périmètre « sénateurs »**.

Ce rafraîchissement apporte donc **(a)** deux **achats de sénateur** authentiques pas encore enregistrés (Hickenlooper, janvier 2026) et **(b)** une **affaire de conflit / transparence** nouvelle et directement pertinente pour la colonne « délit d'initié ».

### (a) Achats — John Hickenlooper (D — Colorado)

| Sénateur | Parti / État | Commission sensible | Société | Ticker | Date d'achat | Montant | Probabilité de gain | **Probabilité de délit d'initié** |
|----------|--------------|---------------------|---------|--------|--------------|---------|---------------------|-----------------------------------|
| **John Hickenlooper** 🆕 | D — Colorado | **Commerce, Science & Transportation** | Uber Technologies | UBER | **2026-01-14** | **100–250 k$** | Modérée (~55 %) — rebond (EPS +44 % T1, catalyseur robotaxi/Waymo, cibles ~100–115 $) mais titre −10 % YTD, volatil | **Modérée-Élevée** ⚠️ (Uber = plateforme de **transport**, cœur de juridiction de la commission Transports de Hickenlooper ; **pas de *blind trust* qualifié** → atténuation faible) |
| **John Hickenlooper** 🆕 | D — Colorado | Énergie (via Eaton) ; Commerce | Eaton Corporation | ETN | **2026-01-14** | **50–100 k$** | **Élevée (~65 %)** — momentum *data-center power* (commandes électriques +60 % a/a, +27 % sur 1 an) mais valorisation tendue | **Modérée** ⚠️ (électricité/réseau/data-center, recoupe la commission **Energy & Natural Resources** ; ordre passé par un gérant tiers selon son bureau) |

> 🔎 **Contexte Hickenlooper / Uber (UBER) & Eaton (ETN)** : le 14 janvier 2026, Hickenlooper a acheté **100 001–250 000 $** d'**Uber** et **50 001–100 000 $** d'**Eaton** (divulgation début février 2026, relayée jusqu'en juillet dans le sillage de l'affaire STOCK Act ci-dessous). **Uber** est une plateforme de **transport** relevant directement de la commission **Commerce, Science & Transportation** où il siège → **chevauchement de juridiction net**. **Eaton** (gestion de l'énergie, réseau électrique, alimentation des data-centers) recoupe la commission **Energy & Natural Resources**. Facteur **aggravant** relevé par la presse (Colorado Newsline, NOTUS) : Hickenlooper **n'a pas constitué de *blind trust* qualifié** approuvé par la commission d'éthique du Sénat, ce qui affaiblit l'argument « gérant tiers ». Trades **légaux**. Soupçon **Modéré-Élevé** (Uber) / **Modéré** (Eaton).

### (b) 🚨 Nouvelle affaire — violations du STOCK Act : Hickenlooper (D-CO) & Rounds (R-SD)

| Sénateur | Parti / État | Titre concerné | Ticker | Sens | Date transaction | Montant | Retard de divulgation | **Probabilité de délit d'initié / gravité** |
|----------|--------------|----------------|--------|------|------------------|---------|-----------------------|---------------------------------------------|
| **John Hickenlooper** 🚨 | D — Colorado | Palantir (via enfant à charge) | PLTR | Vente | 2025 | ~3 312 $ | Env. **1 an de retard** | **Modérée** ⚠️ — *violation de transparence* avérée (déclaration tardive), pas d'insider prouvé ; PLTR = contractant défense/ICE, sensible |
| **John Hickenlooper** 🚨 | D — Colorado | Liberty Broadband (conjoint) | LBRDK | Vente | ~2024 | **500 k–1 M$** | Près d'**un an de retard** | **Faible-Modérée** — violation de déclaration ; média (télécom, Commerce) |
| **Mike Rounds** 🚨 | R — Dakota du Sud | Aeronics Inc. (titre non coté) | — | Vente | ~début 2026 | **1–5 M$** | **> 5 mois de retard** | **Faible-Modérée** — titre non coté, violation de déclaration |

> 🔎 **Contexte (NOTUS, ~juillet 2026)** : deux sénateurs ont **enfreint le STOCK Act** par des divulgations **au-delà du délai de 45 jours**. **Hickenlooper (D-CO)** a déclaré avec ~1 an de retard la vente par son épouse d'actions **Liberty Broadband (500 k–1 M$)** et, tout aussi tardivement, la vente par son enfant à charge d'actions **Palantir (PLTR)** — valorisée 2–30 k$ dans le dépôt mais **3 312 $** selon son bureau ; Palantir est un **contractant défense/ICE** aux gros contrats fédéraux. **Rounds (R-SD)** a déclaré avec **plus de 5 mois de retard** la vente de **1–5 M$** d'actions **non cotées d'Aeronics Inc.** (fabricant d'équipements). ⚠️ Il s'agit de **violations de l'obligation de divulgation** (transparence), **pas d'accusations de délit d'initié** ; aucune connaissance d'information privilégiée n'est établie. La gravité tient au **retard** et, pour Hickenlooper, au **chevauchement** avec ses commissions (Commerce ; contractant défense).

> 🔁 **Hors périmètre `buy` / hors « sénateurs » ce mois-ci** : **Whitehouse (D-RI)** — ventes AAPL & CCI (juin, divulguées le 08/07). Cluster **SpaceX** (Meuser, Cisneros) et **santé/J&J** (Doggett, McCormick, McClain) + **Brookfield Renewables** (Salazar) = **membres de la Chambre**, non repris dans ce suivi Sénat. **Mullin (R-OK)** : pas de nouvel achat d'action confirmé postérieur à Parker-Hannifin (PH, 12/06).

---

## 🆕 Nouveautés de ce rafraîchissement (2026-07-06)

CapitolTrades et les agrégateurs (Quiver, Trendlyne, StockCircle, MarketBeat, Benzinga, Finviz, Barchart…) restent en **HTTP 403** depuis cet environnement (politique réseau + anti-bot). Les éléments ci-dessous proviennent de divulgations STOCK Act relayées par la presse financière (MarketBeat, Benzinga, Quiver, Markets Daily). **Un nouvel achat d'action** apparaît dans la fenêtre de divulgation de **fin juin / début juillet 2026**.

| Sénateur | Parti / État | Commission sensible | Société | Ticker | Date d'achat | Montant | Probabilité de gain | **Probabilité de délit d'initié** |
|----------|--------------|---------------------|---------|--------|--------------|---------|---------------------|-----------------------------------|
| **Gary Peters** 🆕 | D — Michigan | **Commerce, Science & Transportation** (télécom/FCC) ; Armed Services ; Homeland Security (RM) ; Appropriations | AT&T | T | **2026-06-29** | 1–15 k$ | Modérée (~55 %) — télécom défensif à haut dividende, faible volatilité, upside limité | **Faible-Modérée** ⚠️ (télécom sous juridiction Commerce/FCC, mais petit montant, AT&T méga-cap peu sensible à une info isolée ; Peters est **auteur de lois anti-conflits** et **ne se représente pas** en 2026 → soupçon atténué) |

> 🔎 **Contexte Peters / AT&T (T)** : achat de **1 001–15 000 $** d'actions **AT&T** le **29 juin 2026**, divulgué vers le **2 juillet 2026** (relayé par MarketBeat/Benzinga le 5 juillet). Peters (D-MI) siège à la commission **Commerce, Science & Transportation**, qui supervise les **télécommunications et la FCC** → chevauchement de juridiction avec un opérateur télécom. **Facteurs atténuants notables** : (1) le montant est **faible** (tranche 1–15 k$) ; (2) AT&T est une **méga-cap défensive à dividende élevé**, peu susceptible de bouger sur une information privilégiée ponctuelle ; (3) Peters est **co-auteur de plusieurs lois bipartites anti-conflits d'intérêts** (contractants fédéraux) ; (4) il a annoncé **ne pas se représenter en 2026** (fin de mandat), ce qui réduit l'intérêt spéculatif. ➡️ soupçon **Faible-Modéré**. Gain **Modéré (~55 %)** : profil rendement/défensif plus que croissance.

> 🔁 **Hors périmètre `buy` / non attribuable ce mois-ci** : une divulgation d'un **achat obligataire municipal** (bons d'assainissement du comté d'Allegheny, PA ; **250–500 k$**, transaction du **18 juin 2026**) apparaît dans les trackers mais **n'a pas pu être attribuée nommément** de façon fiable depuis cet environnement ; les **obligations municipales** présentent de toute façon un **risque de délit d'initié quasi nul** (pas d'exposition à un émetteur privé sensible à une décision législative). Côté **Mullin (R-OK)** : pas de nouvel achat d'action confirmé postérieur au **Parker-Hannifin (PH) du 12 juin** déjà listé. **Sheldon Whitehouse (D-RI)** : divulgations toujours dominées par des **ventes**.

---

## ⚠️ Avertissement méthodologique — à lire avant la lecture des colonnes

- **« Probabilité de gain »** : estimation **heuristique et subjective** (momentum constaté, secteur, catalyseurs connus). Ce **n'est pas** un conseil d'investissement ni une prévision fiable. Les performances passées ne préjugent pas des performances futures.
- **« Probabilité de délit d'initié »** : il s'agit d'un **score de soupçon / risque de conflit d'intérêts**, fondé uniquement sur des facteurs **publics** (chevauchement avec une commission sénatoriale compétente, timing de l'achat par rapport à un évènement connu). Ce **n'est pas** une accusation ni une constatation juridique.
  - Tous les trades listés sont **légaux** au regard du droit actuel (STOCK Act : déclaration sous 45 jours).
  - Dans le cas de Mullin notamment, son bureau affirme que les ordres sont passés **par un gérant tiers, sans son intervention**, et il **n'existe aucun élément** indiquant une connaissance d'information privilégiée.
  - Échelle utilisée : **Très faible / Faible / Modérée / Élevée**.

---

## 🆕 Nouveautés de ce rafraîchissement (2026-06-29)

Le site cible (CapitolTrades) et tous les agrégateurs (Quiver, Trendlyne, StockCircle, MarketBeat, Barchart…) restent en **HTTP 403** depuis cet environnement (politique réseau + anti-bot). Les éléments ci-dessous proviennent de divulgations STOCK Act relayées par la presse financière, reconstituées via recherche web. **Une nouvelle position d'achat** apparaît dans la fenêtre de divulgation **11–20 juin 2026**.

| Sénateur | Parti / État | Commission sensible | Société | Ticker | Date d'achat | Montant | Probabilité de gain | **Probabilité de délit d'initié** |
|----------|--------------|---------------------|---------|--------|--------------|---------|---------------------|-----------------------------------|
| **Markwayne Mullin** 🆕 | R — Oklahoma | **Armed Services** | Parker-Hannifin | PH | **2026-06-12** | 15–50 k$ | Élevée (~65 %) — momentum aéro-défense (≈ +46 % sur 1 an), proche de plus-hauts | **Modérée-Élevée** ⚠️ (équipementier aéro-défense, segment Aerospace Systems / dépenses militaires US — recoupe Armed Services) |

> 🔎 **Contexte Parker-Hannifin (PH)** : équipementier industriel dont le segment **Aerospace Systems** profite des marchés **commercial ET défense** (OEM + aftermarket ; revenus organiques du segment ≈ **+14 % a/a** au T3 fiscal 2026). En **mai 2026**, PH a signé le rachat de l'activité **Commercial & Defense Aerospace de CIRCOR pour ~2,55 Md$** (clôture attendue au 2ᵉ semestre). L'action a gagné ≈ **+46 % sur 12 mois** et évolue près de ses plus-hauts — d'où une probabilité de gain élevée mais un **risque de valorisation** (des initiés de PH ont vendu ~17 M$ d'actions, signal de prudence). Mullin siégeant à **Armed Services**, l'exposition à un fournisseur de la défense constitue un **chevauchement de conflit d'intérêts** (soupçon **Modéré-Élevé**), sans preuve de connaissance privilégiée ; ordre passé par un gérant tiers selon son bureau ; **légal**.

> 🔁 **Hors périmètre `buy` ce mois-ci** : les divulgations récentes de **Sheldon Whitehouse (D-RI)** restent dominées par des **ventes** (Home Depot/HD le 23/02, PepsiCo/PEP, Verizon/VZ, Mastercard/MA). **Bernie Moreno (R-OH)** : son achat marquant (Canadian Imperial Bank / **CM**, banque — alors qu'il siège à **Banking & Commerce**) date du **17/12/2025**, déjà antérieur ; pas de nouvel achat confirmé sur la fenêtre de juin. **John Kennedy (R-LA)** : gain mensuel estimé ~209 k$ par Quiver, sans nouvel achat sensible isolable ici.

---

## 🆕 Nouveautés du rafraîchissement précédent (2026-06-22)

Le site cible (CapitolTrades) et tous les agrégateurs (Quiver, Trendlyne, StockCircle, MarketBeat…) restent en **HTTP 403** depuis cet environnement. Les éléments ci-dessous proviennent de divulgations STOCK Act relayées par la presse financière (Quiver, Nasdaq, Newsweek, Finbold). Deux **achats récents** s'ajoutent, et la **datation du cluster IA est corrigée**.

| Sénateur | Parti / État | Commission sensible | Société | Ticker | Date d'achat | Montant | Probabilité de gain | **Probabilité de délit d'initié** |
|----------|--------------|---------------------|---------|--------|--------------|---------|---------------------|-----------------------------------|
| **Bill Hagerty** 🆕 | R — Tennessee | Banking ; Appropriations | Contour Venture Partners V LP (fonds de VC) | — (LP non coté) | 2026-01-23 | n.c. | n.c. — fonds de capital-risque illiquide (pas de cotation, horizon long) | **Très faible** (fonds aveugle / *blind pool*, pas un émetteur public) |
| **Markwayne Mullin** 🆕 | R — Oklahoma | **Armed Services** | Nvidia | NVDA | 2025-12-29 → 2026-02-04 | jusqu'à ~50 k$ | Modérée-Élevée (~60 %) — méga-cap IA | **Modérée-Élevée** ⚠️ (puces IA sous régime de licences d'export vers la Chine) |
| Markwayne Mullin | R — Oklahoma | **HELP** (Santé) | UnitedHealth Group | UNH | 2026-02-25 (divulg. ~10–12/03) | **50–100 k$** | Modérée (~55 %) — achat sur repli | **Modérée-Élevée** ⚠️ (assureur santé / commission HELP) |

> ✅ **Correction de millésime — cluster IA = 2025, pas 2026.** Le rapport CREW « *Four senators bought AI stock while Senate considered deregulating AI* » a été **publié le 15 juillet 2025** et porte sur des achats de **mai 2025** : Jerry Moran aurait acheté 4 004–60 000 $ de Google (GOOGL) **le jour même de l'audition IA de la commission Commerce du 8 mai 2025** (avec témoignage de Sam Altman) ; Capito, Fetterman et Boozman (+ NVDA/META/AMZN) sur la même période. **Ce cluster ne relève donc pas des achats 2026** et est déplacé en section « contexte 2025 » ci-dessous. Le soupçon de conflit d'intérêts (timing audition + commission) reste **Élevé pour Moran**, mais l'opération reste **légale** et sans preuve de connaissance privilégiée.

> 🔎 **Contexte Hagerty** : la même divulgation comporte surtout des **ventes** (Equitrans/ETRN, AutoZone/AZO, Intuit/INTU) — hors périmètre `buy` ; seul l'engagement dans le fonds Contour Venture Partners V est un **achat**. Quiver estime un gain ~812 k$ sur le mois pour son portefeuille.

---

## Tableau de suivi — positions d'achat récentes (cumul)

| Sénateur | Parti / État | Commission sensible | Société | Ticker | Date d'achat | Montant | Probabilité de gain | **Probabilité de délit d'initié** |
|----------|--------------|---------------------|---------|--------|--------------|---------|---------------------|-----------------------------------|
| Alan Armstrong 🆕 | R — Oklahoma | Énergie (ex-PDG Williams) ; interim | Apple | AAPL | **~2026-03-25** | ≥ 250 k$ | Modérée-Élevée (~60 %) | **Très faible-Faible** (direct indexing, méga-cap, J+1 prise de fonction) |
| Alan Armstrong 🆕 | R — Oklahoma | Énergie ; interim | Nvidia | NVDA | **~2026-03-25** | ≥ 50 k$ | Élevée (~65 %) — momentum IA | **Faible** (basket algorithmique) |
| Alan Armstrong 🆕 | R — Oklahoma | Énergie ; interim | Alphabet | GOOGL | **~2026-03-25** | ≥ 50 k$ | Modérée-Élevée (~60 %) | **Très faible-Faible** (basket) |
| Alan Armstrong 🆕 | R — Oklahoma | Énergie ; interim | Berkshire Hathaway | BRK | **~2026-03-25** | ≥ 50 k$ | Modérée (~55 %) | **Très faible** (holding diversifié) |
| John Hickenlooper 🆕 | D — Colorado | Commerce & Transports | Uber Technologies | UBER | **2026-01-14** | 100–250 k$ | Modérée (~55 %) — rebond robotaxi, −10 % YTD | **Modérée-Élevée** ⚠️ (transport / Commerce ; pas de blind trust qualifié) |
| John Hickenlooper 🆕 | D — Colorado | Énergie ; Commerce | Eaton Corporation | ETN | **2026-01-14** | 50–100 k$ | Élevée (~65 %) — data-center power, +27 %/an | **Modérée** ⚠️ (réseau/énergie ; gérant tiers) |
| Gary Peters 🆕 | D — Michigan | Commerce (télécom/FCC) | AT&T | T | **2026-06-29** | 1–15 k$ | Modérée (~55 %) — télécom défensif, dividende | **Faible-Modérée** ⚠️ (télécom / Commerce ; montant faible, réformateur, fin de mandat) |
| Markwayne Mullin | R — Oklahoma | Armed Services | Parker-Hannifin | PH | **2026-06-12** | 15–50 k$ | Élevée (~65 %) — aéro-défense, ≈ +46 %/an | **Modérée-Élevée** ⚠️ (équipementier défense) |
| Markwayne Mullin | R — Oklahoma | Armed Services | Chevron | CVX | 2025-12-29 | 15–50 k$ | Élevée (~70 %) — déjà +10 % | **Élevée** ⚠️ |
| Markwayne Mullin | R — Oklahoma | Armed Services | RTX (Raytheon) | RTX | 2025-12-29 | 15–50 k$ | Élevée (~70 %) — déjà +9,5 % | **Élevée** ⚠️ |
| Markwayne Mullin | R — Oklahoma | Armed Services | ConocoPhillips | COP | 2025-12 (fin) | 15–50 k$ | Modérée-Élevée (~65 %) | **Élevée** ⚠️ |
| Markwayne Mullin | R — Oklahoma | Armed Services | Nvidia | NVDA | 2025-12-29 → 2026-02-04 | jusqu'à ~50 k$ | Modérée-Élevée (~60 %) | **Modérée-Élevée** ⚠️ (licences export puces IA) |
| Markwayne Mullin | R — Oklahoma | Armed Services | Amkor Technology | AMKR | 2026-01-05 / 02-04 | jusqu'à 150 k$ (×3) | Modérée (~55 %) — déjà +60 % | **Modérée** |
| Markwayne Mullin | R — Oklahoma | Armed Services | Carpenter Technology | CRS | 2026-02-04 | 15–50 k$ | Modérée (~55 %) — déjà +22 % | **Modérée** (alliages défense) |
| Markwayne Mullin | R — Oklahoma | Armed Services | VSE Corp | VSEC | 2026-01-05 | 15–50 k$ | Modérée (~55 %) | **Modérée-Élevée** (contrats USAF) |
| Markwayne Mullin | R — Oklahoma | Armed Services | McKesson | MCK | 2026-01-05 | 15–50 k$ | Modérée (~55 %) | **Faible-Modérée** |
| Markwayne Mullin | R — Oklahoma | Armed Services | Monolithic Power | MPWR | 2026-01-05 | 15–50 k$ | Modérée (~55 %) | **Faible-Modérée** (semi/militaire) |
| Markwayne Mullin | R — Oklahoma | Armed Services | Adobe | ADBE | 2026-01-05 | 15–50 k$ | Modérée (~50 %) | **Faible** |
| Markwayne Mullin | R — Oklahoma | Armed Services | Citigroup | C | 2026-01-05 | 15–50 k$ | Modérée (~50 %) | **Faible** |
| Markwayne Mullin | R — Oklahoma | Armed Services | APi Group | APG | 2026-01-05 | 15–50 k$ | Modérée (~50 %) | **Faible** |
| Markwayne Mullin | R — Oklahoma | Armed Services | FirstCash Holdings | FCFS | 2026-01-05 | 15–50 k$ | Modérée (~50 %) | **Faible** |
| Markwayne Mullin | R — Oklahoma | Armed Services | Stride | LRN | 2026-01-05 | 15–50 k$ | Modérée (~50 %) | **Faible** |
| Bill Hagerty | R — Tennessee | Banking ; Appropriations | Contour Venture Partners V LP | — (LP non coté) | 2026-01-23 | n.c. | n.c. (VC illiquide) | **Très faible** (fonds aveugle, pas d'émetteur public) |
| John Hickenlooper | D — Colorado | Commerce | Palo Alto Networks | PANW | 2026 (T1) | 1–15 k$ | Modérée (~55 %) | **Modérée-Élevée** (procurement) |
| Tommy Tuberville | R — Alabama | — (ETF diversifiés) | SPDR Consumer Staples | XLP | 2025-12-17 | 15–50 k$ | Modérée (~50 %) | **Très faible** |
| Tommy Tuberville | R — Alabama | — (ETF diversifiés) | SPDR Utilities | XLU | 2025-12-17 | 15–50 k$ | Modérée (~50 %) | **Très faible** |
| Tommy Tuberville | R — Alabama | — (ETF diversifiés) | SPDR Health Care | XLV | 2025-12-17 | 15–50 k$ | Modérée (~50 %) | **Très faible** |

---

## Analyse détaillée par sénateur

### 🆕 🟠 Alan Armstrong (R — Oklahoma) — nouveau sénateur, ex-PDG de Williams (WMB) *(nouveauté 2026-07-27)*

- **Nomination** : le **24 mars 2026**, le gouverneur **Kevin Stitt** nomme Armstrong (Tulsa, ex-PDG 2011-2025 du groupe gazier **Williams / WMB**) pour occuper — jusqu'à fin 2026 — le siège du Sénat laissé vacant par **Markwayne Mullin**, parti diriger le **Department of Homeland Security**. Profil énergie affiché (« *permitting reform* »).
- **Activité `buy`** : dans les jours suivant sa prestation de serment (fin mars 2026), **~703 transactions** pour **≥ 25 M$**, dont **≥ 7,66 M$ d'achats** et **≥ 17,37 M$ de ventes**. Les **achats** significatifs sont une **rotation « Magnificent Seven »** : **Apple (AAPL, ≥ 250 k$)**, puis **Alphabet (GOOGL)**, **Nvidia (NVDA)** et **Berkshire Hathaway (BRK)** (≥ 50 k$ chacun). Une **note du dépôt** attribue ces mouvements à une stratégie de ***direct indexing*** mise en œuvre par un **conseiller tiers**.
- **Facteurs atténuants (délit d'initié)** : (1) panier **large, diversifié, algorithmique** (direct indexing) plutôt que paris ciblés ; (2) méga-caps peu sensibles à une information ponctuelle ; (3) ordres passés **le lendemain de son entrée en fonction**, avant tout accès plausible à une information de commission ; (4) surtout, l'essentiel des flux est une **vente** — la **liquidation de 5–25 M$ de son ancien titre Williams (WMB)** + 250–500 k$ d'options, soit un **désengagement** de l'émetteur où il aurait le plus d'information privilégiée.
- **Facteur aggravant (transparence)** : ~700 déclarations **hors délai de plus de deux mois** → **violation caractérisée du STOCK Act**, la **plus importante de 2026** — et symboliquement mal placée, révélée la **semaine du vote de la Chambre** sur l'interdiction du trading (H.R. 7008).
- ➡️ **Probabilité de délit d'initié : Très faible-Faible** ⚠️ (le risque réel porte sur la *transparence*, **Élevé**, pas sur l'*insider*). **Probabilité de gain : Modérée-Élevée** — méga-caps porteuses (NVDA momentum IA le plus fort du lot), mais achats déjà anciens (fin mars). **Légal** sur le fond ; **infraction de déclaration** avérée.

### 🔴 Markwayne Mullin (R — Oklahoma) — membre du *Senate Armed Services Committee*

Le profil le plus actif et le plus sensible du tableau. Mullin a déclaré avoir acheté/vendu **~24 M$ d'actions depuis 2023**. Son bureau précise que les ordres sont passés **par un gérant tiers sans son intervention**.

**🚩 Le signal le plus marquant — Chevron / RTX / ConocoPhillips avant l'opération au Venezuela**
- Achats de **Chevron (CVX)** et **RTX (RTX)** le **29 décembre 2025**, soit **~5 jours avant** la frappe américaine et la capture de Nicolás Maduro (**3 janvier 2026**).
- Depuis : **CVX ~ +10,1 %**, **RTX ~ +9,5 %**, et **ConocoPhillips (COP)** en hausse plus forte encore.
- Mullin siège à la commission des forces armées, qui supervise la politique militaire et la sécurité nationale → **chevauchement direct** entre l'information potentiellement accessible et les positions prises.
- ➡️ **Probabilité de délit d'initié : Élevée** sur le plan du *soupçon public* (timing + commission). **Mais** : aucune preuve de connaissance privilégiée, trades passés par un gérant tiers selon le bureau, et **légaux**. À considérer comme un **drapeau de conflit d'intérêts**, pas comme une culpabilité établie.

**Vague d'achats de janvier–février 2026** (10 titres le 5 janvier, renforcements le 4 février) : rotation vers des **plus petites capitalisations**, dont plusieurs liées à la **défense** :
- **VSE Corp (VSEC)** : services aéronautiques, contrat USAF de 565 M$ → conflit potentiel avec Armed Services.
- **Carpenter Technology (CRS)** : alliages utilisés par Raytheon / Lockheed → **+22 %**, plus haut historique.
- **Amkor (AMKR)** : packaging de semi-conducteurs, chaîne d'approvisionnement militaire → **+60 %**.
- Autres : Adobe, Citigroup, APi Group, FirstCash, Stride, McKesson, Monolithic Power → conflit faible.

### 🆕 🔴 Markwayne Mullin (R — OK) — achat Parker-Hannifin (PH) — *Senate Armed Services* *(nouveauté 2026-06-29)*

- Achat de **Parker-Hannifin (PH)** le **12 juin 2026** pour **15 001–50 000 $**, divulgué dans la fenêtre 11–20 juin 2026.
- PH est un **équipementier industriel à forte composante aéro-défense** : son segment **Aerospace Systems** sert à la fois les marchés commercial et **défense** (OEM + pièces de rechange), et le groupe a annoncé en **mai 2026** le rachat de l'activité **Commercial & Defense Aerospace de CIRCOR (~2,55 Md$)**. Forte dynamique : action ≈ **+46 % sur 12 mois**, près de ses plus-hauts.
- Mullin siège à la commission **Armed Services**, qui supervise la politique de défense → **chevauchement** avec un fournisseur de la défense en pleine montée des budgets militaires.
- ➡️ **Probabilité de délit d'initié : Modérée-Élevée** ⚠️ (recoupement commission défense / fournisseur). **Probabilité de gain : Élevée (~65 %)** — momentum aéro-défense soutenu, mais **risque de valorisation** (titre proche de plus-hauts ; ventes d'initiés PH ~17 M$ relevées comme signal de prudence). Ordre passé par un gérant tiers selon son bureau ; **légal**.

### 🆕 🔴 Markwayne Mullin (R — OK) — achat UnitedHealth (UNH) — *Senate HELP Committee*

- Achat de **50 001–100 000 $** d'actions **UnitedHealth Group (UNH)** le **25 février 2026** (divulgué vers le 10–12 mars 2026), **en plus** d'un précédent achat d'UNH de 15–50 k$ en **septembre 2025**.
- Mullin siège à la commission **HELP** (*Health, Education, Labor and Pensions*) et à des sous-commissions santé → **chevauchement direct** avec un grand assureur santé. Les analystes de gouvernance (Simply Wall St, Sahm Capital) ont qualifié l'opération de **risque de gouvernance / conflit d'intérêts**.
- Contexte : Mullin a été **désigné par Trump pour diriger le DHS** et s'est engagé à **céder ses participations s'il est confirmé**.
- ➡️ **Probabilité de délit d'initié : Modérée-Élevée** (chevauchement commission santé + assureur). **Probabilité de gain : Modérée (~55 %)** — achat opéré sur un repli marqué d'UNH ; rebond non garanti. Trades passés par un gérant tiers selon son bureau ; **légal**.

### 🟣 Cluster « titres IA » — Moran, Capito, Fetterman, Boozman (rapport CREW) — *millésime 2025*

> **Mise à jour 2026-06-22 :** ce cluster a été **sorti des « achats récents 2026 »**. Le rapport CREW a été **publié le 15 juillet 2025** et l'audition IA de la commission Commerce (témoignage de Sam Altman) a eu lieu le **8 mai 2025**. Les achats sont donc de **mai 2025**. Conservé ici comme **précédent de référence** pour le motif de conflit d'intérêts.

- Le rapport **CREW** (*Citizens for Responsibility and Ethics in Washington*) « *Four senators bought AI stock while Senate considered deregulating AI* » identifie quatre sénateurs ayant acheté (eux-mêmes, conjoint ou enfant à charge) des titres **Google/Alphabet** en **mai 2025**, pendant que le Sénat examinait un **moratoire de 10 ans interdisant aux États de réguler l'IA** (lobbying d'Amazon, Google, Microsoft, Meta).
- **Jerry Moran (R — KS)**, membre de la commission **Commerce**, aurait acheté **4 004–60 000 $** d'actions Google (avec son épouse) **le jour même de l'audition de la commission Commerce sur l'IA (8 mai 2025)** → **timing le plus sensible du lot**.
- **John Boozman (R — AR)** a aussi déclaré **Nvidia (NVDA), Meta (META) et Amazon (AMZN)**.
- **Shelley Moore Capito (R — WV)** et **John Fetterman (D — PA)** : achats de Google sur la même période.
- ➡️ **Probabilité de délit d'initié : Élevée pour Moran** (timing audition + commission compétente), **Modérée-Élevée pour Capito/Fetterman/Boozman**. **Probabilité de gain : Modérée-Élevée (~60 %)** — méga-cap technologiques porteuses, mais risque antitrust (procédure DOJ sur le monopole de recherche de Google). **Légal**.

### 🆕 🔴 Markwayne Mullin (R — OK) — achat Nvidia (NVDA) — *Senate Armed Services*

- Achat de **Nvidia (NVDA)** dans la fenêtre **29 décembre 2025 → 4 février 2026** (jusqu'à ~50 k$), au sein d'une vague d'achats plus large.
- NVDA fait l'objet de **conditions de licences d'exportation** de puces IA vers la Chine — sujet de sécurité nationale qui recoupe la commission **Armed Services** de Mullin.
- Nvidia figure parmi les **~305 009 $** de titres « à conflit potentiel » (avec RTX, L3Harris, Alphabet, Microsoft, Amazon…) que Mullin s'est **engagé à céder** dans le cadre de son accord d'éthique pour la confirmation au **DHS**.
- ➡️ **Probabilité de délit d'initié : Modérée-Élevée** ⚠️ (chevauchement export de puces / sécurité nationale). **Probabilité de gain : Modérée-Élevée (~60 %)**. Trades passés par un gérant tiers selon son bureau ; **légal**.

### 🆕 🔴 Bill Hagerty (R — Tennessee) — *Senate Banking & Appropriations*

- La dernière divulgation STOCK Act de Hagerty est dominée par des **ventes** : **Equitrans Midstream (ETRN)**, **AutoZone (AZO)**, **Intuit (INTU)** → hors périmètre `buy`.
- Côté **achat** : une participation dans **Contour Venture Partners V LP** (fonds de capital-risque *early-stage*), déclarée le **23 janvier 2026**. Il s'agit d'un **fonds aveugle non coté** (engagement de capital, déploiement progressif sur plusieurs années).
- ➡️ **Probabilité de délit d'initié : Très faible** — un fonds de VC diversifié et illiquide n'offre aucune exposition à un émetteur public unique sensible à une décision législative. **Probabilité de gain : non quantifiable** (horizon 7–10 ans, illiquidité, dispersion). Quiver estime ~**812 k$** de gain mensuel sur l'ensemble de son portefeuille (titres cotés détenus par ailleurs).

### 🆕 🔵 Gary Peters (D — Michigan) — achat AT&T (T) — *Senate Commerce Committee* *(nouveauté 2026-07-06)*

- Achat de **1 001–15 000 $** d'actions **AT&T (T)** le **29 juin 2026**, divulgué vers le **2 juillet 2026** (relais MarketBeat / Benzinga / Markets Daily du 5 juillet 2026).
- Peters siège à la commission **Commerce, Science & Transportation** (qui supervise les **télécommunications et la FCC**), ainsi qu'à **Armed Services**, **Appropriations** et comme *Ranking Member* de **Homeland Security & Governmental Affairs** → chevauchement de juridiction avec un opérateur télécom.
- **Éléments d'atténuation du soupçon** : (1) montant **faible** (tranche 1–15 k$) ; (2) AT&T est une **méga-cap défensive à dividende élevé**, dont le cours répond surtout aux taux et aux résultats trimestriels — peu susceptible de bouger sur une information privilégiée ponctuelle ; (3) Peters est **co-auteur de plusieurs textes bipartites anti-conflits d'intérêts** (contractants fédéraux) ; (4) il a annoncé **ne pas se représenter en 2026**, ce qui réduit l'intérêt spéculatif de fin de mandat.
- ➡️ **Probabilité de délit d'initié : Faible-Modérée** ⚠️ (juridiction télécom/Commerce, atténuée par les facteurs ci-dessus). **Probabilité de gain : Modérée (~55 %)** — profil rendement/défensif (dividende, fibre, désendettement) plus que forte croissance ; upside limité. **Légal** (STOCK Act, déclaration dans les délais).

### 🔵 John Hickenlooper (D — Colorado) — membre du *Senate Commerce, Science & Transportation Committee*

- Investissement dans **Palo Alto Networks (PANW)**, société de cybersécurité, déclaré **quelques mois avant** l'expansion d'un programme fédéral d'achats élargissant l'accès des agences aux produits de la firme.
- **🆕 (2026-07-13) Achats du 14 janvier 2026** : **Uber (UBER, 100–250 k$)** et **Eaton (ETN, 50–100 k$)**. Uber (plateforme de **transport**) relève directement de sa commission **Commerce/Transports** → conflit **Modéré-Élevé** ; Eaton (énergie/réseau/data-center) recoupe **Energy & Natural Resources** → conflit **Modéré**. La presse (Colorado Newsline, NOTUS, Hoodline) souligne l'**absence de *blind trust* qualifié**, qui affaiblit l'argument « gérant tiers ». Gain estimé : **Modéré (~55 %)** pour UBER (rebond robotaxi mais −10 % YTD), **Élevé (~65 %)** pour ETN (momentum data-center, +27 % sur 1 an).
- **🚨 (2026-07-13) Violation du STOCK Act** : divulgations tardives (~1 an) de ventes **Liberty Broadband (LBRDK, 500 k–1 M$, conjoint)** et **Palantir (PLTR, ~3 312 $, enfant à charge)** — voir section « Nouveautés 2026-07-13 (b) ». **Violation de transparence** avérée, sans preuve de délit d'initié ; PLTR (contractant défense/ICE) rend le retard sensible.
- Chevauchement avec la commission Commerce → **risque de conflit d'intérêts Modéré-Élevé** signalé par les organismes de surveillance.

### 🔴 Tommy Tuberville (R — Alabama)

- Le **17 décembre 2025**, achat de trois **ETF sectoriels diversifiés** SPDR : **XLP** (consommation de base), **XLU** (utilities), **XLV** (santé), 15–50 k$ chacun ; parallèlement vente d'Apple (AAPL) et d'Alphabet (GOOGL).
- Des ETF larges ne donnent pas d'exposition à un émetteur unique → **risque de délit d'initié Très faible**. Rotation défensive classique.

### ⚪ Mentions (contexte, hors achats récents)

- **Ashley Moody (R — FL, commission Santé)** : ~2,2 M$ sur 57 transactions (dont **Eli Lilly, LLY**) en début de mandat (fév.–avr. 2025), via un *family partnership*. **Plus aucune transaction depuis avril 2025** ; co-sponsor d'un projet de loi d'interdiction du trading au Congrès → exclue des achats « récents ».
- **Sheldon Whitehouse (D — RI)** : surtout des **ventes** en 2026 (Nvidia/NVDA le 8 mai, Home Depot, PepsiCo, Verizon, Mastercard) → hors périmètre `buy`.

---

## Contexte réglementaire

- Une **analyse de CNN (février 2026)** a identifié au moins **10 sénateurs** ayant tradé des titres dans des secteurs supervisés par leurs commissions : R — Hagerty, Kennedy, Moody, Moran, Moreno, Mullin, Tuberville ; D — Hickenlooper, Peters, Whitehouse.
- Un **projet de loi bipartisan (Ossoff)** d'interdiction du trading d'actions au Congrès a passé une commission clé du Sénat — réforme en cours de discussion en 2026.
- **17 juin 2026** : la commission des forces armées du Sénat a approuvé un texte interdisant aux **sous-traitants de la défense** de racheter leurs propres actions sans accord du Pentagone (contexte de défiance accru autour des conflits d'intérêts).
- **🆕 22 juillet 2026** : la **Chambre des représentants** a adopté (**232-198**, bipartite) le **Stop Insider Trading Act** (**H.R. 7008**, porté par **Bryan Steil (R-WI)**) : interdiction pour les membres, conjoints et enfants à charge d'**acheter des actions cotées**, **préavis de 7 jours** avant toute vente, **pénalité 2 000 $ ou 10 %** et **confiscation des plus-values**. **Sort incertain au Sénat** (règle des 60 voix ; dispositions annexes). Fortune relève que le texte **exempterait les ~3 600 trades du T1 de D. Trump**. Si le Sénat suivait, ce suivi « achats de sénateurs » deviendrait structurellement caduc pour les titres cotés.
- **🆕 ~juillet 2026** : le nouveau sénateur **Alan Armstrong (R-OK)**, successeur de Mullin, a **enfreint le STOCK Act** en divulguant **~700 transactions** de fin mars 2026 avec **plus de deux mois de retard** — la plus grosse divulgation tardive de l'année (voir fiche dédiée).
- **Bill Hagerty (R — TN)** : détail désormais partiellement récupéré (voir fiche dédiée) — achat du fonds **Contour Venture Partners V LP** (23/01/2026) et ventes ETRN/AZO/INTU ; gain mensuel estimé ~812 k$ par Quiver.

---

## Sources

- [CapitolTrades — trades (buy)](https://www.capitoltrades.com/trades?txType=buy) *(cible — inaccessible depuis cet environnement, 403)*
- [CNN — Senators' stock trades overlapped with committee work](https://www.cnn.com/2026/02/09/politics/senator-stock-trading-congress)
- [Snopes — Mullin bought Chevron, Raytheon before Maduro capture](https://www.snopes.com/fact-check/mullin-chevron-stock-us-venezuela/)
- [Quiver Quantitative — Mullin bought Chevron and Raytheon before Venezuela operation](https://www.quiverquant.com/news/Sen.+Markwayne+Mullin+Bought+Chevron+and+Raytheon+Stock+Days+Before+U.S.+Venezuela+Operation)
- [Yahoo / AOL — Mullin buys 10 stocks (Jan 2026)](https://www.aol.com/articles/trump-ally-mullin-buys-10-153127702.html)
- [Benzinga — Mullin's 2026 shopping list (Carpenter Technology)](https://www.benzinga.com/news/politics/26/03/51056217/trump-ally-mullin-goes-stock-shopping-again-heres-his-latest-buys-including-potential-conflict-of-interest)
- [Benzinga — Mullin's Amkor trade surges 60 %](https://www.benzinga.com/news/politics/26/05/52272511/markwayne-mullins-amkor-stock-trade-is-on-a-roll-says-nancy-pelosi-tracker-as-stock-surges-60)
- [GuruFocus — Markwayne Mullin trades](https://www.gurufocus.com/politician/260/markwayne-mullin)
- [MarketBeat — Tommy Tuberville trades](https://marketbeat.com/congress-stock-trades/profiles/tommy-tuberville)
- [Senate Stock Watcher](https://senatestockwatcher.com/)
- [Markets Daily — Mullin achète UnitedHealth (UNH)](https://www.themarketsdaily.com/2026/03/12/sen-markwayne-mullin-purchases-shares-of-unitedhealth-group-incorporated-nyseunh.html)
- [Sahm Capital — Mullin's UnitedHealth trade puts governance risk in sharper focus](https://www.sahmcapital.com/news/content/senator-mullins-unitedhealth-trade-puts-governance-risk-in-sharper-focus-2026-03-12)
- [CREW — Four senators bought AI stock while Senate considered deregulating AI](https://www.citizensforethics.org/reports-investigations/crew-investigations/four-senators-bought-ai-stock-while-senate-considered-deregulating-ai/)
- [CNBC — Defense contractors barred from stock buybacks in Senate panel bill (17/06/2026)](https://www.cnbc.com/amp/2026/06/17/defense-contractors-stock-buybacks-senate-warren-trump.html)
- [Trendlyne — US politician trade tracker (11–20 juin 2026)](https://us.trendlyne.com/us/politicians/recent-trades/)
- [Finbold — Inside Markwayne Mullin's biggest stock trades of 2026 (Nvidia)](https://finbold.com/the-top-2026-stock-trades-of-markwayne-mullin/)
- [Newsweek — Mullin's stock portfolio sparks conflict-of-interest concerns (DHS divestment)](https://www.newsweek.com/markwayne-mullin-dhs-stock-trading-defense-11644758)
- [NOTUS — Mullin pledges to sell stock holdings if confirmed (DHS)](https://www.notus.org/trump-white-house/markwayne-mullin-dhs-stock-holdings-sell)
- [Quiver / Nasdaq — Senator Bill Hagerty just disclosed new stock trades](https://www.nasdaq.com/articles/congress-trade-senator-bill-hagerty-just-disclosed-new-stock-trades)
- [CapitolTrades — Hagerty bought Contour Venture Partners V LP (2026-01-23)](https://www.capitoltrades.com/trades/10000064641)
- [Nasdaq / Quiver — U.S. Senator Markwayne Mullin Just Reported a Purchase of $PH Stock (12/06/2026)](https://www.nasdaq.com/articles/us-senator-markwayne-mullin-just-reported-purchase-ph-stock)
- [Barchart — U.S. Senator Markwayne Mullin Just Reported a Purchase of $PH Stock](https://www.barchart.com/story/news/27363400/us-senator-markwayne-mullin-just-reported-a-purchase-of-ph-stock)
- [Zacks / TradingView — PH Gains From Strength in Aerospace Systems Unit (segment défense/commercial, CIRCOR)](https://www.tradingview.com/news/zacks:21a21eb2c094b:0-ph-gains-from-strength-in-aerospace-systems-unit-can-it-sustain/)
- [Stock Analysis — Parker-Hannifin (PH) performance (~+46 % sur 12 mois)](https://stockanalysis.com/stocks/ph/)
- [CapitolTrades — Bernie Moreno first stock buy (CIBC / CM, Banking & Commerce, 17/12/2025)](https://www.capitoltrades.com/buzz/bernie-moreno-makes-first-stock-buy-in-congress-with-eye-on-banking-and-commerce-2026-01-13)
- [MarketBeat — Sen. Gary C. Peters Buys Shares of AT&T Inc. (NYSE:T) (05/07/2026)](https://www.marketbeat.com/instant-alerts/sen-gary-c-peters-buys-shares-of-att-inc-nyset-2026-07-05/)
- [Benzinga — This Senator Just Bought Up To $15K In AT&T Stock (Gary Peters, 29/06/2026)](https://www.benzinga.com/government/26/07/60266082/senator-just-bought-15k-t-stock)
- [Markets Daily — Sen. Gary C. Peters Buys Shares of AT&T Inc. (NYSE:T)](https://www.themarketsdaily.com/2026/07/05/sen-gary-c-peters-buys-shares-of-att-inc-nyset.html)
- [Quiver — Congress Trade: Senator Gary C. Peters Just Disclosed New Stock Trades](https://www.quiverquant.com/news/Congress+Trade:+Senator+Gary+C.+Peters+Just+Disclosed+New+Stock+Trades)
- [U.S. Senator Gary Peters — Committee Assignments (Commerce ; Armed Services ; HSGAC RM ; Appropriations)](https://www.peters.senate.gov/about/committee-assignments)
- [NOTUS — Two U.S. Senators Violated the STOCK Act With Late Disclosures (Hickenlooper, Rounds ; Palantir / Liberty Broadband / Aeronics)](https://www.notus.org/congress/senators-hickenlooper-rounds-stock-trading-act-violations-palantir-transparency-law)
- [NOTUS — John Hickenlooper Just Bought a Ton of Uber Stock. He Also Sits on the Senate's Transportation Committee](https://www.notus.org/money/john-hickenlooper-uber-stock-purchase-transportation-senate-ethics)
- [Colorado Newsline — Hickenlooper investments raise questions amid push to ban congressional stock trading (Uber, Eaton, blind trust)](https://coloradonewsline.com/2026/03/26/hickenlooper-stock-trade-questions/)
- [Hoodline — Hickenlooper's Uber Buy Puts Colorado Senator On Ethics Defensive](https://hoodline.com/2026/03/hickenlooper-s-uber-buy-puts-colorado-senator-on-ethics-defensive/)
- [Kavout — Why is Senator Hickenlooper Investing in Uber (UBER 100–250 k$, ETN 50–100 k$, 14/01/2026)](https://www.kavout.com/market-lens/why-is-senator-hickenlooper-investing-in-uber)
- [Benzinga — This Senator Just Sold Up To $165K In Apple Stock (Whitehouse, AAPL, divulg. 08/07/2026)](https://www.benzinga.com/government/26/07/60362118/senator-just-sold-165k-apple-stock)
- [Daily Political — Sen. Sheldon Whitehouse Sells Off Crown Castle Inc. (CCI) Stock (10/07/2026)](https://www.dailypolitical.com/2026/07/10/sen-sheldon-whitehouse-sells-off-crown-castle-inc-nysecci-stock.html)
- [CNBC — First known congressional SpaceX stock buys surface after record IPO (Meuser & Cisneros — Chambre, hors périmètre)](https://www.cnbc.com/2026/07/03/spacex-stock-congress-meuser-cisneros-ipo-disclosure.html)
- [Yahoo Finance / Zacks — Eaton (ETN) surged on accelerated demand for data center power solutions (+27 %/an)](https://finance.yahoo.com/markets/stocks/articles/eaton-etn-surged-accelerated-demand-135055504.html)
- [TIKR — Uber stock rose 6% in a single day; where the stock could go in 2026 (cibles ~100–115 $)](https://www.tikr.com/blog/uber-stock-rose-6-in-a-single-day-heres-where-the-stock-could-go-in-2026)

- [NOTUS — Sen. Alan Armstrong Violated STOCK Act With 700 Tardy Stock Disclosures](https://www.notus.org/congress/sen-alan-armstrong-violated-stock-act-with-700-tardy-stock-disclosures)
- [Benzinga — New Senator Makes 700+ Stock Trades, Ditches Old Oil Company for Magnificent Seven (Armstrong : AAPL, GOOGL, NVDA, BRK ; vente WMB)](https://www.benzinga.com/news/politics/26/07/60657916/new-senator-makes-700-stock-trades-ditches-old-oil-company-for-magnificent-seven)
- [Benzinga — This Senator Just Sold Up To $28.36M In Williams Companies Stock (Armstrong / WMB)](https://www.benzinga.com/government/26/07/60610986/senator-just-sold-28-36m-williams-companies-stock)
- [moomoo — Congress Trade: Senator Alan Armstrong Just Disclosed New Stock Trades](https://www.moomoo.com/news/post/73386138/congress-trade-senator-alan-armstrong-just-disclosed-new-stock-trades)
- [NonDoc — Seeking 'permitting reform,' Stitt appoints former Williams CEO Alan Armstrong to U.S. Senate (24/03/2026)](https://nondoc.com/2026/03/24/seeking-permitting-reform-stitt-appoints-former-williams-ceo-alan-armstrong-to-u-s-senate/)
- [Steil.house.gov — House Passes Steil's Congressional Stock Trading Ban (H.R. 7008, 22/07/2026)](https://steil.house.gov/media/press-releases/house-passes-steil-s-congressional-stock-trading-ban)
- [NOTUS — House Lawmakers Pass Bill Restricting Congressional Stock Trading](https://www.notus.org/money/congressional-stock-ban-bill-passes-house)
- [Washington Post — House passes stock-trading restrictions for lawmakers, but not a full ban (22/07/2026)](https://www.washingtonpost.com/politics/2026/07/22/house-passes-stock-trading-restrictions-lawmakers-not-full-ban/)
- [Fortune — The Republican-controlled House just passed a stock trading bill — that exempts Trump's 3,600 Q1 trades](https://fortune.com/2026/07/23/house-stock-trading-ban-trump-exemption-vote/)
- [CNN — House passes bill to restrict lawmaker stock trading (22/07/2026)](https://www.cnn.com/2026/07/22/politics/stock-trading-restriction-congress)
- [Congress.gov — H.R.7008, 119th Congress: Stop Insider Trading Act](https://www.congress.gov/bill/119th-congress/house-bill/7008)

- [Oklahoma Watch — Sen. Alan Armstrong Violated STOCK Act With 700 Tardy Stock Disclosures (chiffrage 3,24 M$–16,05 M$, 27/07/2026)](https://oklahomawatch.org/2026/07/27/sen-alan-armstrong-violated-stock-act-with-700-tardy-stock-disclosures/)
- [KGOU — Sen. Alan Armstrong violated STOCK Act with 700 tardy stock disclosures (27/07/2026)](https://www.kgou.org/politics-and-government/2026-07-27/sen-alan-armstrong-violated-stock-act-with-700-tardy-stock-disclosures)
- [GovTrack — H.R. 7008: Stop Insider Trading Act, House Vote #280 (22/07/2026) puis transmission au Sénat](https://www.govtrack.us/congress/bills/119/hr7008)
- [Trendlyne — US politician trade tracker (21 juil.–1er août 2026 ; divulgations tardives Chambre : Crenshaw, Laurel Lee)](https://us.trendlyne.com/us/politicians/recent-trades/)
- [24/7 Wall St. — « New Nancy Pelosi? » Senator Ashley Moody up 310% (aucune transaction depuis avril 2025)](https://247wallst.com/investing/2026/07/01/new-nancy-pelosi-senator-ashley-moody-up-310-in-one-year-with-these-stocks/)

> *Données reconstituées le 2026-08-03 (rafraîchissement #7 — fenêtre calme côté `buy` sénateurs, affinage Armstrong + suivi H.R. 7008 au Sénat) ; base précédente le 2026-07-27 (rafraîchissement #6) à partir de divulgations publiques STOCK Act relayées par la presse. Ne constitue pas un conseil en investissement ni une imputation de fait délictueux. Les « violations du STOCK Act » mentionnées (Armstrong, Hickenlooper, Rounds) désignent des **retards de déclaration** (transparence), non des délits d'initié établis. La colonne « probabilité de délit d'initié » est un **score de soupçon / conflit d'intérêts** fondé sur des facteurs publics (chevauchement de commission, timing, nature du titre), pas une constatation juridique.*
