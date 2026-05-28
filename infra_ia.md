# Infrastructure de l'Intelligence Artificielle — Cartographie & thèse d'investissement

> **Objet.** Identifier *tous* les acteurs et composants de l'infrastructure IA (la « pile »
> matérielle et logicielle), puis isoler les acteurs **sous-évalués ou sous-appréciés par le
> marché** alors qu'ils jouent — ou vont jouer — un rôle critique. Document de travail destiné à
> alimenter les décisions du journal de trading (`positions.md`, `etoro_trades.md`).
>
> **Date :** 28 mai 2026 — **édition v5** (six poches inédites : voir §10). v4 (sept poches) : 27 mai.
> v3 (valorisée) : 26 mai. v2 (approfondie) : 26 mai. v1 : 25 mai 2026.
> **Auteur :** analyse « picks-and-shovels » de l'écosystème IA.
>
> **Nouveauté v3.** Ajout d'une **couche quantitative** : pour chaque acteur, un **multiple de valorisation
> (PER avant — *forward P/E* —, parfois EV/EBITDA ou rendement) au ~25 mai 2026** et un **indice de
> sous-évaluation de 0 à 10** (10 = très sous-évalué / fort couple criticité-décote ; 0 = cher / déjà
> pleinement pricé). Voir la **§4 bis** pour le tableau maître trié et la méthodologie. Deux pistes
> nouvelles explorées : le **foncier du calcul (REIT data centers)** et les **monopoles/OEM « pas chers
> en pleine vue »** (TSMC, Dell, TE Connectivity).
>
> **Nouveauté v4 (27 mai).** Sept **poches encore sous-couvertes** ouvertes et chiffrées (§9) : **(I)** le
> *test/inspection du packaging avancé & probe cards* (Technoprobe — pure-play **européen** coté à Milan,
> FormFactor, Camtek, Photronics) ; **(J)** les *passifs & le mécanique du rack* (Nidec sur les
> ventilateurs/CDU **décoté de ~28 %**, MLCC Murata/TDK en pénurie) ; **(K)** l'*eau* — angle quasi ignoré
> (Veolia **~13–14× et français**, Xylem) ; **(L)** la *renaissance du stockage* (Seagate/WDC, mais
> **déjà très chers**) ; **(M)** les *semis de puissance SiC/GaN décotés/distressed* (onsemi ~23× avant,
> Power Integrations, Wolfspeed post-Chapter 11) ; **(N)** les *moteurs/gensets gaz de secours* (Wärtsilä) ;
> **(O)** les *services électriques & utilities régulées exposés à la charge data center* (SPIE **~16×
> français**, NKT 3ᵉ câblier EU, fusion **NextEra–Dominion 67 Md$ du 18 mai**, Southern, AEP). Objectif :
> dénicher des leviers **accessibles à un petit compte Euronext** (Veolia, SPIE, Technoprobe, Siltronic).
>
> **Nouveauté v5 (28 mai).** Six **poches encore absentes des v1–v4** ouvertes et chiffrées (§10) : **(P)** le
> *compute « stranded-power »* — les **mineurs de bitcoin reconvertis en hébergeurs IA/HPC** (IREN, Core
> Scientific, TeraWulf, Cipher, Hut 8, Applied Digital) avec >70 Md$ de contrats HPC signés ; **(Q)** les
> *superalliages & coulées de précision* qui **conditionnent la production de turbines à gaz** (Howmet, ATI,
> Carpenter, Haynes) — le goulet *derrière* le goulet énergie ; **(R)** les *pompes à vide & sous-systèmes
> de fab* (Ebara **CMP+vide+abatement**, Atlas Copco/Edwards) ; **(S)** les *consommables du packaging
> avancé* — **EMC, die-attach, slurry CMP, TIM** (Resonac **~11–12× avant**, Sumitomo Bakelite, Henkel,
> Indium) ; **(T)** les *céramiques techniques & chucks électrostatiques* (Ferrotec **~14× avant**, NGK
> Insulators **~18,7× + stockage réseau**, Kyocera) ; **(U)** le *stockage d'énergie data center (BESS /
> ride-through)* (Fluence, Bloom Energy — déjà cher, NGK NAS). Le vrai gisement *value* de la v5 sort sur
> **Resonac, Ferrotec et NGK Insulators** ; la poche mineurs-IA est un pari **fort levier / fort risque**
> (rôle « nouvellement critique » plus que multiple bas) ; superalliages = critiques **mais déjà chers**.
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

### ★ Poche G — Le **foncier du calcul** : REIT data centers (NOUVELLE v3)
Piste peu présente dans le débat « semis » : qui **possède et loue les murs** où tourne l'IA. Ces REIT
encaissent des baux longs indexés, profitent de la rareté de la puissance raccordée (un site « powered
shell » prêt à l'emploi vaut une prime), et se traitent à des multiples **plus raisonnables** que le
reste du complexe IA — plusieurs synthèses de mai 2026 les qualifient d'« attractivement valorisés ».

| Acteur | Ticker | Rôle | Angle de décote |
|---|---|---|---|
| **Digital Realty** | DLR | REIT data centers (wholesale + colocation, hyperscale) | Pré-loue sa capacité ; valorisation de REIT (FFO) bien sous les semis pour un même vent porteur. |
| **Equinix** | EQIX | Interconnexion / colocation premium (effet de réseau) | Douve « écosystème » (cross-connects) ; croissance AFFO régulière, multiple < complexe IA. |
| **Iron Mountain** | IRM | Stockage + data centers en forte croissance | Branche DC sous-appréciée derrière l'activité historique ; dérivée plus discrète. |
| **Prologis** | PLD | Logistique convertissant du foncier/énergie en data centers | Optionalité « power bank » foncière (sites raccordables) encore peu pricée. |

### ★ Poche H — Monopoles & OEM « **pas chers en pleine vue** » (NOUVELLE v3)
Le paradoxe de 2026 : certains des actifs **les plus critiques** se traitent à des multiples *modestes*
parce qu'ils sont mal classés (« fonderie cyclique », « OEM matériel à faible marge ») alors que leur
rôle est irremplaçable.

| Acteur | Ticker | Rôle (criticité) | Pourquoi « pas cher en pleine vue » |
|---|---|---|---|
| **TSMC** | TSM / 2330.TW | **Monopole de fait** de la fonderie ≤3 nm + CoWoS | PER ~28–29× : **le multiple le plus bas du sommet de la pile** pour l'acteur sans qui *rien* n'existe. |
| **Dell** | DELL | OEM serveurs IA (n°1 sur les racks GB200/300) | **PER avant ~13–14×** pour ~17 %/an de croissance de BPA : la « pelle » la moins chère de la liste. |
| **TE Connectivity** | TEL | Connecteurs/câblage haute densité (rack & réseau) | **PER avant ~19×** : le moins cher du groupe connecteurs/optique, exposition DC croissante. |

---

## 4 bis. ★ Tableau de valorisation chiffrée & indice de sous-évaluation (v3)

> **Méthodologie de l'indice (0–10).** Composite *qualitatif* combinant : (i) la **cherté du multiple
> rapportée à la croissance et aux pairs** (un PER de 50× pour +40 %/an est « moins cher » qu'un PER de
> 25× pour +5 %/an — logique PEG) ; (ii) le **degré de sous-possession / d'invisibilité** (moins le titre
> est « crowded », plus l'indice monte) ; (iii) la **criticité/douve** du rôle dans la pile. On *pénalise*
> les noms déjà ×2–×5 en 2026 et les multiples qui pricent l'exécution parfaite. **10 = très sous-évalué
> au regard de son rôle ; 0 = cher / pleinement pricé / spéculatif sans marge.**
>
> **⚠️ Avertissements.** (1) Les multiples sont des **instantanés d'agrégateurs publics** (GuruFocus,
> Yahoo Finance, stockanalysis, MarketScreener…) autour du **25 mai 2026**, arrondis, et **bougent vite**.
> (2) Le PER avant des semis **cycliques** (STM, Micron) peut paraître élevé/bas selon que le BPA est
> déprimé ou au pic — d'où le croisement avec EV/EBITDA et la croissance. (3) L'indice est un **jugement
> d'analyse, pas une recommandation** ; un indice élevé peut s'accompagner d'un **risque élevé** (signalé).

| Acteur | Ticker | Couche / rôle | PER avant (≈) | Autre repère | Indice 0–10 | Lecture |
|---|---|---|---:|---|:---:|---|
| **SK Hynix** | 000660.KS | Leader HBM | **~6×** (NTM) | Marge op. ~72 %, +198 % CA YoY | **9** | Pure-play HBM **le moins cher** ; décote « cyclique coréen » injustifiée |
| **Micron** | MU | HBM + DRAM/NAND | **~11×** | HBM « sold out » 2026 ; +70 % YTD | **8** | Qualité en solde ; multiple bas pour la croissance |
| **Energy Transfer** | ET | Gas-to-power midstream | **~11,5×** | **Rdt ~6,7 %**, MLP | **8** | Dérivée DC **la moins chère** ; 1er contrat off-grid (1,2 GW) |
| **Dell** | DELL | Serveurs/racks IA (OEM) | **~13–14×** | Croissance BPA ~17 %/an | **7** | La « pelle » la moins chère ; ⚠️ marges fines, mix |
| **AT&S** | ATS.VI | Substrats ABF | n.s. (pertes) | EV/ventes faible, deep value | **7** | Contrarian fort levier packaging ; ⚠️ dette/exécution |
| **Nexans** | NEX.PA | Câbles réseau/DC | **~20,5×** | EV/EBITDA ~11,9× | **7** | *Laggard* value vs Prysmian (fwd ~33,5×) ; **top pick EU** |
| **HD Hyundai Electric** | 267260.KS | Transfos pure-play | **~25–28×** | Marge op. 24–26 %, carnet ~7,9 Md$ | **7** | Levier transfo **le plus pur** ; capacité US fin 2026 |
| **TE Connectivity** | TEL | Connecteurs HD (rack/réseau) | **~19×** | — | **7** | Le moins cher du groupe connecteurs ; piste v3 |
| **Vistra** | VST | Nucléaire / IPP | **~18×** | EV/EBITDA ~10×, FCF yield élevé | **7** | Optionalité PPA nucléaire ; moins cher que CEG |
| **Kinder Morgan** | KMI | Gaz midstream (40 % du gaz US) | **~23×** | **Rdt ~3,5 %**, carnet >10 Md$ | **6** | Dérivée DC à multiple d'infra + rendement |
| **TSMC** | TSM | Fonderie ≤3 nm (monopole de fait) | **~28–29×** | — | **6** | **Monopole le moins cher du sommet de pile** |
| **Constellation Energy** | CEG | Nucléaire (PPA hyperscalers) | **~26×** | EV/EBITDA ~13× ; **-20 % YTD** | **6** | Repli = point d'entrée ; visibilité PPA 20 ans |
| **Digital Realty / Equinix** | DLR / EQIX | REIT data centers | — | Multiples FFO/AFFO < complexe IA | **6 / 5** | « Foncier du calcul » attractivement valorisé (piste v3) |
| **Disco** | 6146.T | Découpe/meulage (quasi-monopole) | **~43×** | **ROIC ~54 %, net cash** | **5** | Qualité rare ; prime largement justifiée → faible décote |
| **Infineon** | IFX.DE | Semis de puissance (800 VDC + auto) | **~37×** | — | **5** | Décoté vs pairs power US ; cyclique |
| **Air Liquide** | AI.PA | Gaz spéciaux fabs | **~24–25×** | **Rdt ~2,1 %**, payout ~60 % | **5** | Décote de **perception** (« défensive » ≠ « play IA ») |
| **STMicroelectronics** | STM.PA | Semis power/analog (liste 800 VDC) | **~47×** (BPA déprimé) | — | **5** | Optionalité **reprise cyclique** ; pas « cher » sur mid-cycle |
| **Talen Energy** | TLN | Nucléaire IPP (campus AWS) | n.d. | Special situation | **5** | Précédent data center nucléaire ; volatil |
| **VAT Group** | VACN.SW | Vannes à vide (quasi-monopole) | **~40×+** | — | **4** | Monopole réel, mais prime de qualité ; sur repli |
| **Advantest** | 6857.T | Test HBM/SoC (quasi-monopole) | **~53×** | — | **4** | Gagnant structurel **mais** cher ; sur repli |
| **Schneider Electric** | SU.PA | Power + cooling DC | **~28×** | — | **4** | Cœur de thèse, qualité, **pas donné** (détenu) |
| **Broadcom** | AVGO | ASIC (TPU/Maia) + réseau | **~37×** | ~médiane semis | **4** | Croissance forte ; multiple raisonnable pour la qualité |
| **ASML** | ASML.AS | Litho EUV (monopole) | **~56×** (TTM) | +55 % vs moyenne hist. | **4** | Monopole absolu **mais** cher vs son propre historique |
| **Delta Electronics** | 2308.TW | Alim/cooling (~60 % power shelf) | **~42×** | Marge brute record 37 % | **4** | Dominant ; déjà ~100 Md$ de cap. |
| **Williams** | WMB | Gas-to-power on-site | **~34,5×** | **Rdt ~2,7 %** | **4** | Re-raté « croissance » ; plus cher que ET/KMI |
| **Hanmi Semiconductor** | 042700.KS | TC bonder HBM (~71 %) | n.d. | — | **4** | Pure-play ; ⚠️ **risque de concentration client** (SK Hynix) |
| **Hammond Power** | HPS.A.TO | Transfos NA (n°1) | n.d. | **+111 % YTD**, carnet +122 % | **3** | Excellent mais **déjà couru** → repli seulement |
| **Prysmian** | PRY.MI | Câbles (leader mondial) | **~33,5×** | EV/EBITDA ~17,5× | **3** | A déjà couru vs Nexans |
| **Amphenol** | APH | Connecteurs HD | **~29×** | — | **3** | Qualité au juste prix |
| **Marvell** | MRVL | ASIC + optique | **~47–51×** | — | **3** | Riche pour le secteur |
| **Vertiv** | VRT | Power + cooling (leader) | **~48×** | EV/EBITDA ~51× ; carnet >15 Md$ | **3** | Carnet soutient mais multiple **élevé** → seulement sur capitulation |
| **Monolithic Power** | MPWR | VRM (~70 % sockets Rubin) | élevé | — | **3** | Qualité chère ; sur repli |
| **Lasertec** | 6920.T | Inspection masques EUV (>90 %) | **~44×** | Très volatil | **3** | Monopole réel **mais** volatil/cher → repli marqué |
| **GE Vernova** | GEV | Turbines + transfos (Prolec) | **~55–69×** | médiane indus. ~22× | **2** | Exécution parfaite déjà pricée ; acheter **gros** replis |
| **Siemens Energy** | ENR.DE | Turbines + réseau | **~69×** | post-turnaround | **2** | Re-rating spectaculaire déjà fait |
| **Corning** | GLW | Fibre / CPO (accord Meta 6 Md$) | **~55×** | — | **2** | Riche |
| **Navitas** | NVTS | GaN 800 VDC | **déficitaire** | PT consensus **< cours** | **2** | Pari pur 800 VDC ; **spéculatif**, pas « sous-évalué » |
| **Coherent** | COHR | Optique / CPO | **~159×** (CY26) | ~41× sur CY27 | **1** | Extrêmement cher ; tout le succès est pricé |
| **Lumentum** | LITE | Optique / CPO (EML 200G) | **~159×** | +1 269 % sur 1 an | **1** | Idem ; momentum extrême, marge d'erreur nulle |

> **Lecture du tableau.** Le **gisement net** (indice ≥ 7) se concentre sur : **la mémoire HBM mal-aimée**
> (SK Hynix, Micron), **le gaz-to-power et le réseau sous-possédés** (Energy Transfer, Nexans, HD Hyundai,
> Vistra), et **les « pelles » peu chères en pleine vue** (Dell, TE Connectivity, + TSMC à 6). À l'inverse,
> l'**optique/CPO et les turbines** (Coherent, Lumentum, GE Vernova, Siemens Energy, Corning) pricent déjà
> la perfection : indice ≤ 2, à n'aborder que sur **forte** correction. Les **monopoles japonais/suisses**
> (Advantest, Disco, VAT, Lasertec) sont des actifs superbes mais **pas des aubaines** aux cours actuels.

---

## 5. Lien avec le portefeuille actuel (≈ 1 017 €, eToro / Euronext)

| Ligne détenue | PER avant (≈) · indice | Lecture « infra IA » | Action suggérée |
|---|---|---|---|
| **Air Liquide (AI.PA)** | ~24–25× · **5** | **Vrai play infra IA sous-apprécié** : gaz spéciaux indispensables aux fabs (consommation ↑ avec chaque fab). Le marché la traite en « défensive » → décote de perception. | **Conserver** — réévaluer comme ligne IA, pas seulement défensive |
| **Schneider (SU.PA)** | ~28× · **4** | Cœur du goulet **power + cooling** data center (UPS, switchgear, Motivair, CDU). Thèse validée (meilleur performeur du portefeuille). | **Conserver / laisser courir** |
| **STMicro (STM.PA)** | ~47× (BPA déprimé) · **5** | Semi power/analog, **désormais sur la liste 800 VDC de Nvidia** → exposition renforcée à la bascule électrique des racks. Optionalité reprise cyclique. | Conserver dans la zone neutre (cf. carnet d'ordres) |
| **Sanofi (SAN.PA)** | ~12–13× · n/a | **Hors thème IA** — pure défensive/rendement. | Candidate à rotation si une idée infra à meilleur couple rendement/risque se présente |

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

## 8. Synthèse — et ce que disent les chiffres (v3)

> En 2026, l'argent « facile » sur Nvidia et les semis européens est fait ; le **rendement ajusté du
> risque** se trouve désormais (1) dans les **dislocations tactiques** de qualité (Micron, Vertiv,
> SK Hynix), (2) dans les **dérivées énergie/réseau/carburant sous-possédées** (Siemens Energy, Nexans,
> GE Vernova, HD Hyundai/Hammond sur les transfos, Williams/KMI/ET sur le gaz-to-power, nucléaire/
> uranium) et (3) dans les **oligopoles de composants invisibles** (Advantest, Disco, VAT, AT&S, et —
> avec prudence sur la concentration client — Hanmi sur le packaging HBM) — en achetant toujours
> **la faiblesse, pas la force**, et en gardant à l'esprit que la panique « OpenAI » de mai price
> un excès qui, sur la plupart des goulets, n'existe pas (offre contrainte, pas surcapacité).

**Ce que la couche chiffrée v3 ajoute :** une fois les multiples posés (§4 bis), trois constats tranchent
le récit. **(a)** La **mémoire HBM** est l'anomalie la plus criante — SK Hynix à **~6× les bénéfices** et
Micron à **~11×** sont des leaders à multiples de valeur, là où l'optique (Coherent/Lumentum **~159×**) et
les turbines (GE Vernova/Siemens Energy **~55–69×**) pricent l'exécution parfaite. **(b)** Le **gaz-to-power
le moins cher est Energy Transfer (~11,5×, ~6,7 % de rendement)**, pas Williams (~34×) déjà re-raté. **(c)**
Des actifs **critiques restent « pas chers en pleine vue »** par simple erreur de catégorie : **TSMC ~28×**
(monopole de fait), **Dell ~13–14×** (la pelle la moins chère), **TE Connectivity ~19×**. À l'opposé, les
**monopoles japonais/suisses** (Advantest ~53×, Disco ~43×, VAT >40×, Lasertec ~44×) sont superbes mais
**déjà bien valorisés** : on les veut sur repli, pas au cours. Le couple **criticité × décote** le plus
favorable (indice ≥ 7) pointe vers **SK Hynix, Micron, Energy Transfer, Nexans, HD Hyundai Electric,
Vistra, TE Connectivity, Dell** — sans jamais oublier que l'indice intègre un risque (cyclicité mémoire,
MLP, exécution AT&S, marges Dell) qu'il faut accepter sciemment.

---

## 9. ★★ Édition v4 (27 mai 2026) — sept poches encore sous-couvertes

> Le but de la v4 n'est pas de réécrire la thèse (intacte : *l'IA est d'abord un trade énergie + composants
> oligopolistiques, à acheter sur faiblesse*) mais d'**élargir le filet** vers des couches que les v1–v3
> n'avaient qu'effleurées, en privilégiant ce qui est **réellement accessible à un petit compte Euronext**.
> Chaque poche est chiffrée (PER avant / repère + indice 0–10, même méthodologie qu'en §4 bis).

### ★ Poche I — Test, inspection & *probe cards* du packaging avancé (le « contrôle qualité » de l'IA)
Chaque die logique, chaque pile HBM et — désormais — chaque assemblage 2.5D/3D doit être **sondé, inspecté
et mesuré**. Plus le packaging se complexifie (chiplets, hybrid bonding, jusqu'à ~500 M de micro-bumps),
plus ces étapes deviennent un goulet à forte valeur ajoutée. Couche distincte des *bonders* (Poche E) et du
test final (Advantest, Poche C) : ici c'est l'**interface puce↔testeur** et la **métrologie/inspection**.

| Acteur | Ticker | Rôle (criticité) | PER avant (≈) | Indice | Lecture / nuance |
|---|---|---|---:|:---:|---|
| **Technoprobe** | **TPRO.MI** (Milan) | **~60 % du haut de gamme des *probe cards* MEMS verticales** ; entre sur la *probe card* HBM (TAM ~600 M$ en 2028, ~20 % visé) | **~55×** (avant) ; trailing ~113× | **4** | **Pure-play coté en Europe** (Borsa Italiana, donc *accessible*). 3ᵉ relèvement de guidance en 13 mois, T1-26 record. **Mais cher** → sur repli, pas au cours. |
| **FormFactor** | FORM | Hégémon des *probe cards* logique avancée + leadership **HBM4 chiplet** | élevé (cyclique) | **4** | ASP en hausse avec HBM4 ; gagnant structurel US, valorisation tendue. |
| **Camtek** | CAMT | **Leader métrologie/inspection packaging avancé** (plateformes Hawk/Eagle G5 ; HBM, hybrid bonding) | **~25×** (avant) | **5** | >3 000 outils installés. Multiple avant raisonnable **si** l'accélération S2-26 se confirme ; sinon compression brutale (T1 en repli). |
| **Onto Innovation** | ONTO | Métrologie/inspection AP (lithographie packaging, *bumps*) | moyen-élevé | **4** | Concurrent direct de Camtek ; exposé au même cycle AP/HBM. |
| **Photronics** | PLAB | **Photomasques** (high-end IC, virage masques IA petits nœuds) | **~23×** (avant) ; **TTM ~14,5× vs pairs ~48×** | **6** | *Deep-reasonable value* sous le radar : décote massive vs pairs semis. ⚠️ capex ~330 M$ en 2026 (×2) pèse sur le BPA court terme. |

### ★ Poche J — Passifs, ventilateurs & mécanique du rack (le contenu « ennuyeux » qui explose au rack)
Chaque carte d'accélérateur et chaque rack 100 kW+ embarque **plus de MLCC, plus de ventilateurs, plus de
modules de puissance** que jamais. Marché japonais dominant, peu « crowded » côté narratif IA.

| Acteur | Ticker | Rôle | PER avant (≈) | Indice | Lecture / nuance |
|---|---|---|---:|:---:|---|
| **Nidec** | 6594.T (Tokyo) | **Ventilateurs serveurs IA** (8 modèles 38–120 mm) **+ chaîne liquid-cooling** (In-Rack/In-Row CDU, *cold plates*, manifolds, *quick disconnects*, prototype CDU spec Google OCP) + moteurs | **~11×** (avant) ; trailing ~19× | **7** | **Titre battu (~-28 % sur 1 an)** sur un *overhang* gouvernance (révélation d'irrégularités de contrôle qualité) qui masque une **exposition cooling IA pleine** → couple décote × criticité élevé. ⚠️ risque gouvernance à surveiller. |
| **Murata** | 6981.T (Tokyo) | **N°1 MLCC** ; demande MLCC serveurs IA **×3,3 d'ici FY30** ; hausses de prix 15–35 % (avr. 26) ; modules de puissance IA (¥50 Md visés FY27) | moyen (~20×) | **5** | Pénurie MLCC = *pricing power* retrouvé ; grande cap de qualité, pas une aubaine mais levier propre. |
| **TDK** | 6762.T (Tokyo) | MLCC + batteries (BBU de rack) | moyen | **4** | Même vent MLCC ; mix plus diversifié (batteries) → levier IA plus dilué. |

### ★ Poche K — L'**eau** : l'angle quasi totalement ignoré du marché
La demande d'eau (data centers + fabs + production électrique) pourrait croître de **~130 %**. Quasi personne
ne traite l'IA comme une *water story* — le rachat de CoolIT par Ecolab a commencé à réveiller le thème.
**Atout pour ce compte : Veolia est française, cotée à Paris, et bon marché.**

| Acteur | Ticker | Rôle | PER avant (≈) | Indice | Lecture |
|---|---|---|---:|:---:|---|
| **Veolia** | **VIE.PA** (Paris) | Gestion/traitement d'eau de refroidissement DC, monitoring continu, chimie des tours | **~13–14×** | **6** | **Le levier eau-IA le moins cher et le plus accessible (Euronext).** Thème naissant, décote « utility » → optionalité gratuite. |
| **Xylem** | XYL | Pompes, traitement, *metering* (eau côté DC) | **~23–25×** | **4** | Levier US plus pur sur l'eau, mais déjà valorisé comme une qualité industrielle. |
| **Ecolab** | ECL | Chimie de l'eau + **CoolIT** (direct-to-chip) | élevé | **3** | Intègre eau + cooling ; multiple premium déjà reconnu. |

### ★ Poche L — Renaissance du **stockage** (HDD nearline) — *intéressant mais déjà couru*
Toute donnée d'entraînement/inférence finit stockée à froid sur **HDD nearline**. Duopole STX/WDC
**vendu jusqu'à fin 2026** (POs fermes), prix +~50 %, transition HAMR. **Mais les titres ont déjà flambé.**

| Acteur | Ticker | Rôle | PER avant (≈) | Indice | Lecture |
|---|---|---|---:|:---:|---|
| **Seagate** | STX | HDD nearline (HAMR ; ~50 % des EB nearline en HAMR fin 2026) | **~52×** ; **+322 % vs moy. 10 ans** | **3** | Duopole + sold-out, **mais** valorisation au pic du cycle ; n'acheter que sur repli. |
| **Western Digital** | WDC | HDD nearline (+ NAND via SanDisk scindé) | **~47–48×** ; **+115 % YTD** | **3** | Idem : qualité du cycle reconnue, marge d'erreur faible désormais. |
| **Sandisk** | SNDK | NAND (scission WDC) | n.d. | **3** | Pure-play NAND ; levier cyclique mémoire de 2ᵉ ordre. |

### ★ Poche M — Semis de puissance **SiC/GaN** décotés / *distressed* (la bascule 800 VDC, côté value)
Complément « value » de la Poche D (v2). Le contenu *power* par rack **double** (GB200→Rubin : ×4 à ×11,5 ;
onsemi : 50 k$ → 100 k$ par rack 1 MW). Mais les BPA sont au **creux cyclique** → certains multiples *avant*
sont déjà raisonnables alors que le *trailing* paraît absurde.

| Acteur | Ticker | Rôle | PER avant (≈) | Indice | Lecture / nuance |
|---|---|---|---:|:---:|---|
| **onsemi** | ON | **SiC** (étage AC/DC → 800 VDC) ; contenu/rack ×2 | **~22–23×** (avant) ; trailing ~228× (BPA déprimé) | **6** | Levier **reprise cyclique + 800 VDC** ; le *forward* est déjà digeste pour un leader SiC. ⚠️ pari sur le creux du cycle. |
| **Power Integrations** | POWI | **PowiGaN 1250 V** pour 800 VDC (densité > SiC stacké) | élevé (~40×+) | **3** | Techno bien placée mais multiple riche ; momentum récent (+~18 %). |
| **Wolfspeed** | WOLF | Matériaux/modules **SiC** purs | n.s. (post-restructuration) | **2** | **Sortie de Chapter 11**, +~150 % en mai : *turnaround* hautement spéculatif, pas « sous-évalué » au sens fondamental. |

### ★ Poche N — Moteurs & *gensets* gaz : la puissance **off-grid** rapide
Faute de raccordement réseau, les moteurs alternatifs **gaz** (démarrage rapide, modulaires) deviennent une
solution de base/secours pour campus IA. Complément « machines » de la Poche F (midstream/gas-to-power).

| Acteur | Ticker | Rôle | PER avant (≈) | Indice | Lecture |
|---|---|---|---:|:---:|---|
| **Wärtsilä** | WRT1V.HE (Helsinki) | Moteurs gaz **50SG/34SG** pour centrales DC *off-grid* (790 MW Texas, 412 MW US, ~800 MW de commandes DC 2025) | **~25–28×** | **5** | Nouveau venu DC à traction rapide ; levier *flexible power* peu pricé. Coté Helsinki (accès courtier-dépendant). |
| **Cummins / Caterpillar / Generac** | CMI / CAT / GNRC | Groupes électrogènes de secours (diesel/gaz) | ~14–22× | **4** | Dérivées *backup power* de qualité ; CMI le moins cher, levier DC encore sous-apprécié. |

### ★ Poche O — Services électriques & **utilities régulées** exposés à la charge data center
Au-delà des IPP/nucléaire (v2 : CEG/VST/TLN), deux familles sous-couvertes : **(1)** l'**ingénierie
électrique multi-technique** (installation/maintenance des salles et raccordements) et **(2)** les
**utilities régulées** dont la base d'actifs gonfle avec la charge DC — à des multiples bien plus bas que
les semis. **Fait marquant v4 : fusion NextEra–Dominion (67 Md$, 18 mai 2026)** → 1ʳᵉ utility régulée
mondiale, *pipeline* de charge >130 GW ; Dominion +9 %, NextEra -4 % à l'annonce.

| Acteur | Ticker | Rôle | PER avant (≈) | Indice | Lecture |
|---|---|---|---:|:---:|---|
| **SPIE** | **SPIE.PA** (Paris) | **Services multi-techniques** énergie/élec. (installation, maintenance salles & raccordements DC) | **~16×** (avant) | **6** | **Français, accessible Euronext, bon marché** : levier *picks-and-shovels* sur le déploiement physique, peu « crowded ». |
| **NKT** | NKT.CO (Copenhague) | **3ᵉ câblier HT/HVDC européen** (carnet Transmission ~13,5 Md€, >95 % TSO européens) | **PER ~25–30×** (TTM, ~25 % > médiane) | **5** | Complète Nexans/Prysmian ; **mais** au plus-haut et au-dessus de sa médiane → moins décoté que Nexans. |
| **Southern Company** | SO | Utility régulée (8 GW contractés DC, *pipeline* >50 GW, +8 %/an) | **~20×** | **5** | Croissance de charge réglementée visible ; multiple d'utility. |
| **AEP** | AEP | Utility régulée (capex 5 ans 72 Md$, +33 %) | **~17–18×** | **5** | Forte base d'actifs en expansion ; levier transmission/DC peu cher. |
| **Dominion / NextEra** | D / NEE | Utilities régulées (fusion 67 Md$, *pipeline* >130 GW) | D ~17× / NEE ~19× | **5 / 4** | *Situation spéciale* fusion ; Dominion repricé à la hausse, NEE digère la dilution. |

### 9 bis. Tableau v4 trié par indice (nouveaux noms uniquement)

| Acteur | Ticker | Couche / rôle | PER avant (≈) | Indice | Accès Euronext ? |
|---|---|---|---:|:---:|:---:|
| **Nidec** | 6594.T | Ventilateurs + liquid cooling rack | **~11×** | **7** | Non (Tokyo) |
| **Photronics** | PLAB | Photomasques IA (deep value) | **~23×** (TTM ~14,5×) | **6** | Non (Nasdaq) |
| **Veolia** | **VIE.PA** | Eau de refroidissement DC | **~13–14×** | **6** | **Oui (Paris)** |
| **onsemi** | ON | SiC / 800 VDC (reprise cyclique) | **~22–23×** | **6** | Non (Nasdaq) |
| **SPIE** | **SPIE.PA** | Services électriques DC | **~16×** | **6** | **Oui (Paris)** |
| **Camtek** | CAMT | Inspection/métrologie packaging | **~25×** | **5** | Non (Nasdaq) |
| **Murata** | 6981.T | MLCC (pénurie, pricing power) | **~20×** | **5** | Non (Tokyo) |
| **Wärtsilä** | WRT1V.HE | Gensets gaz off-grid DC | **~25–28×** | **5** | Partiel (Helsinki) |
| **NKT** | NKT.CO | 3ᵉ câblier HVDC européen | **~25–30×** | **5** | Partiel (Copenhague) |
| **Southern / AEP** | SO / AEP | Utilities régulées charge DC | **~17–20×** | **5** | Non (NYSE) |
| **Technoprobe** | **TPRO.MI** | Probe cards MEMS (pure-play EU) | **~55×** | **4** | **Oui (Milan)** |
| **FormFactor** | FORM | Probe cards HBM4 | élevé | **4** | Non (Nasdaq) |
| **Xylem** | XYL | Eau côté DC | **~23–25×** | **4** | Non (NYSE) |
| **TDK** | 6762.T | MLCC + BBU | moyen | **4** | Non (Tokyo) |
| **Cummins** | CMI | Gensets de secours | **~14–16×** | **4** | Non (NYSE) |
| **Seagate / WDC** | STX / WDC | HDD nearline (sold-out **mais cher**) | **~47–52×** | **3** | Non (Nasdaq) |
| **Power Integrations** | POWI | GaN 800 VDC | **~40×+** | **3** | Non (Nasdaq) |
| **Ciena** | CIEN | DCI / optique cohérente ZR | **~82–111×** | **2** | Non (NYSE) |
| **Wolfspeed** | WOLF | SiC post-Chapter 11 (spéculatif) | n.s. | **2** | Non (NYSE) |

> **Lecture v4.** Le **meilleur couple décote × criticité parmi les nouveaux noms** sort sur **Nidec** (~11×,
> battu sur un sujet gouvernance qui masque une exposition cooling pleine), **Photronics** (photomasques IA
> à ~14,5× *trailing* vs pairs ~48×), **onsemi** (SiC à ~23× *avant*, pari reprise cyclique) et — surtout
> pour *ce* compte — deux **français bon marché et accessibles** : **Veolia (~13–14×, l'eau)** et
> **SPIE (~16×, services électriques DC)**. À l'inverse, **stockage (STX/WDC) et optique (Ciena)** ont déjà
> couru : on les classe « tard », pas « tôt ». **Technoprobe** est le pure-play *probe card* européen le plus
> élégant, mais **cher** (~55×) → à n'aborder que sur correction.

### 9 ter. Mise à jour du lien avec le portefeuille (candidats de rotation **réalistes**, Euronext)
La v4 ajoute **trois** idées directement exécutables sur un petit compte eToro/Euronext, au-delà de Nexans
(toujours *top pick* EU de la §5) :

1. **Veolia (VIE.PA)** — *l'eau de l'IA*, **~13–14×**, décote « utility » : optionalité thème naissant, prix
   d'action modeste. **Nouveau candidat phare value EU de la v4.**
2. **SPIE (SPIE.PA)** — services électriques/raccordements DC, **~16×**, *picks-and-shovels* peu « crowded ».
3. **Siltronic (WAF.DE)** *(cf. §0 Couche 0)* — wafers silicium en **creux cyclique** (pertes TTM, sous la
   valeur comptable) : *deep value* contrarian à fort levier de reprise, **risque élevé** (comme AT&S).
4. *(Watchlist, accès courtier-dépendant)* **Technoprobe (TPRO.MI)** sur repli, **NKT (NKT.CO)**, **Nidec
   (6594.T)**, **onsemi (ON)**, **Photronics (PLAB)**.

> Cohérence inchangée : **acheter la faiblesse, pas la force.** La v4 ne renie rien ; elle ajoute des
> leviers *value* et *accessibles* (eau, services élec., SiC en creux, passifs battus) là où la v3
> concluait que le « propre » se trouvait surtout dans la mémoire HBM, le gaz-to-power et les pelles peu
> chères en pleine vue.

---

## 10. ★★★ Édition v5 (28 mai 2026) — six poches inédites

> Même méthode (PER avant / repère + indice 0–10, cf. §4 bis), même discipline (« acheter la faiblesse,
> pas la force »). La v5 ne touche pas aux conclusions v1–v4 ; elle **complète la cartographie** sur des
> couches que les éditions précédentes n'avaient **pas du tout** abordées. Pour honorer l'objectif
> « *identifier tous les acteurs et tous les composants* », chaque poche précise **où elle se branche dans
> la pile** (renvoi §2). ⚠️ Multiples = instantanés d'agrégateurs publics (~26–28 mai 2026), arrondis.

### 10.0 — Rattachement à la cartographie (§2)
| Poche v5 | Couche de la pile (§2) | Nature du goulet ajouté |
|---|---|---|
| **P** — Mineurs BTC → IA/HPC | Couche 8 (cloud/capacité) + Couche 6a (énergie) | **Puissance déjà raccordée** revendue en compute — arbitrage *stranded power* |
| **Q** — Superalliages & coulées | Couche 0 (matériaux) → amont **Couche 6a** (turbines) | Le **goulet derrière le goulet énergie** : sans aubes/coulées, pas de turbine |
| **R** — Pompes à vide & sous-systèmes | Couche 1 (WFE) | Brique présente sur *chaque* outil dépôt/etch (comme VAT, en complément) |
| **S** — Consommables packaging avancé | Couche 0 + Couche 3 (packaging) | **EMC, die-attach, slurry CMP, TIM** — la *chimie* du 2.5D/3D & HBM |
| **T** — Céramiques & chucks ESC | Couche 1 (WFE, sous-systèmes) | Pièces critiques d'usure (chucks, quartz, *feedthroughs*) |
| **U** — Stockage d'énergie (BESS) | Couche 6a (énergie DC) | *Ride-through*, écrêtage, accès file d'interconnexion |

### ★ Poche P — Mineurs de bitcoin reconvertis en hébergeurs **IA / HPC** (le *compute « stranded-power »*)
La transformation la plus rapide de 2026 : des mineurs assis sur des **gigawatts déjà raccordés** (la
ressource la plus rare du secteur, cf. §1) convertissent leurs campus en hébergement GPU. **>70 Md$ de
contrats HPC** déjà signés sur le secteur coté ; pour IREN, Core Scientific et TeraWulf, le HPC vise **~70 %
du CA dès fin 2026**. Lecture clé : ce n'est **pas** une poche « multiple bas » — la plupart ont explosé —
mais une poche « **rôle nouvellement critique** » à fort bêta, où la *valeur d'option* tient au **backlog
contracté rapporté à l'EV** et à la puissance sécurisée, pas au PER.

| Acteur | Ticker | Rôle / atout | Valorisation (≈) | Indice | Lecture / **risque** |
|---|---|---|---:|:---:|---|
| **Core Scientific** | CORZ | Conversion Pecos (300 MW) ; ~590 MW contractés avec **CoreWeave** ; levée 3,3 Md$ | *Special situation* (saga rachat CoreWeave) | **5** | Le pari **événementiel** le plus pur ; ⚠️ issue de l'opération CoreWeave = binaire |
| **TeraWulf** | WULF | **Sortie totale du BTC en 2026** ; **12,8 Md$** de contrats HPC (Fluidstack/Core42) ; site Kentucky ~1 GW | EV/backlog faible | **5** | Pivot le plus « pure-play » ; ⚠️ exécution capex + dilution |
| **Cipher Mining** | CIFR | **9,3 Md$** de backlog (AWS 300 MW + Fluidstack *Google-backstopped*) | ~plus-haut historique | **4** | Backlog adossé à des contreparties solides ; ⚠️ déjà couru |
| **Applied Digital** | APLD | Hébergement HPC + neocloud (déjà §8) | Volatil | **4** | Levier capacité ; ⚠️ endettement, bêta extrême |
| **Hut 8** | HUT | Mix BTC + HPC, campus haute densité | ~plus-haut historique | **3** | Qualité de sites ; ⚠️ valorisation tendue |
| **IREN** | IREN | **Leader** : deal **Microsoft** ~1,94 Md$/an (≈85 % marge EBITDA projet), pipeline **4,5 GW**, extension **Dell** | **PER ~205×**, **+657 % sur 1 an** | **3** | Meilleurs fondamentaux du groupe **mais** déjà *priced for perfection* → repli seulement |

> **Lecture P.** Le couple risque/rendement le moins « déjà fait » se trouve sur les **situations
> spéciales / pivots purs** (Core Scientific, TeraWulf) où l'EV reste basse vs le backlog signé, **pas**
> sur IREN/Hut 8 qui ont déjà fait ×5–×7. **Poche entière = haut risque** (financement circulaire, bêta
> crypto résiduel, exécution capex) → taille de position spéculative uniquement, cohérente avec la §7.

### ★ Poche Q — Superalliages & **coulées de précision** : le goulet *derrière* la turbine à gaz
La v2 a identifié le gaz-to-power et les turbines (GE Vernova, Siemens Energy) ; mais ces turbines ne se
fabriquent pas sans **aubes, distributeurs et coulées en superalliages Ni/Co**. Le marché des superalliages
passe de **~10,2 Md$ (2026) à ~23 Md$ (2036)** ; le sous-marché *power generation* progresse explicitement
« *driven by data centers* », et la **chaîne de coulées (aubes/vanes) est le point d'étranglement** qui peut
retarder les plans d'expansion turbine. Couche 0 (matériaux) en amont direct de la Couche 6a.

| Acteur | Ticker | Rôle (criticité) | PER avant (≈) | Indice | Lecture / nuance |
|---|---|---|---:|:---:|---|
| **ATI Inc.** | ATI | Superalliages Ni/Co (aéro + **énergie/power-gen**) | **~36×** | **4** | Le **moins cher** du trio ; mix énergie en hausse, mais déjà bien valorisé |
| **Carpenter Technology** | CRS | Alliages spéciaux (aéro + énergie) | **~39×** (avant) ; trailing ~50× | **3** | Bénéfices records ; **~22 % au-dessus** de sa médiane 5 ans → cher |
| **Haynes International** | (privé/HAYN) | Superalliages haute température | n.d. | **3** | Pur acteur alliages ; accès/liquidité limités |
| **Howmet Aerospace** | HWM | **Coulées de précision** (aubes/vanes) — quasi-incontournable | **~55×** (avant) | **2** | Goulet réel des coulées **mais** exécution parfaite déjà pricée |

> **Lecture Q.** Poche **critique mais déjà découverte** : tout le trio s'est re-raté (HWM ~55×, CRS ~50×
> trailing). On la **cartographie** comme dérivée de 3ᵉ ordre du capex énergie, à n'aborder que sur **fort
> repli** ; **ATI** est le seul à un multiple « relativement » digeste pour qui veut le levier coulées/alliages.

### ★ Poche R — Pompes à vide & **sous-systèmes** de fab (le complément de VAT)
La v2 a posé VAT (vannes à vide, quasi-monopole). En amont/aval immédiat se trouvent les **pompes à vide
sèches**, l'**abatement de gaz** et le **CMP** — présents sur *chaque* chambre de dépôt/etch. Marché
oligopolistique, peu « crowded » côté narratif IA.

| Acteur | Ticker | Rôle | PER avant (≈) | Indice | Lecture |
|---|---|---|---:|:---:|---|
| **Ebara** | 6361.T (Tokyo) | **Pompes à vide sèches + CMP + abatement + plating** — large pan de la fab | **~26×** (norm.) ; certaines mesures ~34× | **5** | Étendue rare (vide *et* CMP *et* abatement) ; qualité japonaise sous-suivie ; PT relevés |
| **Atlas Copco** | ATCO-A.ST (Stockholm) | Propriétaire d'**Edwards Vacuum** (~5 Md$ de CA vide) + compresseurs | **~25,5×** (avant) | **4** | *Compounder* de qualité, levier semi via Edwards ; prime de qualité (~peers +28 %) |

> **Lecture R.** **Ebara** est le levier le plus *concentré* sur la fab (vide + CMP + abatement) à un
> multiple raisonnable pour la qualité ; **Atlas Copco** dilue le thème dans un conglomérat industriel mais
> apporte liquidité + accès européen (Stockholm). Ni l'un ni l'autre n'est une aubaine, mais tous deux
> complètent VAT/Comet (Poche C) sur le **vide & sous-systèmes**.

### ★ Poche S — Consommables du **packaging avancé** : EMC, die-attach, slurry CMP, **TIM**
Les v2/v4 ont couvert les *machines* (bonders, découpe, inspection). Manquait la **chimie consommable** qui
part à chaque wafer/boîtier 2.5D/3D et HBM : **epoxy molding compound (EMC)**, **films de die-attach**,
**slurry/pads CMP**, et **matériaux d'interface thermique (TIM)** côté refroidissement. Récurrent, à forte
marge, et indexé sur les *volumes* d'IA (pas seulement le capex).

| Acteur | Ticker | Rôle | PER (≈) | Indice | Lecture / nuance |
|---|---|---|---:|:---:|---|
| **Resonac** | 4004.T (Tokyo) | **Leader mondial EMC** + top-3 **slurry CMP** + die-attach ; matériaux AP **+~30 %/an** ; **spin-off pétrochimie (Crasus) 2026** = catalyseur de re-rating | **avant ~11–12×** ; trailing distordu (~15–104× selon source, *restructuration*) | **7** | **Meilleure trouvaille *value* de la v5** : leader des consommables AP/HBM à multiple *avant* bas + catalyseur de scission. ⚠️ trailing optiquement élevé (one-offs) ; cyclicité pétrochimie résiduelle |
| **Sumitomo Bakelite** | 4203.T (Tokyo) | EMC / résines d'encapsulation | moyen (~13–15×) | **5** | Co-leader EMC, value japonaise discrète ; mix plus large |
| **Indium Corp.** | (privé) | **TIM** haute perf. (indium, *liquid metal*) pour GPU/HBM | n.d. | n/a | Pure-play TIM mais **non coté** — à connaître, pas investissable en direct |
| **Henkel** | HEN3.DE (Francfort) | TIM + adhésifs « AI data center » (centre Bengaluru 2026) | **~16–17×** | **5** | Levier TIM **coté en Europe** dilué dans un grand groupe ; value raisonnable |
| **Honeywell / DuPont / Shin-Etsu** | HON / DD / 4063.T | TIM, *greases*, films | divers (~18–24×) | **3–4** | Exposition TIM réelle mais **diluée** ; pas des pure-plays |

> **Lecture S.** **Resonac** ressort comme le **vrai pari value** de la v5 (leader EMC/CMP, ~11–12× avant,
> catalyseur scission) ; **Sumitomo Bakelite** et **Henkel** complètent à moindre conviction. Le TIM le plus
> « pur » (**Indium Corp.**) n'est **pas coté** — utile à la cartographie, pas au portefeuille.

### ★ Poche T — **Céramiques techniques & chucks électrostatiques** (les pièces d'usure de la fab)
Sous-systèmes Couche 1 que les v1–v4 n'avaient pas isolés : **chucks électrostatiques (ESC)**, **quartz**,
**feedthroughs**, anneaux et pièces fines en céramique qui tiennent le wafer et s'**usent** (donc à
récurrence). Marché dominé par le Japon ; NGK Insulators + Shinko ≈ **>35 %** des ESC.

| Acteur | Ticker | Rôle | PER (≈) | Indice | Lecture / nuance |
|---|---|---|---:|:---:|---|
| **Ferrotec** | 6890.T (Tokyo) | ESC, quartz, *feedthroughs*, thermo-électrique ; consommables fab | **avant ~14×** ; trailing ~26× | **6** | *Value* la plus marquée de la poche ; large exposition consommables. ⚠️ **forte exposition Chine** (géopolitique) |
| **NGK Insulators** | 5333.T (Tokyo) | **Co-leader ESC** + **stockage réseau NAS** (batteries sodium-soufre) | **avant ~18,7×** ; trailing ~27× | **6** | **Double levier IA** (chucks *et* stockage réseau, cf. Poche U) à multiple modéré ; qualité sous-suivie |
| **Kyocera** | 6971.T (Tokyo) | Composants céramiques fab + condensateurs + connectique | **~30×** (trailing) | **4** | Très diversifié (levier IA dilué) ; **+46 % sur 1 an** → moins décoté |

> **Lecture T.** **Ferrotec (~14× avant)** et **NGK Insulators (~18,7× avant, + optionalité stockage
> réseau)** sont les deux **vraies idées value** de la poche ; **Kyocera** est de qualité mais trop
> diversifié et déjà couru. Ces noms complètent VAT/Comet/Ebara (Poches C & R) sur les **sous-systèmes
> consommables** — la partie « lames de rasoir » du WFE.

### ★ Poche U — **Stockage d'énergie** data center (BESS / *ride-through*)
Complément direct de la Couche 6a : entre le réseau saturé (transfos à 24–36 mois, §1) et les groupes de
secours, le **BESS** offre *ride-through*, écrêtage de pointe et **accélère la file d'interconnexion** (en
réduisant l'appel de puissance ferme). Thème naissant, déjà partiellement pricé sur les noms vedettes.

| Acteur | Ticker | Rôle | Valorisation (≈) | Indice | Lecture / **risque** |
|---|---|---|---:|:---:|---|
| **Fluence Energy** | FLNC | BESS *grid-scale* ; **MSA avec deux hyperscalers** (mai 2026), prises de commandes ×2 | rebond +78 % à l'annonce ; PT médian ~18,5 $ (9–28 $) | **4** | *Turnaround* + entrée DC réelle ; ⚠️ consensus « Hold », conformité FEOC, exécution |
| **NGK Insulators** | 5333.T | **Batteries NAS** longue durée (réseau / DC) | cf. Poche T (~18,7× avant) | **6** | **Levier BESS le moins cher**, adossé à une activité ESC rentable → double thèse |
| **Bloom Energy** | BE | Piles à combustible on-site (≠ BESS pur, mais *firm power* DC) | **~149× BPA**, **~27× ventes**, ~87× *book* | **1** | Croissance réelle (CA ×1,3 a/a) **mais** valorisation **spéculative** ; tout est pricé |
| **Tesla / Stem / EnerSys** | TSLA / STEM / ENS | Megapack / logiciel BESS / BBU | divers | **2–3** | Leviers indirects ; EnerSys (BBU/réseau) le plus « value » mais exposition DC diluée |

> **Lecture U.** Le levier BESS-IA le **moins cher** n'est pas un pure-play américain (Fluence déjà rebondi,
> Bloom déjà cher) mais **NGK Insulators** (Poche T) via ses **batteries NAS**, adossées à une base ESC
> profitable. **Bloom Energy** illustre le travers de 2026 : un excellent récit **entièrement pricé**.

### 10 bis. Tableau v5 trié par indice (nouveaux noms uniquement)
| Acteur | Ticker | Couche / rôle | PER (≈) | Indice | Accès Euronext ? |
|---|---|---|---:|:---:|:---:|
| **Resonac** | 4004.T | Consommables packaging (EMC/CMP/die-attach) | **avant ~11–12×** | **7** | Non (Tokyo) |
| **Ferrotec** | 6890.T | Chucks ESC / quartz / consommables fab | **avant ~14×** | **6** | Non (Tokyo) |
| **NGK Insulators** | 5333.T | ESC **+ stockage réseau NAS** (double levier) | **avant ~18,7×** | **6** | Non (Tokyo) |
| **Core Scientific** | CORZ | Mineur→IA (situation spéciale CoreWeave) | special situation | **5** | Non (Nasdaq) |
| **TeraWulf** | WULF | Mineur→IA pur (12,8 Md$ backlog) | EV/backlog bas | **5** | Non (Nasdaq) |
| **Ebara** | 6361.T | Pompes à vide + CMP + abatement | **~26×** (norm.) | **5** | Non (Tokyo) |
| **Sumitomo Bakelite** | 4203.T | EMC / résines | **~13–15×** | **5** | Non (Tokyo) |
| **Henkel** | HEN3.DE | TIM + adhésifs DC | **~16–17×** | **5** | **Oui (Francfort)** |
| **Daikin** | 6367.T | HVAC/chillers DC (unité dédiée 2026) | **~mid-20×** | **5** | Non (Tokyo) |
| **Cipher Mining** | CIFR | Mineur→IA (9,3 Md$ backlog) | ~ATH | **4** | Non (Nasdaq) |
| **Applied Digital** | APLD | Hébergement HPC / neocloud | volatil | **4** | Non (Nasdaq) |
| **ATI Inc.** | ATI | Superalliages énergie/aéro | **~36×** | **4** | Non (NYSE) |
| **Atlas Copco** | ATCO-A.ST | Vide (Edwards) + compresseurs | **~25,5×** | **4** | Partiel (Stockholm) |
| **Kyocera** | 6971.T | Céramiques fab (diversifié) | **~30×** | **4** | Non (Tokyo) |
| **Fluence** | FLNC | BESS grid-scale (MSA hyperscalers) | turnaround | **4** | Non (Nasdaq) |
| **IREN** | IREN | Mineur→IA leader (Microsoft/Dell) | **~205×**, +657 % | **3** | Non (Nasdaq) |
| **Hut 8** | HUT | Mineur→IA (ATH) | ~ATH | **3** | Non (Nasdaq) |
| **Carpenter Tech.** | CRS | Alliages spéciaux énergie/aéro | **~39×** | **3** | Non (NYSE) |
| **Keysight** | KEYS | Test/validation 800G/1.6T DC | **~40×** | **3** | Non (NYSE) |
| **Howmet** | HWM | Coulées de précision turbines | **~55×** | **2** | Non (NYSE) |
| **Bloom Energy** | BE | Firm power on-site (cher) | **~149× BPA** | **1** | Non (NYSE) |

> **Lecture v5.** Le **gisement net** (indice ≥ 6) tient en **trois noms japonais sous-suivis** : **Resonac**
> (consommables packaging à ~11–12× avant + catalyseur scission), **Ferrotec** (chucks/quartz à ~14× avant)
> et **NGK Insulators** (ESC + stockage réseau à ~18,7× avant). La poche **mineurs→IA** est la plus
> *spectaculaire* mais la moins « bon marché » (rôle nouvellement critique, fort bêta) : n'y aller qu'en
> taille spéculative, et plutôt sur les **pivots purs / situations spéciales** (Core Scientific, TeraWulf)
> que sur les têtes déjà ×5–×7 (IREN, Hut 8). Les **superalliages** (Howmet/CRS/ATI) sont **critiques mais
> déjà chers** ; on les retient surtout comme **cartographie** du goulet « derrière » les turbines.

### 10 ter. Mise à jour du lien avec le portefeuille (réalisme Euronext)
**Constat d'honnêteté :** la v5 est **pauvre en leviers directement exécutables sur Euronext** — l'essentiel
des trouvailles est à **Tokyo** (Resonac, Ferrotec, NGK, Ebara, Daikin) ou aux **États-Unis** (mineurs→IA,
superalliages). Pour *ce* compte (~1 017 €, eToro/Euronext), les candidats accessibles restent ceux des v3/v4 :

1. **Veolia (VIE.PA)** et **SPIE (SPIE.PA)** — toujours les **meilleurs leviers value français** (eau & services électriques DC).
2. **Nexans (NEX.PA)** — *top pick* EU inchangé (câbles réseau/DC).
3. **Henkel (HEN3.DE)** — *seule nouveauté v5 cotée en zone euro* : levier **TIM / adhésifs DC** à ~16–17×, accessible (Francfort), mais exposition IA **diluée** dans un grand groupe → conviction modérée.
4. *(Watchlist, accès courtier-dépendant)* **Resonac (4004.T)**, **Ferrotec (6890.T)**, **NGK Insulators (5333.T)** sur tout repli ; **Core Scientific / TeraWulf** en louche **spéculative** uniquement.

> Cohérence inchangée depuis v1 : **acheter la faiblesse, pas la force**, **offre contrainte ≠ surcapacité**,
> et **taille spéculative** pour les paris à fort bêta (mineurs→IA, BESS *turnaround*). La v5 élargit la
> *carte* (compute stranded-power, superalliages, vide, consommables packaging, céramiques, BESS) ; elle
> confirme que le **value « propre »** se loge désormais dans les **consommables japonais sous-suivis**
> (Resonac/Ferrotec/NGK) et que les couches énergie restent le fil conducteur de toute la thèse.

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

### v3 (26 mai 2026 — couche valorisation & indice de sous-évaluation)
- [SK Hynix: Trading At A Mere 7x With Growing HBM Complexity — Seeking Alpha](https://seekingalpha.com/article/4837148-sk-hynix-trading-7x-growing-hbm-complexity)
- [Micron Earnings Power Still Looks Undervalued at 10.7x Forward PE — Investing.com](https://www.investing.com/analysis/micron-earnings-power-still-looks-undervalued-at-107x-forward-pe-200676559)
- [Micron Technology: HBM Sold Out For 2026, Wall Street Is Still Underpricing — Seeking Alpha](https://seekingalpha.com/article/4881338-micron-technology-hbm-sold-out-for-2026-wall-street-is-still-underpricing)
- [Vertiv Holdings Co (VRT) Statistics & Valuation — stockanalysis.com](https://stockanalysis.com/stocks/vrt/statistics/)
- [GE Vernova Forward PE Ratio — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/GEV)
- [Constellation Energy vs. Vistra: Which Nuclear-Powered Utility Has More Upside? — TIKR](https://www.tikr.com/blog/constellation-energy-vs-vistra-which-nuclear-powered-utility-has-more-upside)
- [Nexans EV-to-EBITDA & valuation — GuruFocus / valueinvesting.io](https://valueinvesting.io/NEX.PA/valuation/ev_ebitda-multiples)
- [Prysmian S.p.A. Valuation Measures — Yahoo Finance](https://finance.yahoo.com/quote/PRY.MI/key-statistics/)
- [HD Hyundai Electric: 27.6% Margin, Backlog — MoatAlpha](https://moatalpha.com/hd-hyundai-electric-analysis-2026/)
- [Disco (TYO:6146) Statistics & Valuation Metrics — stockanalysis.com](https://stockanalysis.com/quote/tyo/6146/statistics/)
- [Advantest Corp (6857.T) Forward P/E — valueinvesting.io](https://valueinvesting.io/6857.T/metric/forward-pe)
- [Amphenol / TE Connectivity / Corning Forward PE — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/TEL)
- [Broadcom / Marvell / TSMC Forward PE — GuruFocus / Multiples.vc](https://www.gurufocus.com/term/forward-pe-ratio/AVGO)
- [Air Liquide Forward PE Ratio & Dividend — GuruFocus / stocksguide](https://www.gurufocus.com/term/forward-pe-ratio/AIQUY)
- [Williams / Kinder Morgan / Energy Transfer dividend & P/E — Morningstar / Dividend.com](https://www.morningstar.com/stocks/xnys/et/quote)
- [The Best Under-the-Radar AI Stocks to Buy in 2026 (Dell, DLR, EQIX) — Motley Fool](https://www.fool.com/investing/2026/05/10/the-best-under-the-radar-ai-stocks-to-buy-in-2026/)
- [Navitas Semiconductor (NVTS) Valuation — Simply Wall St](https://simplywall.st/stocks/us/semiconductors/nasdaq-nvts/navitas-semiconductor/news/a-look-at-navitas-semiconductor-nvts-valuation-after-ai-data)
- [Which Optics Stock Has Dominated in 2026 (Coherent ~159x) — 24/7 Wall St.](https://247wallst.com/investing/2026/05/12/which-optics-stock-has-dominated-in-2026-applied-optoelectronics-lumentum-or-coherent/)

### v4 (27 mai 2026 — sept poches sous-couvertes)
- [Technoprobe: The Hidden Picks-and-Shovels Monopoly of the AI Test Era — Crack the Market](https://crackthemarket.substack.com/p/technoprobe-the-hidden-picks-and)
- [UBS initiates Technoprobe coverage at "buy," highlights AI-driven growth — Investing.com](https://www.investing.com/news/stock-market-news/ubs-initiates-technoprobe-coverage-at-buy-highlights-aidriven-growth-4244392)
- [Technoprobe (BIT:TPRO) Statistics & Valuation Metrics — stockanalysis.com](https://stockanalysis.com/quote/bit/TPRO/statistics/)
- [The Testing Wall: FormFactor (FORM) in the HBM4 Era — FinancialContent](https://markets.financialcontent.com/stocks/article/finterra-2026-3-25-the-testing-wall-a-comprehensive-analysis-of-formfactor-inc-form-in-the-hbm4-era)
- [Camtek (CAMT): Navigating the HBM Cycle Reset / Advanced Packaging Dominance — AInvest](https://www.ainvest.com/news/camtek-nasdaq-camt-navigating-hbm-cycle-reset-capitalize-advanced-packaging-dominance-2506/)
- [CAMT (Camtek) Forward PE Ratio — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/CAMT)
- [Photronics: Growth Ahead At A Deeply Reasonable Price (PLAB) — Seeking Alpha](https://seekingalpha.com/article/4823960-photronics-growth-ahead-at-a-deeply-reasonable-price)
- [PLAB (Photronics) Forward PE Ratio — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/PLAB)
- [Nidec Launches Standard Fans for AI Servers (DigiKey, Mouser) — Nidec](https://www.mynewsdesk.com/us/nidec/pressreleases/nidec-launches-standard-fans-for-ai-servers-at-major-electronics-distributors-digikey-and-mouser-3421652)
- [Nidec Prototypes Project Deschutes CDU (Google OCP spec) — Nidec](https://www.nidec.com/en/product/news/2025/news1203-01/)
- [Nidec (TYO:6594) Statistics & Valuation Metrics — stockanalysis.com](https://stockanalysis.com/quote/tyo/6594/statistics/)
- [Murata Explores Raising Prices of Key AI Server Component (MLCC) — Bloomberg](https://www.bloomberg.com/news/articles/2026-02-17/murata-explores-raising-prices-of-ai-server-ceramic-capacitors)
- [Murata to Mass Produce AI Server Power Modules in 2026, Targets ¥50B by FY27 — TrendForce](https://www.trendforce.com/news/2025/12/17/news-murata-reportedly-to-mass-produce-ai-server-power-modules-in-2026-targets-%C2%A550b-by-fy27)
- [Data center water demand +130%; AI as a "water story" (Ecolab-CoolIT) — MarketWise](https://marketwise.com/investing/ai-data-center-cooling-stocks-ecolab-coolit/)
- [Veolia Environnement (VIE.PA) Forward P/E — valueinvesting.io](https://valueinvesting.io/VIE.PA/metric/forward-pe)
- [Efficient Data Center Cooling & Water Management — Xylem](https://www.xylem.com/en-us/markets/buildings-facilities/data-centers/)
- [Seagate and Western Digital: AI Storage Demand Shows Up in Pricing Power — 24/7 Wall St.](https://247wallst.com/investing/2026/05/16/seagate-and-western-digital-ai-storage-demand-is-now-showing-up-in-pricing-power/)
- [Western Digital Forward PE Ratio (47.61) — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/WDC)
- [Seagate Forward P/E (52.43) — valueinvesting.io](https://valueinvesting.io/STX/metric/forward-pe)
- [Power Semis in the AI Data Center (SiC/GaN, content per rack) — Tech Fund](https://www.techinvestments.io/p/power-semis-in-the-ai-data-center)
- [ON (ON Semiconductor) Forward PE Ratio (22.73) — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/ON)
- [Power Integrations 1250V/1700V PowiGaN for 800VDC AI data centers — Semiconductor Today](https://www.semiconductor-today.com/news_items/2025/oct/power-integrations-141025.shtml)
- [Wärtsilä 790 MW data center order in Texas (50SG engines) — Wärtsilä](https://www.wartsila.com/media/news/23-04-2026-wartsila-continues-to-expand-its-data-center-footprint-with-new-790-mw-order-in-texas-the-next-data-center-alley-3744599)
- [Part 1: Where Carel Sees Data Center Cooling Headed (CDU/immersion controls) — NaturalRefrigerants](https://naturalrefrigerants.com/news/part-1-after-decades-in-data-center-cooling-this-is-where-carel-sees-the-industry-headed-next/)
- [NextEra Energy to acquire Dominion ($67B), creating world's largest regulated utility — DataCenterDynamics](https://www.datacenterdynamics.com/en/news/nextera-energy-to-acquire-dominion-energy-creating-worlds-largest-regulated-electrical-utility/)
- [Combined NextEra-Dominion would have 130-GW large-load pipeline — Utility Dive](https://www.utilitydive.com/news/nextera-dominion-merger-would-create-worlds-largest-regulated-electric-ut/820457/)
- [NKT Shares Hit a Fresh High as Record Cable Orders Reframe the 2026 Story — TS2](https://ts2.tech/en/nkt-shares-hit-a-fresh-high-as-record-cable-orders-reframe-the-2026-story/)
- [FRA:NKT PE Ratio (TTM) 30.29 — GuruFocus](https://www.gurufocus.com/term/pettm/FRA:NKT)
- [SPIE (HAM:4SP) Forward PE Ratio (16.29) — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/HAM:4SP)
- [Switchgear, Cables, and Gensets: The Quiet Winners of the AI Data Center Boom — Medium/MH](https://medium.com/@_mh/switchgear-cables-and-gensets-the-quiet-winners-of-the-ai-data-center-boom-1c01bd41a67c)
- [Ciena Forward PE Ratio (82.53) — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/CIEN)
- [Semtech (SMTC) Valuation After OFC 2026 AI / Active Copper Interconnects — Webull](https://www.webull.com/news/14571612518491136)
- [Siltronic AG (WAF.DE) Valuation Measures — Yahoo Finance](https://finance.yahoo.com/quote/WAF.DE/key-statistics/)

### v5 (28 mai 2026 — six poches inédites)
- [Bitcoin miners pivot to AI and HPC as cryptocurrency market slumps — S&P Global Market Intelligence](https://www.spglobal.com/market-intelligence/en/news-insights/research/2026/02/bitcoin-miners-pivot-to-ai-and-hpc-as-cryptocurrency-market-slumps)
- [Bitcoin Miners Pivot to AI Data Centers: The 2026 Shift — Intellectia](https://intellectia.ai/blog/bitcoin-miners-ai-pivot-2026)
- [Bitcoin miners are becoming AI companies and selling BTC to fund the transition — CoinDesk](https://www.coindesk.com/markets/2026/03/27/bitcoin-miners-are-becoming-ai-companies-and-selling-their-btc-to-fund-the-transition)
- [Miners Beat Bitcoin by 70% in 2026 as TeraWulf Locks $12.8B in AI Contracts — Bitcoin.com](https://news.bitcoin.com/miners-beat-bitcoin-by-70-in-2026-as-terawulf-locks-12-8b-in-ai-contracts/)
- [From Bitcoin To AI: How IREN and Cipher Are Hosting Nvidia-Powered AI Workloads — Benzinga](https://www.benzinga.com/markets/tech/25/10/48082517/from-bitcoin-to-ai-how-iren-and-cipher-are-hosting-nvidia-powered-ai-workloads)
- [Bitcoin miner-to-AI boom sends stocks soaring as Cipher and Hut 8 hit fresh highs — The Block](https://www.theblock.co/post/402773/bitcoin-miner-ai-boom-stocks-soaring-cipher-hut-8-fresh-highs)
- [Core Scientific Q1 FY2026 earnings (Pecos conversion, CoreWeave) — SEC 8-K](https://www.sec.gov/Archives/edgar/data/0001839341/000162828026031246/q12026corescientificinc-ea.htm)
- [IREN (NASDAQ:IREN) Stock Analysis — Simply Wall St](https://simplywall.st/stocks/us/software/nasdaq-iren/iren)
- [Surging Gas Turbine Demand Fueled by Data Center, AI Growth — Turbomachinery Magazine](https://www.turbomachinerymag.com/view/surging-gas-turbine-demand-fueled-by-data-center-ai-growth)
- [Global Superalloys Market Trends 2036 (Carpenter, Haynes, ATI) — WFMZ](https://www.wfmz.com/online_features/press_releases/global-superalloys-market-trends-2036-carpenter-technology-corporation-haynes-international-driving-aerospace-demand/article_cd95c409-8bb9-57bb-8f6a-5c75fde8265e.html)
- [Howmet Aerospace (HWM) Statistics & Valuation — stockanalysis.com](https://stockanalysis.com/stocks/hwm/statistics/)
- [ATI Forward PE Ratio (36.02) — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/ATI)
- [Carpenter Technology (CRS) Statistics & Valuation — stockanalysis.com](https://stockanalysis.com/stocks/crs/statistics/)
- [Ebara Corp (6361) CMP / dry vacuum pumps / abatement — Ebara](https://www.ebara.com/global-en/precision/)
- [Ebara (TSE:6361) price target lifted to ¥3,944 — Simply Wall St](https://simplywall.st/stocks/jp/capital-goods/tse-6361/ebara-shares/news/earnings-update-heres-why-analysts-just-lifted-their-ebara-c)
- [Atlas Copco AB (ATCO A) Forward PE Ratio (25.54) — GuruFocus](https://www.gurufocus.com/term/forward-pe-ratio/OSTO:ATCO%20A)
- [Resonac advanced packaging materials (EMC, die-attach, CMP slurry) — Resonac](https://www.resonac.com/products/semi-backend-process/76)
- [Resonac Holdings (TYO:4004) Statistics & Valuation — stockanalysis.com](https://stockanalysis.com/quote/tyo/4004/statistics/)
- [Semiconductor TIM Market Led by Honeywell, DuPont, Indium, Shin-Etsu, Henkel — openPR](https://www.openpr.com/news/4122101/semiconductor-thermal-interface-materials-market-to-grow)
- [Material solutions for AI data center (TIM) — Henkel Adhesives](https://next.henkel-adhesives.com/us/en/brochures/material-solutions-for-ai-data-center.html)
- [Electrostatic Chucks: NGK Insulators + Shinko >35% share — IntelMarketResearch](https://www.intelmarketresearch.com/electrostatic-chuck-for-semiconductor-process-market-35590)
- [NGK Insulators (TYO:5333) Statistics & Valuation (fwd PE 18.71) — stockanalysis.com](https://stockanalysis.com/quote/tyo/5333/statistics/)
- [Ferrotec (TYO:6890) Statistics & Valuation (fwd PE 14.18) — stockanalysis.com](https://stockanalysis.com/quote/tyo/6890/statistics/)
- [Kyocera (TSE:6971) Valuation after 46% rally — Yahoo Finance / Simply Wall St](https://finance.yahoo.com/news/too-consider-kyocera-tse-6971-081316520.html)
- [Fluence signs master supply agreements with two hyperscalers — Utility Dive](https://www.utilitydive.com/news/fluence-energy-signs-master-supply-agreements-with-two-major-hyperscalers/820016/)
- [Fluence doubles order intake, expects first big data centre deal — Energy-Storage.News](https://www.energy-storage.news/fluence-doubles-order-intake-expects-to-book-first-big-data-centre-deal-this-quarter/)
- [Fluence Energy (FLNC) Stock Forecast & Price Target — MarketBeat](https://www.marketbeat.com/stocks/NASDAQ/FLNC/forecast/)
- [Bloom Energy: valuation up 1,400%, 149x EPS / 27x sales / 87x book — TIKR / Seeking Alpha](https://www.tikr.com/blog/bloom-energy-stock-is-up-over-1400-in-one-year-heres-what-the-valuation-says-now)
- [Keysight: The AI Infrastructure Winner Most Investors Miss (fwd PE 40.4x) — Seeking Alpha](https://seekingalpha.com/article/4902206-keysight-technologiesthe-ai-infrastructure-winner-most-investors-miss)
- [Daikin Applied Powering Data Centers for AI Era (modular plants) — ACHR News](https://www.achrnews.com/articles/165431-daikin-applied-powering-data-centers-for-ai-era)
