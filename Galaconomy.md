# Design Document (CS)
(Unstructured draft v.2020-07-18)

## Základní teze
* Budovatelská a obchodní strategie v prostředí vzdálené galaxie. 
* Základem je výroba zboží a/nebo jeho distribuce.
* Hra má pouze jednoduchou grafiku v 2d pohledu.
* Těžištěm dění jsou planety (asteroidy, vesmírné základny), na kterých se staví výrobní, skladovací a spotřební budovy. Planety jsou umístěny v rámci hvězdných systémů, které dohromady tvoří galaxii.
* V rámci systému lodě cestují „klasickým“ podsvětelným pohonem, mezi systémy se pohybují tzv. „riftovým pohonem“, který probíhá buď mezi dvěma pevnými branami nebo ze speciálního portálu do libovolného cílového systému (souřadnice cílového místa v rámci systému jsou pevně dány).
* Hra se odehrává v nově kolonizované galaxii, do které začalo lidstvo nedávno pronikat.
* Existuje „centrální moc“ GLS (Galactic League Service), která nabízí základní úroveň přepravních a skladovacích služeb. Vydělává poplatky z každé transakce + provozem svých zařízení (riftové brány a portály). Z vydělaných peněz rozšiřuje svou infrastrukturu. Dynamicky utlumuje aktivitu tam, kde operují soukromé subjekty. V případě problémů může být dotována skrz dodávky z mateřské galaxie. Taková dodávka je herní událostí.
* Galaxie je s mateřským světem („Galaktická Liga“) spojena pouze jednou intergalaktickou „mega bránou“ z centrálního systému „Sicopia“. Tato brána je obsluhována NPC službou, lze odesílat a nakupovat zboží, tamní cenotvorba je nezávislá a nepodléhá vnitřní dynamice herní ekonomiky. Její změny jsou herní událostí.
* Ve hře se vyskytuje množství AI, které sledují své cíle dle nastavení. Existuje mezi nimi specializace (těžaři, výrobci, přepravci, investoři).
* Hra má expanzivní charakter – postupně jsou zpřístupňovány nové systémy, když je GLS prohlásí za otevřené. Otevření nových systémů je herní událostí.
* Hráč se může zapojit buďto úplně od začátku nebo po nějaké době od založení prvních kolonii (mezitím hrají AI).
* Těžba – probíhá na planetách, vyžaduje továrnu + planeta musí na daném segmentu mapy mít zásoby dané suroviny.
* Výroba – probíhá na planetách, vyžaduje továrnu a příslušné vstupní suroviny.
* Provoz budov vyžaduje energii – další speciální typ výrobní budovy. Energii lze skladovat převážet v podobě baterií (energy cells), které slouží rovněž pro pohon lodí.
* Zboží musí být uskladněno. Menší skladové kapacity mají jednotlivé továrny, lze stavět samostatné skladovací budovy. Skladovací kapacita je obchodovatelná služba. Továrna, která nemá kam ukládat, nemůže pokračovat ve výrobě.
* Planety mají svou vlastní NPC poptávku odvislou od velikosti populace na povrchu. Výkupní cena se mění dynamicky podle dostupné nabídky.

## Mapa
* Mapa celé galaxie – zobrazuje jednotlivé systémy, přímá propojení mezi nimi a lodě aktuálně cestující riftovým pohonem
* Mapa systému – zobrazuje objekty v rámci systému (centrální hvězda + planety/asteroidy + riftové brány)
* Mapa planety – zobrazuje budovy na povrchu planety 

## Objekty na mapě
*Mapa galaxie
** Zobrazuje jednotlové hvězdné systémy v 2d pohledu
** Mezi systémy lodě cestují riftovým pohonem, rychlost přesunu závisí na úrovni jejich motorů
* Hvězdný systém (**Star**)
** Zobrazen na hlavní galaktické mapě
** Obsahuje
*** 0-n přístupných planet/asteroidů/základen
*** 1-n dalších objektů (StellarObject), které mají pouze doplňkovou funkci, obvykle je jeden "centrální" objekt (mateřská hvězda)
*** 1-n rifových bran (přímé propojení na jiné systémy, manipulační poplatek)
*** 0-1 riftový portál (možnost volného skoku kamkoliv za vyšší poplatek)
*** právě jeden výstup z riftového portálu (při skoku pomocí portálu z jiného systému)
** Lodě v rámci systému se pohybují podsvětelnou rychlostí dle výkonu svých motorů mezi jednotlivými zájmovými body. Systém mohou opustit pouze riftovou branou nebo portálem.
* Planeta/asteroid/základna (**Base**)
** Dostupná rozkliknutím ze systému, ve kterém se nachází
** Část ve středu zabírá GLS, která vždy provozuje ubytovací kapacity pro obyvatele a kosmoport.
** Poptávka po zboží je určena počtem obyvatel + lokálními modifikátory + aktuální dostupností zboží
** Obsahuje n stavebních parcel pro budovy
** Stavební parcela
*** Je vlastněna GLS, dokud ji neodkoupí hráč/firma
*** Umožňuje postavit právě jednu budou libovolného typu
*** Obsahuje zásoby surovin, které nejsou předem známé, jejich množství se odkrývá pomocí průzkumu. Informace o surovinách ovlivňují cenu.
*** Má koeficient úrodnosti pro produkci potravin.

## Budovy
* Zabírají právě 1 místo na povrchu planety/asteroidu/základny
* Mají dáno, kolik zabírají nákladového prostoru lodí (velké lodi mohou budovy přenášet mezi planetami a systémy)
* Plní svou funkci podle typu
* Jednou postavenou budovu lze za poplatek přesunout jinam, pokud je dostupná transportní kapacita
* Budovu lze zrecyklovat na suroviny (menší množství, než stojí výroba)
* Budovu lze jako celek prodat
* Typy budov
** Továrna (Factory)
*** Může buďto produkovat primární surovinu (těžit) nebo zpracovávat 1-n vstupů na vybraný výstup. 
*** Je univerzální a její specializaci lze za určitý poplatek kdykoliv měnit. 
*** Těžit surovinu lze, pokud se nachází na parcele a dokud je jí > 0.
*** Speciální "surovinou" jsou (přírodní) potraviny, které lze produkovat kdykoliv ovšem v závislosti na koeficientu úrodnosti parcely.
*** Továrna má interní sklady na vstupní a výstupní suroviny, které lze za poplatek do učité míry navyšovat. Nemá-li kam ukládat výstupy, nemůže vyrábět.
** Sklad (Warehause)
*** Umožňují ukládat zboží.
*** Přesun zobží v rámci jedné planety nevyžaduje další prostředky, je-li k dispozici skladovací kapacita (vlastní nebo nasmlouvaná).
** Elektrárna (Generator)
*** Vyrábí energii
*** V rámci jedné planety nevyžaduje napojení další prostředky
*** Volnou energii lze konvertovat na energetické články, je-li k dispozici skladovací kapacita

## Firma
* Podíl ve firmě reprezentují akcie. Akcie dává nárok na výplatu podílu ze zisku a hlasovací právo ve strategických věcech
* Akcie se volně prodávají na trhu
* Rozhodnutí akcionářů – potřeba je nadpoloviční většina hlasů:
** Výše dividend (% ze zisku po zdanění)
** Změna počtu akcií
*** Nový úpis X akcií (a z toho plynoucí změna struktury)
*** Odkup akcií firmou (a jejich zánik)
*** Změna počtu při zachování vlastnických podílů
** Jmenování a odvolání ředitele (hráč nebo NPC)
** Určení platu ředitele
** Určení cílů (zisk, jiné faktory)
** Schválení rozpočtu
** Mimořádné žádosti ředitele (změna rozpočtu)
* Pro založení firmy je třeba stanovit základní cenu za úpis 1 akcie a jejich počet. Poté následuje úvodní prodej. Zakladatel se může rozhodnout zakoupit všechny sám na sebe nebo je nabídne k odkupu veřejně. Peníze za úpis pak představují vstupní kapitál firmy. Firmu nelze založit bez upsání všech akcií.
* Ředitel firmy se rozhoduje volně, ale musí se řídit schváleným rozpočtem, o potřebné změny mimořádně žádat. Pokud neplní stanovené cíle, může vyvolat nespokojenost akcionářů a ti ho mohou odvolat. Za práci ředitele mu náleží plat. Hráč může být ředitelem více firem najednou.
* Pokud hráč vlastní >50 % akcií, může jmenovat sám sebe ředitelem a nemusí se příliš starat (nespokojenost minoritních akcionářů ale může mít vliv na cenu akcií firmy) 
* Firma má svůj vlastní majetek a operace.
* Hodnotu firmy udává suma majetku (minus ten, co je zatížen úvěry). Hráčům se do hodnocení počítá podíl ve firmě podle množství akcií. Dále se počítá aktuální tržní cena akcie (pokud není obchodována, je rovna její úpisové ceně). Podíl z dividend je jednorázovým příjmem do osobního majetku hráčů.

## Hráč
* Začíná jako nový kolonista se sumou peněz, kterou GLS přiděluje do začátku
* Může založit první osobní firmu (doporučeno) nebo zakoupit podíl(y) v jiných nebo se ucházet o vedení nějaké z firem. Další rozvoj je na něm a jeho šikovnosti.
* Disponuje osobním finančním majetkem, se kterým může spekulovat na akciových či komoditních burzách, nebo si kupovat osobní majetek.
* Cílem je pochopitelně vydělat co nejvíc peněz.
* Do celkového skóre se počítá každý vydělaný kredit, druhým kritériem hodnocení je okamžité bohatství.
