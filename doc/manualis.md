# Feladat: A felhasználói felület manuális tesztelése     

**A feladatot végezte:** Jakó Katinka 

**GitHub issue:** [GitHub issue link](https://github.com/BME-MIT-IET/iet-hf-2023-csapat1000/issues/19)  

## 1.) Feladat leírása
Mivel a program felhasználói felületét automatikusan nem sikerült tesztelni, a manuális tesztelés mellett döntöttünk. Az én feladatom volt ellenőrizni, hogy a program különböző funkcionalitásai az elvártnak megfelelően működnek-e. 

## 2) Feladat megvalósítása

### 2.1) A funkciók összegyűjtése
Első lépésként összegyújtöttem, hogy amikor a felhasználó interaktál a programmal, milyen elvárásai vannak a felhasználói felülettől. Ehhez figyelembe kellett venni a különböző helyzeteteket, mint például a játék elindítása, lépés másik mezőre, vagy különböző akciók végrehajtása a mezőkön. 

### 2.2) A tesztek megtervezése és végrehajtása
Ebben a részfeladatban összegyűjtöttem, hogy az egyes játékállapotokat hogyan lehet kiváltani: milyen helyzetben, hova kell kattintani. Miután megterveztem a tesztek végrehajtásának folyamatát, manuálisan el is végeztem azokta. Eközben képernyőképeket készítettem, amelyeket felhasználtam a dokumentáció készítése során.

Külön kezeltem azt az esetet, amikor az egyik játékos megnyeri a játékot. Ehhez nem manuálisan, a GUI-n kattintva jutottam el a kívánt játékállásba, hanem úgy váltottam ki ezt a helyzetet, hogy átírtam a kódban a getWinner() függvényt. 

``` 
    End();
    return map.players.get(0);
```

### 2.4) A teszteredmények dokumentálása
A tesztek leírását, a végrehajtási tervet és a végrehajtás eredményét egy dokumentációban rögzítettem. Beillesztettem a releváns képernyőképeket, és megvizsgáltam, hogy a felhasználói felület az elvártnak megfelelően viselkedett-e.

### 2.4) Eredmények, további teendők
A manuális tesztelés során a felhasználói felület mindent fontosabb funkcióját sikerült megvizsgálnom. A különböző UI elemek megfelelően működtek. Ugyanakkor megfigyeltem a felhasználói felület azon hiányosságát, hogy jelentősen lassabban reagál a különböző eseményekre, mint azt egy átlagos felhasználó elvárná.