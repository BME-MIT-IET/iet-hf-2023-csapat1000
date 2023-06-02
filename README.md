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

---

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/coREwzrI)
