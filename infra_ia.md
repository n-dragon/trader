# Infrastructure de l'Intelligence Artificielle — Cartographie & thèse d'investissement

> **Objet.** Identifier *tous* les acteurs et composants de l'infrastructure IA (la « pile »
> matérielle et logicielle), puis isoler les acteurs **sous-évalués ou sous-appréciés par le
> marché** alors qu'ils jouent — ou vont jouer — un rôle critique. Document de travail destiné à
> alimenter les décisions du journal de trading (`positions.md`, `etoro_trades.md`).
>
> **Date :** 26 mai 2026 — **édition approfondie** (v2). Première version : 25 mai 2026.
> **Auteur :** analyse « picks-and-shovels » de l'écosystème IA.
>
> **Avertissement d'honnêteté intellectuelle.** En mai 2026, l'ensemble du complexe « infra IA »
> s'est **déjà fortement revalorisé** (Nvidia ~47× les bénéfices futurs ; en Europe : STMicro a plus
> que doublé, Aixtron +168 %, Besi +87 %, Soitec ×5, Coherent +97 %, Applied Optoelectronics +441 % ;
> hors Europe : Hammond Power +111 % YTD, Delta Electronics ~100 Md$ de capitalisation). Le « pas cher
> en absolu » est donc rare. Le *vrai* gisement de valeur sous-évaluée se trouve dans quatre poches :
> **(A)** les dislocations tactiques de la panique « surcapacité / OpenAI » de fin avril–mai 2026,
> **(B)** les dérivées énergie/réseau **sous-possédées** (multiples plus bas que les semis pour un
> vent porteur identique), **(C)** les **oligopoles de composants sous le radar** (substrats, test,
> vide, thermique, packaging HBM), **(D/E/F)** les nouvelles poches identifiées dans cette édition
> (équipements de packaging HBM, bascule 800 VDC, gaz-to-power midstream). Ce document distingue
> systématiquement « encore décoté » de « déjà découvert ».

---

## 0. Ce qui a changé / s'est précisé depuis le 25 mai (édition v2)

Cette révision **approfondit** la v1 sans renier ses conclusions. Les ajouts majeurs :

1. **Le déclencheur de la panique de mai est nommé.** Le 28 avril 2026, **OpenAI a manqué ses
   objectifs internes** de revenus et d'utilisateurs → sell-off de l'infra IA (Oracle ~-3 %, puis
   Nvidia/Broadcom/AMD). Plus tôt, **Alphabet a guidé 175–185 Md$ de capex 2026** (~×2 vs 2025),
   ce qui a paradoxalement *déclenché* un repli (le marché doute de la conversion capex → bénéfices).
   **Lecture clé :** la majorité des goulets restent **sous-approvisionnés, pas en surcapacité**
   (compute, mémoire, réseau, *énergie*, packaging avancé). La panique = opportunité, pas signal de
   sortie. Capex hyperscalers 2026 désormais estimé **> 700 Md$** (vs ~410 Md$ en 2025).
2. **Nouvelle couche identifiée : le carburant.** « Gaz-to-power » et midstream alimentent
   directement les data centers (Williams 5,1 Md$ de « power innovation » ; Kinder Morgan >10 Md$ de
   carnet ; Energy Transfer 1,2 GW *off-grid* pour CloudBurst). Couche absente de la v1.
3. **Bascule architecturale : 400 → 800 VDC.** Nvidia pousse une rupture d'architecture électrique du
   rack (liste de 14 partenaires 800 VDC publiée). Crée de nouveaux gagnants en **semis de puissance
   GaN/SiC** (Navitas, Infineon, Monolithic Power, Vicor) et profite aux intégrateurs (Delta, Lite-On).
4. **Packaging HBM précisé.** Le *TC bonder* (thermo-compression) est un goulet : **Hanmi
   Semiconductor ~71 % de part de marché** — mais avec un **risque de concentration client** (SK Hynix
   diversifie, Macquarie a coupé ses prévisions Hanmi 2026). À traiter avec nuance.
5. **Oligopoles japonais ajoutés.** **Disco (6146.T)** — quasi-monopole découpe/meulage (ROIC ~54 %,
   sans dette) ; **Lasertec (6920.T)** — >90 % de l'inspection de masques EUV (mais volatil/cher).
6. **Consolidation du refroidissement.** **Eaton a racheté Boyd Thermal pour 9,5 Md$ (mars 2026)** ;
   **Ecolab a pris CoolIT** — l'intégration *power + cooling au niveau du rack* devient la norme.
7. **Pure-plays transformateurs.** **Hammond Power (HPS.A)** et **HD Hyundai Electric (042100.KS)** :
   leviers plus purs sur le goulet transformateur que les conglomérats.

---

## 1. Vue d'ensemble — le chiffre qui commande tout

- **Capex des hyperscalers > 725 Md$ en 2026** (Microsoft, Amazon, Google, Meta, Oracle), contre
  ~410 Md$ en 2025 — l'essentiel fléché vers les data centers. Alphabet seul vise 175–185 Md$.
- **Demande électrique des data centers : +220 % vs 2023 → ~1 350 TWh en 2030.** L'IA est devenue,
  d'abord, un **trade énergétique**. La demande de **gaz** des DC US pourrait atteindre ~6,1 Bcf/j en 2030.
- **Le goulet d'étranglement s'est déplacé** : `calcul (GPU) → énergie → carburant (gaz) →
  refroidissement → optique → mémoire (HBM) → packaging avancé → substrats`. La valeur migre vers
  l'aval (énergie, carburant, thermique) et vers des composants oligopolistiques peu visibles.
- **Le risque dominant** n'est plus « manque de puce » mais **« manque de mégawatts »** :
  transformateurs à **24–36 mois** de délai (vs 6–12 auparavant), *generator step-up units* à ~144
  semaines, équipements sur mesure à 3–5 ans. Plus de la moitié des projets US 2026 risquent un report
  faute d'équipement électrique. D'où la ruée vers le **gaz on-site** (rapidité de mise en service).
- **Densité de rack en rupture :** Blackwell ~120 kW/rack (5–10× les générations précédentes) → le
  refroidissement **liquide devient obligatoire** et l'architecture électrique bascule vers le 800 VDC.

---

## 2. Cartographie complète de l'infrastructure IA (la « pile »)

> Lecture de bas en haut : des atomes (matériaux) jusqu'aux applications. Chaque couche est un marché
> distinct, avec ses leaders, ses oligopoles et ses goulets. **(★ = ajout/approfondissement v2)**

### Couche 0 — Matières premières & matériaux avancés
| Sous-segment | Acteurs clés |
|---|---|
| Gaz industriels & spéciaux (fabs) | **Air Liquide (AI.PA)**, Linde (LIN), Air Products (APD), Merck KGaA |
| Wafers silicium | Shin-Etsu (4063.T), SUMCO (3436.T), **Siltronic (WAF.DE)**, GlobalWafers |
| Substrats spéciaux (SOI, photonique) | **Soitec (SOI.PA)** |
| Photorésines & chimie | JSR, Tokyo Ohka, Shin-Etsu, **Entegris (ENTG)**, DuPont, Merck KGaA |
| CMP / filtration / matériaux process | Entegris, Cabot Microelectronics |
| ★ Verre & fibre optique (DC interconnect) | **Corning (GLW)** (accord Meta jusqu'à 6 Md$, fibre multicœur, CPO), Sumitomo Electric, Fujikura |
| ★ Fluides de refroidissement (diélectriques) | Chemours (Opteon), Solvay/Syensqo, Honeywell, 3M (sortie en cours) |
| Cuivre (câbles, busbars, bobinages) | Freeport-McMoRan (FCX) et miniers cuivre |
| Terres rares (aimants, moteurs) | MP Materials (MP), Lynas |
| Uranium (carburant nucléaire) | Cameco (CCJ), **Centrus Energy (LEU, enrichissement)**, Kazatomprom |
| ★ Gaz naturel (carburant gas-to-power) | producteurs + midstream → voir Couche 6a-bis |

### Couche 1 — Équipements de fabrication des semi-conducteurs (WFE) + test
| Sous-segment | Acteurs clés (oligopoles très défendables) |
|---|---|
| Lithographie EUV | **ASML (ASML.AS) — monopole mondial** |
| Dépôt / gravure / etch | Applied Materials (AMAT), Lam Research (LRCX), Tokyo Electron (8035.T), **ASM International (ASM.AS)** |
| Métrologie / inspection | KLA (KLAC) |
| ★ **Inspection de masques EUV** | **Lasertec (6920.T) — >90 % de part, actinique = monopole** |
| ★ **Découpe / meulage / polissage de wafers** | **Disco (6146.T) — quasi-monopole, ROIC ~54 %, sans dette** |
| Dépôt composé / photonique (MOCVD) | **Aixtron (AIXA.DE)** |
| Packaging avancé / hybrid bonding | **BE Semiconductor — Besi (BESI.AS)**, ASMPT (0522.HK), Kulicke & Soffa (KLIC) |
| ★ **TC bonders HBM (thermo-compression)** | **Hanmi Semiconductor (042700.KS) ~71 %**, SEMES (~13 %), ASMPT, Hanwha |
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
| Substrats ABF (porte-puces) | **Ibiden (4062.T)**, Shinko (6967.T), **AT&S (ATS.VI)**, Unimicron (3037.TW) |

### Couche 4 — Composants de calcul & mémoire
| Sous-segment | Acteurs clés |
|---|---|
| GPU / accélérateurs IA | Nvidia (NVDA), AMD (AMD), Intel Gaudi |
| Silicium maison hyperscalers | Google TPU, AWS Trainium/Inferentia, Microsoft Maia, Meta MTIA |
| CPU serveur | AMD (EPYC), Intel (Xeon), Arm-based (Ampere, hyperscalers) |
| **Mémoire HBM (goulet majeur)** | **SK Hynix (000660.KS) — leader**, **Samsung**, **Micron (MU)** |
| DRAM / NAND classiques | Mêmes + Kioxia |
| Stockage (HDD/SSD haute capacité) | Western Digital (WDC), Seagate (STX), Kioxia |
| ★ **Gestion de puissance / VRM (sur le die / le rack)** | **Monolithic Power (MPWR) ~70 % des sockets VRM gén. Rubin**, **Infineon (IFX.DE)**, **Vicor (VICR)**, Renesas, TI, ADI |

### Couche 5 — Réseau & interconnexion (le « tissu » du cluster)
| Sous-segment | Acteurs clés |
|---|---|
| Switchs & silicium réseau | **Broadcom (AVGO)** (Tomahawk/Jericho), **Arista (ANET)**, Marvell, Cisco, Nvidia (Spectrum/NVLink/InfiniBand via Mellanox) |
| Retimers / fabric / connectivité PCIe-CXL | **Astera Labs (ALAB)**, **Credo (CRDO)** (câbles électriques actifs) |
| Optique (transceivers 800G/1.6T) | **Coherent (COHR)**, **Lumentum (LITE)**, **Applied Optoelectronics (AAOI)**, InnoLight (300308.SZ), Eoptolink, MACOM (MTSI) |
| ★ **Optique co-packagée (CPO)** | **Lumentum + Coherent (4 Md$ investis par Nvidia, mars 2026)**, Corning, TSMC, Foxconn ; Lumentum = seul fournisseur d'EML 200G/voie en volume |
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
| Transformateurs & équipement réseau (goulet) | **GE Vernova (Prolec)**, **Hitachi Energy** (via Hitachi 6501.T), **ABB (ABBN.SW)**, Eaton (ETN), Siemens Energy, **HD Hyundai Electric (042100.KS)**, **Hammond Power (HPS.A.TO)** |
| Câbles électriques & grid (HT/MT) | **Prysmian (PRY.MI)**, **Nexans (NEX.PA)** |
| Appareillage / switchgear | ABB, Eaton, **Schneider (SU.PA)**, **Powell Industries (POWL)**, Hubbell (HUBB) |
| Onduleurs (UPS) / PDU / busways | **Vertiv (VRT)**, **Schneider (SU.PA / APC)**, Eaton, Legrand (LR.PA) |
| Alimentations serveur / power shelf | ★ **Delta Electronics (2308.TW) ~60 % du marché**, Lite-On (2301.TW), Vertiv |
| Groupes électrogènes / backup | Cummins (CMI), Caterpillar (CAT), Generac (GNRC), Rolls-Royce (mtu) |

**★ 6a-bis. Carburant & gas-to-power (NOUVEAU — alimente directement les DC)**
| Sous-segment | Acteurs clés |
|---|---|
| Pipelines / midstream (gaz vers DC) | **Williams (WMB)** (5,1 Md$ « power innovation », Project Socrates 1,6 Md$), **Kinder Morgan (KMI)** (>10 Md$ de carnet, 40 % du gaz US), **Energy Transfer (ET)** (1,2 GW off-grid CloudBurst) |
| Producteurs de gaz | EQT (EQT), Expand Energy, Coterra |
| Centrales gaz modulaires on-site | ProEnergy, Williams (modular gas plants), GE Vernova/Siemens Energy (turbines) |

**6b. Refroidissement (le 2ᵉ goulet, densités de rack > 100 kW)**
| Sous-segment | Acteurs clés |
|---|---|
| Power + cooling intégrés (liquide) | **Vertiv (VRT)** — leader (~11 % de part), **Schneider**, **Delta Electronics (2308.TW)** |
| Refroidissement air/liquide DC | **Munters (MTRS.ST)**, **nVent (NVT)** (CDU 5× pour GB200/GB300), Modine (MOD), SPX Technologies (SPXC), Stulz, Rittal |
| CVC / thermique de grand bâtiment | Johnson Controls (JCI), Trane (TT), Carrier (CARR) |
| Direct-to-chip / immersion / cold plates | ★ **Boyd (racheté par Eaton, 9,5 Md$, mars 2026)**, **CoolIT (repris par Ecolab/ECL)**, Asetek, Chilldyne, Schneider (Motivair) |

**6c. Bâtiment, foncier & construction**
| Sous-segment | Acteurs clés |
|---|---|
| REIT / opérateurs data centers | **Equinix (EQIX)**, **Digital Realty (DLR)**, Iron Mountain (IRM) |
| Ingénierie/construction électrique & grid | **Quanta Services (PWR)**, MasTec (MTZ), MYR Group |
| Mécanique / CVC de chantier DC | **Comfort Systems (FIX)**, EMCOR (EME), Sterling Infrastructure (STRL) |

### Couche 7 — Serveurs, systèmes & ODM
| Sous-segment | Acteurs clés |
|---|---|
| OEM serveurs IA | **Dell (DELL)**, Super Micro (SMCI), HPE (HPE), Lenovo |
| ODM / intégration rack (Taïwan) | Foxconn/Hon Hai (2317.TW), Quanta (2382.TW), Wiwynn (6669.TW), Wistron, Inventec |

### Couche 8 — Cloud / capacité de calcul (« compute as a service »)
| Sous-segment | Acteurs clés |
|---|---|
| Hyperscalers | **Microsoft (MSFT)**, **Amazon (AMZN)**, **Alphabet (GOOGL)**, **Oracle (ORCL)**, Meta (META, captif) |
| « Neoclouds » GPU pur | **CoreWeave (CRWV)**, **Nebius (NBIS)**, IREN (IREN), Applied Digital (APLD), Lambda, Crusoe (privés) |
| Cloud souverain européen | **OVHcloud (OVH.PA)**, **Nebius (NBIS)** |
| Chine | Alibaba (9988.HK), Tencent, Baidu, Huawei |

### Couche 9 — Modèles de fondation & logiciel
| Sous-segment | Acteurs clés |
|---|---|
| Laboratoires de modèles | OpenAI, Anthropic, Google DeepMind, Meta (Llama), **Mistral (FR, privé)**, xAI, Cohere, DeepSeek, Alibaba (Qwen) |
| Douve logicielle (lock-in) | **Nvidia CUDA**, PyTorch |
| Données / lakehouse / vecteurs | Snowflake (SNOW), Databricks (privé), MongoDB (MDB), Confluent, Elastic |
| Observabilité / MLOps / déploiement | Datadog (DDOG), Palantir (PLTR) |

---

## 3. Où se déplace la valeur en 2026 (les goulets — version enrichie)

1. **Énergie / réseau** — *le* goulet n°1. Transformateurs à **24–36 mois** de délai (vs 6–12 avant),
   switchgear 2–3 ans. La demande dépasse la capacité d'interconnexion. PPA nucléaires 20 ans
   (Constellation/Microsoft sur Three Mile Island ; Vistra/AWS sur Comanche Peak ; Vistra/Meta
   > 2 600 MW) et investissements directs (Google + Intersect Power, 20 Md$).
2. **★ Carburant / gas-to-power (NOUVEAU goulet)** — faute de raccordement réseau rapide, les
   opérateurs se branchent au **gaz on-site** pour la *vitesse de mise sous tension*. Williams bâtit
   des centrales gaz modulaires *sur site* (Socrates) ; Energy Transfer livre 1,2 GW *off-grid* à
   CloudBurst. Le midstream devient une dérivée directe du capex IA, à des multiples d'« utility ».
3. **Refroidissement** — au-delà de 100 kW/rack (Blackwell ~120 kW), l'air ne suffit plus. Bascule
   structurelle vers le liquide (direct-to-chip, immersion). Vertiv : carnet > **15 Md$** (12–18 mois
   de CA), guidance 2026 relevée à ~**14 Md$**. **Consolidation :** Eaton+Boyd (9,5 Md$), Ecolab+CoolIT.
4. **★ Architecture électrique 400 → 800 VDC (NOUVELLE rupture)** — pour alimenter des racks de
   >100 kW sans pertes, Nvidia pousse le 800 VDC (liste de **14 partenaires** : ADI, AOSL, EPC,
   Infineon, Innoscience, MPS, Navitas, onsemi, Power Integrations, Renesas, Richtek, ROHM, STM, TI).
   Gagnants : **semis de puissance GaN/SiC** et intégrateurs (Delta, Lite-On).
5. **Optique / interconnexion & CPO** — quand un cluster passe à des centaines de milliers
   d'accélérateurs, le cuivre sature. Nvidia a investi **4 Md$ (mars 2026) dans Lumentum + Coherent**
   pour la **CPO** ; Lumentum est le seul à expédier des EML 200G/voie en volume. Corning fournit la
   fibre (accord Meta jusqu'à 6 Md$).
6. **Mémoire HBM** — rationnée ; chaque génération de GPU en consomme davantage. Capacité tendue chez
   SK Hynix / Samsung / Micron → discipline d'offre et **hausse de prix attendue en 2027**.
7. **Packaging avancé** — CoWoS (TSMC) en goulet ; hybrid bonding (Besi) ; **TC bonders HBM
   (Hanmi ~71 %)** ; substrats ABF (Ibiden/Shinko/AT&S/Unimicron) ; découpe/meulage (Disco).
8. **Test** — chaque puce IA + chaque pile HBM doit être testée : **Advantest** est le passage obligé ;
   chaque masque EUV est inspecté par **Lasertec**.

---

## 4. Thèse d'investissement — acteurs sous-évalués / sous-appréciés

> Critère retenu : *rôle critique avéré ou à venir* **ET** (décote tactique récente **OU** multiple
> inférieur aux pairs pour un vent porteur identique **OU** criticité encore mal pricée).

### Poche A — Dislocations tactiques (panique « OpenAI / surcapacité » avril–mai 2026)
Le 28 avril, OpenAI manque ses cibles internes ; combiné au doublement du capex Alphabet (175–185 Md$)
qui inquiète sur la conversion en bénéfices, le marché a vendu des leaders de qualité dont le carnet
reste plein. **Achat de la faiblesse, pas de la force.**

| Acteur | Ticker | Rôle | Pourquoi sous-évalué *maintenant* | Risque |
|---|---|---|---|---|
| **Micron** | MU (Nasdaq) | HBM + DRAM/NAND | Chuté sur annonce capacité Samsung + WD ; or Citi voit la **HBM tendue, prix en hausse 2027**. Identifié « high-conviction long » dans plusieurs synthèses de mai. Multiple bas pour la croissance. | Cyclicité mémoire, guerre des prix |
| **SK Hynix** | 000660.KS (Corée) | **Leader HBM** (fournisseur n°1 de Nvidia) | PER très bas (décote « mémoire cyclique ») alors que c'est le play HBM le plus pur. | Accès marché coréen ; cyclicité |
| **Vertiv** | VRT (NYSE) | Power + cooling liquide (leader) | Sous ses plus-hauts malgré un carnet **> 15 Md$** (+~80 % YoY) et guidance relevée à ~14 Md$ : la peur de surcapacité price un ralentissement que le carnet dément. | Si capex hyperscalers coupé |

### Poche B — Dérivées énergie / réseau **sous-possédées** (multiples < semis)
« L'IA est un trade énergétique » : ces noms captent le même capex avec des multiples plus bas et un
public moins encombré. Plusieurs sont **européens et accessibles**.

| Acteur | Ticker | Rôle | Angle de décote / sous-appréciation |
|---|---|---|---|
| **Siemens Energy** | ENR.DE | Turbines à gaz + réseau + transfo | Re-rating en cours après quasi-naufrage 2023, carnet réseau record. |
| **GE Vernova** | GEV (NYSE) | Transfos (Prolec) + turbines | *Book-to-bill* Électrification ~2,5× ; commandes data center record. Acheter les replis. |
| **HD Hyundai Electric** | 042100.KS | **Pure-play transformateurs/switchgear** (~100 % du CA) | Marges op. 24–26 % visées ; carnet géant ; capacité US (Alabama) fin 2026. Le **levier le plus pur** sur le goulet transfo. |
| **Hammond Power** | HPS.A.TO | **N°1 nord-américain transformateurs secs** | +111 % YTD, carnet **+122 % YoY** ; petite/mid-cap, levier direct grid+DC+nucléaire. Déjà couru → sur repli. |
| **Eaton / ABB** | ETN / ABBN.SW | Switchgear, transfos, distribution + cooling (Boyd) | Qualité, multiples raisonnables vs semis ; Eaton intègre désormais power+cooling au rack. |
| **Nexans** | **NEX.PA** | Câbles HT/réseau & data center | **Le laggard français** : moins cher que Prysmian (+71 %), même thèse grid + DC. « Value » accessible Euronext. |
| **Prysmian** | PRY.MI | Câbles (leader mondial) | A déjà couru ; conserver/acheter repli. |
| **Constellation / Vistra / Talen** | CEG / VST / TLN | Production (nucléaire) sous PPA | Encore à des multiples d'*utilities* ; visibilité 20 ans via PPA hyperscalers. Nucléaire = pièce **sous-possédée**. |
| **Cameco / Centrus** | CCJ / LEU | Uranium / **enrichissement** | Dérivée « 3ᵉ ordre » du nucléaire IA ; Centrus = quasi-pure-play enrichissement US, volatil. |

### Poche C — Oligopoles de composants **sous le radar** (criticité mal pricée)
Passages obligés peu médiatisés. Souvent **européens / suisses / japonais / coréens**, donc moins
« crowded » que les noms US.

| Acteur | Ticker | Rôle (pourquoi incontournable) | Statut valorisation |
|---|---|---|---|
| **Advantest** | 6857.T (Tokyo) | **Quasi-monopole du test HBM & SoC IA** — chaque puce passe par lui | Gagnant structurel souvent oublié hors Japon |
| **Disco** | 6146.T (Tokyo) | **Quasi-monopole découpe/meulage/polissage** — indispensable au packaging avancé | Qualité rare (ROIC ~54 %, sans dette) ; sous-suivi hors Japon |
| **Lasertec** | 6920.T (Tokyo) | **>90 % inspection masques EUV** (actinique = monopole) | Monopole réel **mais** valorisation élevée et titre **très volatil** → sur repli marqué seulement |
| **VAT Group** | VACN.SW (Suisse) | **Quasi-monopole des vannes à vide** — sur *chaque* outil dépôt/etch | Qualité rare, à acheter sur repli |
| **AT&S** | ATS.VI (Vienne) | Substrats ABF (porte-puces) | **Vrai contrarian** : massacré (surcapex, dette) mais levier direct au packaging. Spéculatif. |
| **Comet** | COTN.SW (Suisse) | Alimentation RF pour plasma etch | Petite cap cyclique, levier WFE |
| **Munters** | MTRS.ST (Suède) | Refroidissement DC (air + liquide) | Alternative *under-the-radar* à Vertiv |
| **Modine / nVent** | MOD / NVT | Gestion thermique DC (cold plate, CDU) | Mid-caps gagnantes du basculement liquide |
| **Amphenol / TE** | APH / TEL | Connecteurs & câblage haute densité | Composeurs de qualité, multiples raisonnables |
| **Soitec** | **SOI.PA** | Substrats SOI pour photonique/optique | ⚠️ **Déjà découvert** (×5 YTD) — surveiller un repli, pas un achat de force |

### ★ Poche E — Équipements de packaging & test HBM (NOUVELLE)
Le « picks-and-shovels » *du picks-and-shovels* : qui vend les outils qui assemblent et testent la HBM.

| Acteur | Ticker | Rôle | Angle / **nuance importante** |
|---|---|---|---|
| **Hanmi Semiconductor** | 042700.KS | **~71 % des TC bonders HBM** | Pure-play HBM packaging. **⚠️ Risque de concentration client** : SK Hynix réduit/diversifie ses commandes, Macquarie a coupé ses prévisions 2026. Marché TC bonder en croissance ~13 %/an jusqu'à 2030 mais Hanmi pourrait perdre des parts. **À surveiller, pas à chasser.** |
| **Besi** | BESI.AS | Hybrid bonding (gén. suivante du packaging) | Déjà +87 % YTD → sur repli |
| **ASMPT** | 0522.HK | TC bonders + hybrid bonding | Concurrent qui prend des parts à Hanmi |
| **Advantest / Disco / Lasertec** | 6857.T / 6146.T / 6920.T | Test & découpe/meulage & inspection masques | cf. Poche C — gagnants structurels |

### ★ Poche F — Carburant & gas-to-power midstream (NOUVELLE)
La dérivée énergétique **la plus sous-appréciée** : pipelines « ennuyeux » qui redeviennent des actifs
de croissance grâce aux DC, à des multiples d'infrastructure (et souvent un rendement).

| Acteur | Ticker | Rôle | Angle |
|---|---|---|---|
| **Williams** | WMB | Pipelines + **centrales gaz modulaires on-site** | 5,1 Md$ de « power innovation » (Socrates 1,6 Md$, livraison S2-2026) ; +~90 % en 2 ans mais re-rating « croissance » encore jeune. |
| **Kinder Morgan** | KMI | Plus grand réseau gaz d'Amérique du Nord (40 % du gaz US) | Carnet > 10 Md$ (93 % gaz) ; dérivée directe de la demande DC ; rendement. |
| **Energy Transfer** | ET | Midstream + **fourniture gaz directe aux DC** | 1er contrat *off-grid* (1,2 GW, CloudBurst, Texas) ; valorisation basse, gros rendement, structure MLP. |

### Poche D — Spéculatif / capacité cloud & semis de puissance (haut risque, fort levier)
| Acteur | Ticker | Rôle | Note |
|---|---|---|---|
| **Nebius** | **NBIS** (Amsterdam/Nasdaq) | Neocloud GPU | Européen accessible ; pari capacité, volatil |
| **CoreWeave** | CRWV | Neocloud GPU | Très endetté, bêta extrême |
| **OVHcloud** | **OVH.PA** | Cloud souverain FR | Petite cap décotée, pari souveraineté EU |
| ★ **Navitas** | NVTS | GaN pour 800 VDC (carte 800V→6V à GTC 2026) | Pari pur sur la bascule 800 VDC ; micro-cap très volatile, exécution non garantie |
| ★ **Monolithic Power** | MPWR | VRM (~70 % des sockets gén. Rubin) | Qualité, mais valorisation élevée → sur repli |
| ★ **Vicor / Infineon** | VICR / IFX.DE | Modules de puissance / CoolGaN | Leviers 800 VDC plus « industriels » ; Infineon = grande cap décotée vs pairs US |

---

## 5. Lien avec le portefeuille actuel (≈ 1 017 €, eToro / Euronext)

| Ligne détenue | Lecture « infra IA » | Action suggérée |
|---|---|---|
| **Air Liquide (AI.PA)** | **Vrai play infra IA sous-apprécié** : gaz spéciaux indispensables aux fabs (consommation ↑ avec chaque fab). Le marché la traite en « défensive » → décote de perception. | **Conserver** — réévaluer comme ligne IA, pas seulement défensive |
| **Schneider (SU.PA)** | Cœur du goulet **power + cooling** data center (UPS, switchgear, Motivair, CDU). Thèse validée (meilleur performeur du portefeuille). | **Conserver / laisser courir** |
| **STMicro (STM.PA)** | Semi power/analog, **désormais sur la liste 800 VDC de Nvidia** → exposition renforcée à la bascule électrique des racks. | Conserver dans la zone neutre (cf. carnet d'ordres) |
| **Sanofi (SAN.PA)** | **Hors thème IA** — pure défensive/rendement. | Candidate à rotation si une idée infra à meilleur couple rendement/risque se présente |

### Candidats de rotation **réalistes pour ce compte** (Euronext/accessibles, taille ~1 share)
Le compte est petit ; privilégier des titres à prix d'action modéré et accessibles, sur les goulets
les moins « crowded » :

1. **Nexans (NEX.PA)** — câbles réseau/DC, *laggard* value vs Prysmian. Goulet énergie. **Top pick EU.**
2. **Legrand (LR.PA)** — PDU/busways/infra électrique DC, qualité française moins « crowded » que Schneider.
3. **Micron (MU)** — si l'on s'autorise une ligne US : meilleure « qualité en solde » HBM de la liste.
4. **Soitec (SOI.PA)** / **Besi (BESI.AS)** — **uniquement sur repli** (déjà ×5 / +87 %).
5. **Nebius (NBIS)** — petite louche spéculative capacité cloud, volatil.
6. *(Hors Euronext, à noter en watchlist)* **HD Hyundai Electric**, **Williams (WMB)**, **Delta
   Electronics (2308.TW)** — leviers purs mais accès dépend du courtier.

> Cohérence avec la méthode du journal : **acheter la faiblesse, pas la force.** Les noms déjà ×2–×5
> cette année (Soitec, Besi, STM, Aixtron, Hammond) ne s'achètent **que** sur repli ; le gisement
> « propre » est en poche A (dislocations), poche B (dérivées énergie sous-possédées) et poche F (gas-to-power).

---

## 6. Watchlist chiffrée & déclencheurs

| # | Acteur | Goulet | Déclencheur d'achat | Logique |
|---|---|---|---|---|
| 1 | Micron (MU) | HBM | Repli/stabilisation post-panique mai | Qualité en solde, HBM tendue 2027 |
| 2 | Nexans (NEX.PA) | Réseau | Faiblesse de marché large | *Laggard* value vs Prysmian, accessible EU |
| 3 | Vertiv (VRT) | Cooling | Capitulation surcapacité + carnet > 15 Md$ confirmé | Leader décoté sur narratif, pas sur fondamentaux |
| 4 | Siemens Energy (ENR.DE) | Énergie | Repli technique | Re-rating réseau/turbines, *under-owned* |
| 5 | Williams (WMB) | Gas-to-power | Repli énergie / consolidation | Dérivée DC à multiple d'infra + rendement |
| 6 | HD Hyundai Electric (042100.KS) | Transfo | Repli sectoriel | Pure-play transfo, marges 24–26 % |
| 7 | Advantest (6857.T) | Test | Repli sectoriel semi | Quasi-monopole test, gagnant oublié |
| 8 | Disco (6146.T) | Packaging | Repli WFE | Quasi-monopole découpe/meulage, ROIC ~54 % |
| 9 | VAT Group (VACN.SW) | WFE | Repli WFE | Monopole vannes à vide |
| 10 | Soitec (SOI.PA) | Optique | **Repli > -20 %** seulement | Déjà découvert, ne pas chasser |
| 11 | AT&S (ATS.VI) | Substrats | Spéculatif, petite taille uniquement | Contrarian fort levier packaging |
| 12 | Hanmi Semi (042700.KS) | Packaging HBM | ⚠️ Stabilisation des commandes SK Hynix d'abord | Pure-play TC bonder, mais risque client |

---

## 7. Risques transverses (à toujours garder en tête)

1. **Coupe de capex hyperscalers** — risque n°1. Le **manquement OpenAI (28 avril)** et le doublement
   du capex Alphabet ont déjà secoué le marché : si MSFT/AMZN/GOOGL/META révisent 2026-2027 à la baisse,
   les commandes infra molissent en 1–2 trimestres. **Contre-argument :** les goulets restent
   sous-approvisionnés (compute, mémoire, énergie, packaging) — c'est une contrainte d'offre, pas un excès.
2. **Surcapacité énergie/cooling** — annonces de capacité plus rapides que les interconnexions réseau.
3. **Financement circulaire** — investissements croisés (Nvidia ↔ Lumentum/Coherent 4 Md$, ↔ CoreWeave,
   ↔ labos) qui gonflent la demande affichée ; à surveiller comme signal de bulle.
4. **Concentration client (spécifique packaging)** — Hanmi dépend de SK Hynix qui diversifie ; les
   pure-plays mono-client sont fragiles malgré leur part de marché.
5. **Valorisations tendues** — une grande partie du complexe price déjà l'exécution parfaite (Coherent
   ~159× ; semis EU ×2–×5 ; Lasertec/MPWR chers). Marge d'erreur faible.
6. **Géopolitique / Chine** — contrôles à l'export, capacité chinoise (SMIC, InnoLight, Eoptolink,
   Innoscience), dépendance Taïwan (TSMC, Delta, Foxconn) = risque systémique.
7. **Discipline d'offre mémoire** — si Samsung casse les prix HBM, la thèse Micron/Hynix se dégrade.
8. **Transition technologique** — la bascule 800 VDC et la CPO créent des gagnants *et des perdants* :
   parier sur la mauvaise architecture (ou le mauvais fournisseur de niche) est un risque réel.

---

## 8. Synthèse en une phrase

> En 2026, l'argent « facile » sur Nvidia et les semis européens est fait ; le **rendement ajusté du
> risque** se trouve désormais (1) dans les **dislocations tactiques** de qualité (Micron, Vertiv,
> SK Hynix), (2) dans les **dérivées énergie/réseau/carburant sous-possédées** (Siemens Energy, Nexans,
> GE Vernova, HD Hyundai/Hammond sur les transfos, Williams/KMI/ET sur le gaz-to-power, nucléaire/
> uranium) et (3) dans les **oligopoles de composants invisibles** (Advantest, Disco, VAT, AT&S, et —
> avec prudence sur la concentration client — Hanmi sur le packaging HBM) — en achetant toujours
> **la faiblesse, pas la force**, et en gardant à l'esprit que la panique « OpenAI » de mai price
> un excès qui, sur la plupart des goulets, n'existe pas (offre contrainte, pas surcapacité).

---

## Sources

### v1 (25 mai 2026)
- [AI Infrastructure Stocks 2026: Picks and Shovels Playbook — heygotrade](https://www.heygotrade.com/en/blog/ai-infrastructure-stocks-picks-and-shovels-playbook/)
- [Will 2026's Top Stocks Keep Riding the AI Infrastructure Boom? — Morningstar](https://www.morningstar.com/stocks/will-2026s-top-stocks-keep-riding-ai-infrastructure-boom)
- [Is This AI Data Center Stock a Buy While the Market Panics About Oversupply? (VRT) — Motley Fool](https://www.fool.com/investing/2026/05/22/ai-data-center-stock-buy-oversupply-vrt/)
- [European AI stocks soar as investors hunt for winners — Resultsense](https://www.resultsense.com/news/2026-05-13-european-ai-stocks-soar-investor-hunt/)
- [Which Optics Stock Has Dominated in 2026: AOI, Lumentum, or Coherent? — 24/7 Wall St.](https://247wallst.com/investing/2026/05/12/which-optics-stock-has-dominated-in-2026-applied-optoelectronics-lumentum-or-coherent/)
- [Citi resets Micron stock price target after an anomaly — TheStreet](https://www.thestreet.com/investing/stocks/citi-resets-micron-stock-price-target-after-an-anomaly)
- [Power Bottlenecks & The AI Data Center — Tech Fund](https://www.techinvestments.io/p/power-bottlenecks-and-the-ai-data)
- [More than half of Data Centers may be delayed due to lack of transformers — Energy News Beat](https://energynewsbeat.co/ai/more-than-half-of-the-data-centers-may-be-delayed-due-to-lack-of-transformers-and-electrical-equipment-2/)
- [Key Questions on Energy and AI — IEA](https://www.iea.org/reports/key-questions-on-energy-and-ai/executive-summary)

### v2 (26 mai 2026 — approfondissement)
- [Hanmi Semiconductor Secures 71% Global Market Share in TC Bonders — 아시아경제](https://cm.asiae.co.kr/en/article/2025122209224722092)
- [BESI, ASMPT, Hanmi, and Hanwha: HBM Supply Chain — Lumen Alpha](https://lumenalpha.substack.com/p/besi-asmpt-hanmi-and-hanwha-what)
- [Nvidia invests $4B in co-packaged optics suppliers Lumentum, Coherent — SiliconANGLE](https://siliconangle.com/2026/03/02/nvidia-invests-4b-co-packaged-optics-suppliers-lumentum-coherent/)
- [Inside Nvidia's $4B Optical Strategy — io-fund](https://io-fund.com/ai-stocks/nvidia-4b-optical-strategy-cpo-ai-data-centers)
- [5 Natural Gas Stocks to Buy as AI Data Centers Devour Power — MarketWise](https://marketwise.com/investing/five-natural-gas-stocks-to-buy-as-ai-data-centers-devour-power/)
- [Williams to Invest $3.1B in Power Projects for Data Centers — Yahoo Finance](https://finance.yahoo.com/news/williams-invest-3-1b-power-141500030.html)
- [Data centers and gas demand make boring pipelines great again — Fortune](https://fortune.com/2026/04/11/data-centers-gas-demand-make-boring-pipelines-great-again/)
- [Navitas Debuts 800V–6V Power Delivery Board at NVIDIA GTC 2026 — Navitas](https://navitassemi.com/navitas-debuts-revolutionary-800-v-6-v-power-delivery-board-at-nvidia-gtc-2026/)
- [The shift to 800-VDC power architectures in AI factories — EDN](https://www.edn.com/the-shift-to-800-vdc-power-architectures-in-ai-factories/)
- [Lasertec: The Global Monopoly in EUV Photomask Inspection — Oreate AI](https://www.oreateai.com/blog/lasertec-the-global-monopoly-in-euv-photomask-inspection/981a881b59881e5650513914c59b6694)
- [Disco Corp (TSE:6146): Japan's Leading Semiconductor Equipment Grower — DividendJapan](https://www.dividendjapan.com/p/disco-corp-dividend-growth-champion)
- [11 AI Data Center Cooling Stocks After the Ecolab-CoolIT Deal — MarketWise](https://marketwise.com/investing/ai-data-center-cooling-stocks-ecolab-coolit/)
- [Hammond Power Solutions: Why It's A Buy After Strong Q3 — Seeking Alpha](https://seekingalpha.com/article/4849045-hammond-power-solutions-why-its-a-buy-after-strong-q3-results)
- [HD Hyundai Electric: 27.6% Margin, Backlog — MoatAlpha](https://moatalpha.com/hd-hyundai-electric-analysis-2026/)
- [Taiwan-based Delta Electronics redefines value as power supplier — DigiTimes](https://www.digitimes.com/news/a20260123PD226/delta-electronics-ai-infrastructure-power-supply.html)
- [Corning and Meta Announce up to $6 Billion Agreement — Corning IR](https://investor.corning.com/news-and-events/news/news-details/2026/Corning-and-Meta-Announce-Multiyear-up-to-6-Billion-Agreement-to-Accelerate-US-Data-Center-Buildout/default.aspx)
- [AI Capex Risk: Why AI Infrastructure Stocks Sold Off (OpenAI revenue) — heygotrade](https://www.heygotrade.com/en/blog/ai-capex-risk-openai-revenue-report/)
- [Vertiv Raises 2026 Guidance to $14B as AI Backlog Surges 80% — TechJack](https://techjacksolutions.com/ai-brief/vertiv-raises-2026-guidance-to-14b-as-ai-data-center-backlog/)
- [The AI Data Center Buildout: Nuclear Could Be the Most Underowned Piece — 24/7 Wall St.](https://247wallst.com/investing/2026/05/22/the-ai-data-center-buildout-is-accelerating-and-nuclear-could-be-the-most-underowned-piece-of-the-puzzle/)
</content>
</invoke>
