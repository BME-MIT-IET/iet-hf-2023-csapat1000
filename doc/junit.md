# Feladat: Egységtesztelés (JUnit5)

**A feladatot végezte:** Kovács Judit

**GitHub issue:** [GitHub issue link](https://github.com/BME-MIT-IET/iet-hf-2023-csapat1000/issues/5)


---

## 1.) Feladat leírása

A feladatom egységtesztek (unit) tesztek készítése volt. Ehhez a JUnit5-öt használtam amit hozzá kellett adnom
a pom.xml fájlhoz, hogy használni tudjam.

---

## 2) Feladat megvalósítása

A legnehezebb feladatnak a tesztesetek kitalálását találtam. Próbáltam sok osztályt tesztelni, de végül csak a 
felszereléseket, az anyagokat és a mezők és a játék egy részét tudtam. 

Azért volt nehéz megírni ezekhez a teszteket, mert sokszor azt vettem észre, hogy a view és a model egybe 
van ágyazva és a működés belül történik. Sok olyan osztályt találtam, ahol nem voltak getterek vagy setterek írva
és minden változás csak privát adattagokban lett volna nyomonkövethető. Ezeket még mock-olni sem tudtam sok esetben,
mert nem nem tudtam injektálni a függőségeket, így sokat kellett volna módosítanom az eredeti projekten, hogy 
jó teszteket tudjak írni.

---

## 3) Eredmények, további teendők

A unit tesztekkel nagyjából 20-40% lefedettséget tudtam elérni ahol tudtam teszteket írni.