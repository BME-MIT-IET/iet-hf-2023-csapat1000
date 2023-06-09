# Csapat1000 - IET házi feladat összefoglaló

## Csapattagok:
A csapat1000 tagjai:
- Bárány Kristóf Zsolt 
- Jakó Katinka
- Kolozsvári Lilla
- Kovács Judit
- Kovács Luca Ágota

---

## Megvalósított feladatok
A tárgy honlapján található feladatok közül vastaggal jelöltük azokat amelyeket megvalósítottunk a házi feladat elvégzése során:

> Technológia fókusz 
> 
> - **Build keretrendszer beüzemelése, ha még nincs (Maven, Gradle...) + CI beüzemelése, ha még nincs (Actions, Travis, AppVeyor, Azure Pipelines...)**
> - **Manuális kód átvizsgálás elvégzése az alkalmazás egy részére (GitHub, Gerrit...) + Statikus analízis eszköz futtatása és jelzett hibák átnézése (SonarCloud, SpotBugs, VS Code Analyzer, Codacy, Coverity Scan...). Mivel az eszközök rengeteg hibát és figyelmeztetést találhatnak, ezért elég azok egy részét megvizsgálni és ha a csapat minden tagja egyetért vele, akkor javítani. Törekedjetek arra, hogy különböző típusú, és lehetőleg nem triviális hibajelzéseket vizsgáljatok meg.**  
> - Deployment segítése (Docker, Vagrant, felhő szolgáltatásba telepítés, ha értelmes az adott alkalmazás esetén...)  
> - **Egységtesztek készítése/kiegészítése (xUnit...) + tesztek kódlefedettségének mérése és ez alapján tesztkészlet bővítése (JaCoCo, OpenCover, Coveralls, Codecov.io...)**  
> 
> Termék/felhasználó fókusz  
> - **Nem-funkcionális jellemzők vizsgálata (teljesítmény, stresszteszt, biztonság, használhatóság...)**  
> - UI tesztek készítése (Selenium, Tosca, Appium...)  
> - BDD tesztek készítése (Cucumber, Specflow...)  
> - **Manuális tesztek megtervezése, végrehajtása és dokumentálása vagy exploratory testing**

---

## Feladatok felosztása
Az feladatokat az alábbi módon osztottuk fel:

- ```Bárány Kristóf Zsolt```: Nem-funkcionális jellemzők vizsgálata (stresszteszt, JMeter)
- ```Jakó Katinka```: Manuális tesztek megtervezése, végrehajtása és dokumentálása
- ```Kolozsvári Lilla```: Statikus analízis eszköz futtatása és jelzett hibák átnézése (SpotBugs, CheckStyle)
- ```Kovács Judit```: Egységtesztek készítése/kiegészítése (JUnit5) + tesztek kódlefedettségének mérése és ez alapján tesztkészlet bővítése (JaCoCo)
- ```Kovács Luca Ágota```: Build keretrendszer beüzemelése, ha még nincs (Maven) + CI beüzemelése, ha még nincs (Actions), kód review


## Tapasztalatok, észrevételek

### Kovács Luca Ágota
Nekem nagyon tetszett ez a házi feladat, mert sok házi feladattal ellentétben nagyon gyakorlatiasnak éreztem. Úgy gondolom, hogy azoknak az eszközöknek, amiknek itt utána kellett nézni és ki kellett próbálni máshol nagy hasznát fogom venni. Tetszett, hogy csoportos volt a házi, mert így tudtam gyakorolni hogy kell a Git/GitHub-ot magasabb szinten használni és ezáltal együttműködni másokkal.

Bár a mi választásunk volt, de az a része nem tetszett a házinak, hogy a projekt laboratórium keretein belül elkészített projekten kellett dolgozni. Ezt azért gondolom így, mert minden csapatnak nagyon feszes volt anno a határidő emiatt sokszor születtek kényszermegoldások amik most nehézzé tették a hibák javítását.

### Jakó Katinka
Mivel  főleg a felhasználói felület tesztelésével foglalkoztam, több programot is megismertem, melyek használhatóak ennek automatizálására. És bár ezt ennél a házi feladatnál nem tudtam végül hasznosítani, de örülök, hogy betekintést nyerhettem abba, hogyan működök az ilyen jellegű tesztelés. 

Jobb lett volna, ha egy webes vagy mobil alkalmazást választunk tesztelésre, mert valószínűleg több eszköz állt volna rendelkezésünkre. A Java Swinget nem valami egyszerű UI tesztelni, ezen kívül magában a konkrét projektben, amit vizsgáltunk, nem voltak megfelelően szétosztva a felelősségek (a felhasználói felület működésébe bele volt égetbe a játék logikája).

A csapat nagyon konstruktívan tudott együttműködik. Zökkenőmentes volt a kommunikáció, és hatékonyan tudtunk haladni a feladatokkal, akár közös, akár egyéni munkáról volt szó.

### Bárány Kristóf Zsolt
A stressztesztelés egy eddig számomra ismeretlen része volt a szoftverfejlesztésnek. Igaz, hogy nagyrészt web-es alkalmazások tesztelésénél elterjedt művelet, de a Java-ban írt játékprogram kontextusában is tudtam alkalmazni néhány elemét.

A játékprogram nem volt eléggé OO ezért kicsit nehéz volt megvalósítani a tesztesetet, aminek segítségével elvégeztem a stressztesztelést. Végül ezt megoldottam, de fölösleges időbe telt, ahogy a stresszteszteléshez használt program működésének felépítése is.

Csapatommal viszont teljes mértékben meg voltam elégedve, az első perctől kezdve profi módon állt hozzá mindenki a házifeladat elkészítéséhez. Szétosztottuk egyenlő részekre a feladatot, de bármikor segítségre volt szüksége egy csapattagnak, ott volt mindig valaki.

### Kovács Judit
A házi feladat megoldása során a unit tesztekkel és a kódlefedettséggel foglalkoztam. A tesztek írása során jöttem rá, hogy mennyire fontos az, hogy egy program jól megtervezett és OOP elveknek esetleg design pattern-eknek megfelelő legyen. Mivel a mi projlab házinkkal dolgoztunk, sokszor szembesültem azzal, hogy mennyi mindent elrontottunk anno és hogy sokkal jobban meg lehetett volna csinálni nagyon sok dolgot.

A legnagyobb tanulság az volt számomra, hogy egyáltalán nem egyszerű jó teszteseteket kitalálni és emiatt nagyon sok idő ment el ötletelésre, amiből sokszor nem is lett teszteset.

Nagyon szerettem együtt dolgozni a csapattal, mert jól szét tudtuk osztani a feladatokat és jól tudtunk egymással kommunikálni. Amikor valaki elakadt valamiben, akkor segítettük egymást, emellett végig úgy éreztem, hogy mindenki nagyon sok energiát fektetett a háziba.

### Kolozsvári Lilla
A házi feladat megoldása során a kód statikus ellenőrzését végeztem. Sok olyan pluginnak néztem utána, amit eddig nem ismertem, és nagyon hasznosnak találtam őket. A jövőben is alkalmazni fogom ezeket, hogy az általam írt kódok minőségét magasabb szintre emeljem.

A SpotBugs és a CheckStyle pluginok nagyon sok hibát találtak a kódbázisban, emiatt nem volt lehetőségem az összes javítására, a nagyobb hibákat viszont sikeresen ki tudtam küszöbölni.

Jó volt ismételten csapatban dolgozni, örülök, hogy új ismeretségeket tudtam szerezni. A többiek nagyon segítőkészek voltak, ha valaki elakadt, és nagyon jó volt közösen összeülni ötletelni a feladaton.

---

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/coREwzrI)
