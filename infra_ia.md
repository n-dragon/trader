# Infrastructure de l'Intelligence Artificielle — Cartographie & thèse d'investissement

> **Objet.** Identifier *tous* les acteurs et composants de l'infrastructure IA (la « pile »
> matérielle et logicielle), puis isoler les acteurs **sous-évalués ou sous-appréciés par le
> marché** alors qu'ils jouent — ou vont jouer — un rôle critique. Document de travail destiné à
> alimenter les décisions du journal de trading (`positions.md`, `etoro_trades.md`).
>
> **Date :** 25 mai 2026. **Auteur :** analyse « picks-and-shovels » de l'écosystème IA.
>
> **Avertissement d'honnêteté intellectuelle.** En mai 2026, l'ensemble du complexe « infra IA »
> s'est **déjà fortement revalorisé** (Nvidia ~47× les bénéfices futurs ; en Europe : STMicro a plus
> que doublé, Aixtron +168 %, Besi +87 %, Soitec ×5, Coherent +97 %, Applied Optoelectronics +441 %).
> Le « pas cher en absolu » est donc rare. Le *vrai* gisement de valeur sous-évaluée se trouve dans
> trois poches : **(A)** les dislocations tactiques de la panique « surcapacité » de mi-mai 2026,
> **(B)** les dérivées énergie/réseau **sous-possédées** (multiples plus bas que les semis pour un
> vent porteur identique), **(C)** les **oligopoles de composants sous le radar** (substrats, test,
> vide, thermique). Ce document distingue systématiquement « encore décoté » de « déjà découvert ».

---

## 1. Vue d'ensemble — le chiffre qui commande tout

- **Capex des hyperscalers ≈ 725 Md$ en 2026** (Microsoft, Amazon, Google, Meta, Oracle), l'essentiel
  fléché vers les data centers.
- **Demande électrique des data centers : +220 % vs 2023 → ~1 350 TWh en 2030.** L'IA est devenue,
  d'abord, un **trade énergétique**.
- **Le goulet d'étranglement s'est déplacé** : `calcul (GPU) → énergie → refroidissement → optique →
  mémoire (HBM) → packaging avancé → substrats`. La valeur migre vers l'aval (énergie, thermique) et
  vers des composants oligopolistiques peu visibles.
- **Le risque dominant** n'est plus « manque de puce » mais **« manque de mégawatts »** :
  transformateurs à **128 semaines** de délai, *generator step-up units* à 144 semaines, équipements
  sur mesure à 3–5 ans. Plus de la moitié des projets US 2026 risquent un report faute d'équipement
  électrique.

---

## 2. Cartographie complète de l'infrastructure IA (la « pile »)

> Lecture de bas en haut : des atomes (matériaux) jusqu'aux applications. Chaque couche est un marché
> distinct, avec ses leaders, ses oligopoles et ses goulets.

### Couche 0 — Matières premières & matériaux avancés
| Sous-segment | Acteurs clés |
|---|---|
| Gaz industriels & spéciaux (fabs) | **Air Liquide (AI.PA)**, Linde (LIN), Air Products (APD), Merck KGaA |
| Wafers silicium | Shin-Etsu (4063.T), SUMCO (3436.T), **Siltronic (WAF.DE)**, GlobalWafers |
| Substrats spéciaux (SOI, photonique) | **Soitec (SOI.PA)** |
| Photorésines & chimie | JSR, Tokyo Ohka, Shin-Etsu, **Entegris (ENTG)**, DuPont, Merck KGaA |
| CMP / filtration / matériaux process | Entegris, Cabot Microelectronics |
| Cuivre (câbles, busbars, bobinages) | Freeport-McMoRan (FCX) et miniers cuivre |
| Terres rares (aimants, moteurs) | MP Materials (MP), Lynas |
| Uranium (carburant nucléaire) | Cameco (CCJ), **Centrus Energy (LEU, enrichissement)**, Kazatomprom |

### Couche 1 — Équipements de fabrication des semi-conducteurs (WFE) + test
| Sous-segment | Acteurs clés (oligopoles très défendables) |
|---|---|
| Lithographie EUV | **ASML (ASML.AS) — monopole mondial** |
| Dépôt / gravure / etch | Applied Materials (AMAT), Lam Research (LRCX), Tokyo Electron (8035.T), **ASM International (ASM.AS)** |
| Métrologie / inspection | KLA (KLAC) |
| Dépôt composé / photonique (MOCVD) | **Aixtron (AIXA.DE)** |
| Packaging avancé / hybrid bonding | **BE Semiconductor — Besi (BESI.AS)**, ASMPT (0522.HK), Kulicke & Soffa (KLIC) |
| Vannes à vide (sur *chaque* outil dépôt/etch) | **VAT Group (VACN.SW) — quasi-monopole** |
| Alimentation RF / plasma | **Comet (COTN.SW)**, Advanced Energy (AEIS), MKS Instruments (MKSI) |
| Sous-systèmes / sous-traitance outils | Ultra Clean Holdings (UCTT), MKS |
| **Test de puces (SoC + HBM)** | **Advantest (6857.T) — quasi-monopole test HBM/AI**, Teradyne (TER) |

### Couche 2 — Conception & propriété intellectuelle
| Sous-segment | Acteurs clés |
|---|---|
| EDA (logiciels de conception) | **Synopsys (SNPS), Cadence (CDNS)** — duopole |
| IP cœurs CPU/GPU | **Arm (ARM)** (Neoverse), Imagination |
| Concepteurs *fabless* GPU/CPU | Nvidia (NVDA), AMD (AMD), Intel (INTC), Qualcomm (QCOM), Apple |
| ASIC sur mesure / XPU des hyperscalers | **Broadcom (AVGO)** (TPU Google, Meta), **Marvell (MRVL)** (Trainium/Inferentia AWS, Maia MS), Alchip, GUC |
| Accélérateurs « challengers » | Groq, Cerebras, SambaNova, Tenstorrent, Graphcore, Etched, d-Matrix (majorité non cotés) |

### Couche 3 — Fabrication (fonderies) & assemblage (OSAT)
| Sous-segment | Acteurs clés |
|---|---|
| Fonderies de pointe (≤ 3 nm) | **TSMC (TSM / 2330.TW)** dominant, Samsung (005930.KS), Intel Foundry (INTC) |
| Fonderies matures / spéc. | GlobalFoundries (GFS), UMC, SMIC (Chine) |
| Packaging in-house (CoWoS / SoIC) | TSMC (capacité = goulet structurel) |
| OSAT (assemblage-test externalisé) | ASE (ASX), **Amkor (AMKR)**, JCET, Powertech |
| Substrats ABF (porte-puces) | **Ibiden (4062.T)**, Shinko (6967.T), **AT&S (ATS.VI)** |

### Couche 4 — Composants de calcul & mémoire
| Sous-segment | Acteurs clés |
|---|---|
| GPU / accélérateurs IA | Nvidia (NVDA), AMD (AMD), Intel Gaudi |
| Silicium maison hyperscalers | Google TPU, AWS Trainium/Inferentia, Microsoft Maia, Meta MTIA |
| CPU serveur | AMD (EPYC), Intel (Xeon), Arm-based (Ampere, hyperscalers) |
| **Mémoire HBM (goulet majeur)** | **SK Hynix (000660.KS) — leader**, **Samsung**, **Micron (MU)** |
| DRAM / NAND classiques | Mêmes + Kioxia |
| Stockage (HDD/SSD haute capacité) | Western Digital (WDC), Seagate (STX), Kioxia |

### Couche 5 — Réseau & interconnexion (le « tissu » du cluster)
| Sous-segment | Acteurs clés |
|---|---|
| Switchs & silicium réseau | **Broadcom (AVGO)** (Tomahawk/Jericho), **Arista (ANET)**, Marvell, Cisco, Nvidia (Spectrum/NVLink/InfiniBand via Mellanox) |
| Retimers / fabric / connectivité PCIe-CXL | **Astera Labs (ALAB)**, **Credo (CRDO)** (câbles électriques actifs) |
| Optique (transceivers 800G/1.6T) | **Coherent (COHR)**, Lumentum (LITE), **Applied Optoelectronics (AAOI)**, InnoLight (300308.SZ), Eoptolink, MACOM (MTSI) |
| Fabrication optique sous contrat | **Fabrinet (FN)** |
| Photonique sur silicium / substrats | Nvidia+TSMC, GlobalFoundries, **Soitec (SOI.PA)** (substrats), Ayar Labs (privé) |
| Connecteurs & câblage cuivre haute densité | **Amphenol (APH)**, **TE Connectivity (TEL)**, Molex |

### Couche 6 — Infrastructure physique du data center
**6a. Énergie (production → réseau → distribution dans la salle)**
| Sous-segment | Acteurs clés |
|---|---|
| Production nucléaire (PPA hyperscalers) | **Constellation Energy (CEG)**, **Vistra (VST)**, **Talen (TLN)**, NRG |
| Petits réacteurs modulaires (SMR) | Oklo (OKLO), NuScale (SMR), Nano Nuclear |
| Turbines à gaz | **GE Vernova (GEV)**, **Siemens Energy (ENR.DE)**, Mitsubishi Heavy (7011.T) |
| Piles à combustible / on-site | Bloom Energy (BE) |
| Transformateurs & équipement réseau (goulet) | **GE Vernova (Prolec)**, **Hitachi Energy** (via Hitachi 6501.T), **ABB (ABBN.SW)**, Eaton (ETN), Siemens Energy, Hyundai Electric |
| Câbles électriques & grid (HT/MT) | **Prysmian (PRY.MI)**, **Nexans (NEX.PA)** |
| Appareillage / switchgear | ABB, Eaton, **Schneider (SU.PA)**, **Powell Industries (POWL)** |
| Onduleurs (UPS) / PDU / busways | **Vertiv (VRT)**, **Schneider (SU.PA / APC)**, Eaton, Legrand |
| Groupes électrogènes / backup | Cummins (CMI), Caterpillar (CAT), Generac (GNRC) |

**6b. Refroidissement (le 2ᵉ goulet, densités de rack > 100 kW)**
| Sous-segment | Acteurs clés |
|---|---|
| Power + cooling intégrés (liquide) | **Vertiv (VRT)** — leader |
| Refroidissement air/liquide DC | **Munters (MTRS.ST)**, nVent (NVT), Modine (MOD), SPX Technologies (SPXC) |
| CVC / thermique de grand bâtiment | Johnson Controls (JCI), Trane (TT), Carrier (CARR) |
| Direct-to-chip / immersion | Boyd (privé), Asetek, Schneider (Motivair) |

**6c. Bâtiment, foncier & construction**
| Sous-segment | Acteurs clés |
|---|---|
| REIT / opérateurs data centers | **Equinix (EQIX)**, **Digital Realty (DLR)**, Iron Mountain (IRM) |
| Ingénierie/construction électrique & grid | **Quanta Services (PWR)**, MasTec, MYR Group |
| Mécanique / CVC de chantier DC | **Comfort Systems (FIX)**, EMCOR (EME), Sterling Infrastructure (STRL) |

### Couche 7 — Serveurs, systèmes & ODM
| Sous-segment | Acteurs clés |
|---|---|
| OEM serveurs IA | **Dell (DELL)**, Super Micro (SMCI), HPE (HPE), Lenovo |
| ODM / intégration rack (Taïwan) | Foxconn/Hon Hai (2317.TW), Quanta, Wiwynn, Wistron, Inventec |

### Couche 8 — Cloud / capacité de calcul (« compute as a service »)
| Sous-segment | Acteurs clés |
|---|---|
| Hyperscalers | **Microsoft (MSFT)**, **Amazon (AMZN)**, **Alphabet (GOOGL)**, **Oracle (ORCL)**, Meta (META, captif) |
| « Neoclouds » GPU pur | **CoreWeave (CRWV)**, **Nebius (NBIS)**, IREN (IREN), Applied Digital (APLD), Lambda, Crusoe (privés) |
| Cloud souverain européen | **OVHcloud (OVH.PA)** |
| Chine | Alibaba (9988.HK), Tencent, Baidu, Huawei |

### Couche 9 — Modèles de fondation & logiciel
| Sous-segment | Acteurs clés |
|---|---|
| Laboratoires de modèles | OpenAI, Anthropic, Google DeepMind, Meta (Llama), **Mistral (FR, privé)**, xAI, Cohere, DeepSeek, Alibaba (Qwen) |
| Douve logicielle (lock-in) | **Nvidia CUDA**, PyTorch |
| Données / lakehouse / vecteurs | Snowflake (SNOW), Databricks (privé), MongoDB (MDB), Confluent, Elastic |
| Observabilité / MLOps / déploiement | Datadog (DDOG), Palantir (PLTR) |

---

## 3. Où se déplace la valeur en 2026 (les 6 goulets)

1. **Énergie / réseau** — *le* goulet n°1. Transformateurs et switchgear à 2–3 ans de délai. La
   demande dépasse la capacité d'interconnexion des réseaux. Les hyperscalers signent des PPA
   nucléaires 20 ans (Constellation/Microsoft sur Three Mile Island ; Vistra/AWS sur Comanche Peak ;
   Vistra/Meta > 2 600 MW) et investissent en direct dans la production (Google + Intersect Power,
   20 Md$).
2. **Refroidissement** — au-delà de 100 kW/rack, l'air ne suffit plus. Bascule structurelle vers le
   liquide (direct-to-chip, immersion). Vertiv : **+252 % de commandes**, ~13,5 Md$ de CA visé 2026.
3. **Optique / interconnexion** — quand un cluster passe à des centaines de milliers d'accélérateurs,
   le cuivre sature et la fibre prend le relais. Nvidia a investi **2 Md$ dans Coherent**.
4. **Mémoire HBM** — la HBM est rationnée ; chaque génération de GPU en consomme davantage. Capacité
   tendue chez SK Hynix / Samsung / Micron → discipline d'offre et **hausse de prix attendue en 2027**.
5. **Packaging avancé** — CoWoS (TSMC) en goulet ; hybrid bonding (Besi) ; substrats ABF
   (Ibiden/Shinko/AT&S).
6. **Test** — chaque puce IA + chaque pile HBM doit être testée : Advantest est le passage obligé.

---

## 4. Thèse d'investissement — acteurs sous-évalués / sous-appréciés

> Critère retenu : *rôle critique avéré ou à venir* **ET** (décote tactique récente **OU** multiple
> inférieur aux pairs pour un vent porteur identique **OU** criticité encore mal pricée).

### Poche A — Dislocations tactiques (panique « surcapacité » de mi-mai 2026)
Le marché a paniqué sur un risque d'**excès de capacité** 2026-2027 et a vendu des leaders de qualité
dont le carnet de commandes reste plein. Achat de la **faiblesse**, pas de la force.

| Acteur | Ticker | Rôle | Pourquoi sous-évalué *maintenant* | Risque |
|---|---|---|---|---|
| **Micron** | MU (Nasdaq) | HBM + DRAM/NAND | Chuté de 818 → ~681 $ (-6 % en séance) sur annonce capacité Samsung + WD ; or Citi voit la **HBM tendue, prix en hausse 2027**. Multiple bas pour la croissance. | Cyclicité mémoire, guerre des prix |
| **SK Hynix** | 000660.KS (Corée) | **Leader HBM** (fournisseur n°1 de Nvidia) | PER très bas (décote « mémoire cyclique ») alors que c'est le play HBM le plus pur. | Accès marché coréen ; cyclicité |
| **Vertiv** | VRT (NYSE) | Power + cooling liquide (leader) | Sous ses plus-hauts malgré +252 % de commandes : la peur de surcapacité price un ralentissement que le carnet dément. | Si capex hyperscalers coupé |

### Poche B — Dérivées énergie / réseau **sous-possédées** (multiples < semis)
« L'IA est un trade énergétique » : ces noms captent le même capex avec des multiples plus bas et un
public moins encombré (*under-owned*). Plusieurs sont **européens et accessibles**.

| Acteur | Ticker | Rôle | Angle de décote / sous-appréciation |
|---|---|---|---|
| **Siemens Energy** | ENR.DE | Turbines à gaz + réseau + transfo | +43 % YTD mais sort à peine d'un quasi-naufrage 2023 ; re-rating en cours, carnet réseau record. |
| **GE Vernova** | GEV (NYSE) | Transfos (Prolec) + turbines | *Book-to-bill* Électrification ~2,5× ; 2,4 Md$ de commandes data center au seul T1 (> tout 2025). A déjà bien monté → acheter les replis. |
| **Eaton / ABB** | ETN / ABBN.SW | Switchgear, transfos, distribution | Industriels de qualité, multiples raisonnables vs semis, exposition directe au goulet électrique. |
| **Nexans** | **NEX.PA** | Câbles HT/réseau & data center | **Le laggard français** : moins cher que Prysmian (+71 %), même thèse grid + DC. Candidat « value » accessible Euronext. |
| **Prysmian** | PRY.MI | Câbles (leader mondial) | A déjà couru (+71 %) ; conserver/acheter repli. |
| **Constellation / Vistra / Talen** | CEG / VST / TLN | Production (nucléaire) sous PPA | Revalorisés mais encore à des multiples d'*utilities* ; visibilité 20 ans via PPA hyperscalers. |
| **Cameco / Centrus** | CCJ / LEU | Uranium / **enrichissement** | Dérivée « 3ᵉ ordre » du nucléaire IA ; Centrus = quasi-pure-play enrichissement US, volatil. |

### Poche C — Oligopoles de composants **sous le radar** (criticité mal pricée)
Passages obligés peu médiatisés. Souvent **européens / suisses / japonais**, donc moins « crowded »
que les noms US.

| Acteur | Ticker | Rôle (pourquoi incontournable) | Statut valorisation |
|---|---|---|---|
| **Advantest** | 6857.T (Tokyo) | **Quasi-monopole du test HBM & SoC IA** — chaque puce passe par lui | Gagnant structurel souvent oublié hors Japon |
| **VAT Group** | VACN.SW (Suisse) | **Quasi-monopole des vannes à vide** — sur *chaque* outil dépôt/etch | Qualité rare, à acheter sur repli |
| **AT&S** | ATS.VI (Vienne) | Substrats ABF (porte-puces) | **Vrai contrarian** : massacré (surcapex, dette) mais levier direct au packaging avancé. Spéculatif. |
| **Comet** | COTN.SW (Suisse) | Alimentation RF pour plasma etch | Petite cap cyclique, levier WFE |
| **Munters** | MTRS.ST (Suède) | Refroidissement DC (air + liquide) | Alternative *under-the-radar* à Vertiv |
| **Modine** | MOD (NYSE) | Gestion thermique DC | Mid-cap, gagnant refroidissement |
| **Amphenol / TE** | APH / TEL | Connecteurs & câblage haute densité | Composeurs de qualité, multiples raisonnables |
| **Soitec** | **SOI.PA** | Substrats SOI pour photonique/optique | ⚠️ **Déjà découvert** (×5 YTD) — surveiller un repli, pas un achat de force |

### Poche D — Spéculatif / capacité cloud (haut risque, fort levier)
| Acteur | Ticker | Rôle | Note |
|---|---|---|---|
| **Nebius** | **NBIS** (Amsterdam/Nasdaq) | Neocloud GPU | Européen accessible ; pari capacité, volatil |
| **CoreWeave** | CRWV | Neocloud GPU | Très endetté, bêta extrême |
| **OVHcloud** | **OVH.PA** | Cloud souverain FR | Petite cap décotée, pari souveraineté EU |

---

## 5. Lien avec le portefeuille actuel (≈ 1 017 €, eToro / Euronext)

| Ligne détenue | Lecture « infra IA » | Action suggérée |
|---|---|---|
| **Air Liquide (AI.PA)** | **Vrai play infra IA sous-apprécié** : gaz spéciaux indispensables aux fabs (la consommation grimpe avec chaque nouvelle fab). Le marché la traite en « défensive », pas en « picks-and-shovels » → décote de perception. | **Conserver** — réévaluer comme ligne IA, pas seulement défensive |
| **Schneider (SU.PA)** | Cœur du goulet **power + cooling** data center. Thèse validée (meilleur performeur du portefeuille). | **Conserver / laisser courir** |
| **STMicro (STM.PA)** | Semi power/analog, exposition capex indirecte. | Conserver dans la zone neutre (cf. carnet d'ordres) |
| **Sanofi (SAN.PA)** | **Hors thème IA** — pure défensive/rendement. | Candidate à rotation si une idée infra à meilleur couple rendement/risque se présente |

### Candidats de rotation **réalistes pour ce compte** (Euronext, taille de ~1 share)
Le compte est petit ; privilégier des titres à prix d'action modéré, cotés Euronext (accès direct
eToro), sur les goulets les moins « crowded » :

1. **Nexans (NEX.PA)** — câbles réseau/DC, *laggard* value vs Prysmian. Goulet énergie.
2. **Soitec (SOI.PA)** — substrats photonique ; **uniquement sur repli** (déjà ×5).
3. **Besi (BESI.AS)** — hybrid bonding ; sur repli (déjà +87 %).
4. **Nebius (NBIS)** — pari capacité cloud, petite louche spéculative, volatil.
5. **Micron (MU)** — si l'on s'autorise une ligne US : la dislocation HBM de mai est le meilleur
   couple rendement/risque « qualité en solde » de la liste.

> Cohérence avec la méthode du journal : **acheter la faiblesse, pas la force.** Les noms déjà ×2–×5
> cette année (Soitec, Besi, STM, Aixtron) ne s'achètent **que** sur repli ; le gisement « propre »
> est en poche A (dislocations) et poche B (dérivées énergie sous-possédées).

---

## 6. Watchlist chiffrée & déclencheurs

| # | Acteur | Goulet | Déclencheur d'achat | Logique |
|---|---|---|---|---|
| 1 | Micron (MU) | HBM | Repli/stabilisation < ~700 $ post-panique mai | Qualité en solde, HBM tendue 2027 |
| 2 | Nexans (NEX.PA) | Réseau | Faiblesse de marché large | *Laggard* value vs Prysmian, accessible EU |
| 3 | Vertiv (VRT) | Cooling | Capitulation surcapacité + carnet confirmé | Leader décoté sur narratif, pas sur fondamentaux |
| 4 | Siemens Energy (ENR.DE) | Énergie | Repli technique | Re-rating réseau/turbines, *under-owned* |
| 5 | Advantest (6857.T) | Test | Repli sectoriel semi | Quasi-monopole test, gagnant oublié |
| 6 | VAT Group (VACN.SW) | WFE | Repli WFE | Monopole vannes à vide |
| 7 | Soitec (SOI.PA) | Optique | **Repli > -20 %** seulement | Déjà découvert, ne pas chasser |
| 8 | AT&S (ATS.VI) | Substrats | Spéculatif, petite taille uniquement | Contrarian fort levier packaging |

---

## 7. Risques transverses (à toujours garder en tête)

1. **Coupe de capex hyperscalers** — si MSFT/AMZN/GOOGL/META révisent 2026-2027 à la baisse, les
   commandes infra molissent en 1–2 trimestres. Risque n°1 de toute la thèse.
2. **Surcapacité énergie/cooling** — annonces de capacité plus rapides que les interconnexions réseau
   ; soit la contrainte électrique calme la demande, soit des projets glissent.
3. **Financement circulaire** — investissements croisés (Nvidia ↔ Coherent / CoreWeave / labos) qui
   gonflent la demande affichée ; à surveiller comme signal de bulle.
4. **Valorisations tendues** — une grande partie du complexe price déjà l'exécution parfaite (Coherent
   ~159× ; semis EU ×2–×5). Marge d'erreur faible.
5. **Géopolitique / Chine** — contrôles à l'export, capacité chinoise (SMIC, InnoLight, Eoptolink),
   dépendance Taïwan (TSMC) = risque systémique.
6. **Discipline d'offre mémoire** — si Samsung casse les prix HBM, la thèse Micron/Hynix se dégrade.

---

## 8. Synthèse en une phrase

> En 2026, l'argent « facile » sur Nvidia et les semis européens est fait ; le **rendement ajusté du
> risque** se trouve désormais (1) dans les **dislocations tactiques** de qualité (Micron, Vertiv,
> SK Hynix), (2) dans les **dérivées énergie/réseau sous-possédées** (Siemens Energy, Nexans, GE
> Vernova, nucléaire/uranium) et (3) dans les **oligopoles de composants invisibles** (Advantest, VAT,
> AT&S) — en achetant toujours **la faiblesse, pas la force**.

---

## Sources

- [AI Infrastructure Stocks 2026: Picks and Shovels Playbook — heygotrade](https://www.heygotrade.com/en/blog/ai-infrastructure-stocks-picks-and-shovels-playbook/)
- [Will 2026's Top Stocks Keep Riding the AI Infrastructure Boom? — Morningstar](https://www.morningstar.com/stocks/will-2026s-top-stocks-keep-riding-ai-infrastructure-boom)
- [Is This AI Data Center Stock a Buy While the Market Panics About Oversupply? (VRT) — Motley Fool](https://www.fool.com/investing/2026/05/22/ai-data-center-stock-buy-oversupply-vrt/)
- [European AI stocks soar as investors hunt for winners — Resultsense](https://www.resultsense.com/news/2026-05-13-european-ai-stocks-soar-investor-hunt/)
- [European infrastructure and data center stocks outperforming, +23% YTD — CryptoRank](https://cryptorank.io/news/feed/b17a2-europe-data-center-infrastructure-ai-stocks)
- [What makes a stock jump 1,000%? Europe's winners of 2026 — Euronews](https://www.euronews.com/business/2026/05/12/europes-best-performing-stocks-of-2026-including-one-up-by-947)
- [Goldman Sachs Sees European Electrification Driving a New Investment Cycle — MarketScreener](https://www.marketscreener.com/news/goldman-sachs-sees-european-electrification-driving-a-new-investment-cycle-ce7f58dcd18af721)
- [Which Optics Stock Has Dominated in 2026: AOI, Lumentum, or Coherent? — 24/7 Wall St.](https://247wallst.com/investing/2026/05/12/which-optics-stock-has-dominated-in-2026-applied-optoelectronics-lumentum-or-coherent/)
- [Astera Labs (ALAB) Stock Analysis — Simply Wall St](https://simplywall.st/stocks/us/semiconductors/nasdaq-alab/astera-labs)
- [Citi resets Micron stock price target after an anomaly — TheStreet](https://www.thestreet.com/investing/stocks/citi-resets-micron-stock-price-target-after-an-anomaly)
- [Power Bottlenecks & The AI Data Center — Tech Fund](https://www.techinvestments.io/p/power-bottlenecks-and-the-ai-data)
- [More than half of Data Centers may be delayed due to lack of transformers — Energy News Beat](https://energynewsbeat.co/ai/more-than-half-of-the-data-centers-may-be-delayed-due-to-lack-of-transformers-and-electrical-equipment-2/)
- [How AI Data Centers Are Reshaping the Power Market (4 Plays) — 24/7 Wall St.](https://247wallst.com/investing/2026/05/04/how-ai-data-centers-are-reshaping-the-power-market-and-the-4-plays-investors-are-making/)
- [Key Questions on Energy and AI — IEA](https://www.iea.org/reports/key-questions-on-energy-and-ai/executive-summary)
