package controller;


import view.Window;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game extends Thread {
    //egy játéktérképet tárol
    private final Map map;
    //és az összes megszerezhető genetikai kódot a játékban
    private final List<Code> allCodes;


    /**
     * A Game osztály konstruktora
     * inicializalasok tortennek benne
     * @param jatekosok paraméterként kapja, hogy hány játékos fogja játszani a játékot
     */
    public Game(int jatekosok) {
        super();
        //létrehoz, egy 6x6-os játéktérképet, és ugyanekkor paraméterül adja tovabb a jatekosok szamat
        map = new Map(6, jatekosok);
        //tárolja az összes megszerezhető genetikai kódot a játékban
        allCodes = new ArrayList<>();
        //ezeket egyesevel hozzaadja
        allCodes.add(map.getChoreaCode());
        allCodes.add(map.getOblivionCode());
        allCodes.add(map.getShieldCode());
        allCodes.add(map.getParalyzeCode());
        //az elejen az aktiv jatekos a lista elejen szereplo jatekos
        map.setCurrentPlayer(map.players.get(0));

    }

    /**
     * A terkep gettere
     * @return visszaadja a jatekterkepet
     */
    public Map getMap() {
        return map;
    }

    /**
     * A jatekot kulon szalon futtato metodus, hogy ne blokkoljon a varakozasaval senkit.
     * Akkor van vege, ha van gyoztes.
     * Threadkent indithato, hogy varakozhasson nyugodtan.
     */
    public void run() {
        Player winner = null;
        //a jatek addig fut, amig nem lesz egy gyoztesunk
        while (winner == null) {
            //minden korben az elso feladat, hogy lepjen egyet a jatekos, helyben maradas nem opcio
            Window.get().setInfo("Valassz egy szomszedos mezot, amire lepni szeretnel");
            Field lepett = null;
            if (map.getCurrentPlayer().ParalyzedFor()>0) { lepett = map.getCurrentPlayer().GetField(); }
            while (lepett == null) {
                lepett = getField();
                // Itt kene hogy booleant csinalni
                if (!map.getCurrentPlayer().Move(lepett))
                    lepett = null;
            }
            //lepes sikeres vegrehajtasa utan update, ami frissiti a label-eket
            this.update();
            //majd a terkep frissitese, ez az update, csak az aktiv jatekoshoz kapcsolodo mezoket frissitik a terkepen
            this.mapUpdate();
            //műveletek (támadás, lopás, anyag felvétel, felszerelés felvétel, kraftolás, kód tanulása)
            String action;
            do {
                action = null;
                Window.get().setInfo(map.currentPlayer.GetField().getName() + " Válassz egy akciót, amit végre szeretnél hajtani");
                //varunk egy akcio label kattintasra
                while (action == null) {
                    action = getAction();
                }
                //ha ez megtortent, akkor megvizsgaljuk, hogy melyik akcio volt az
                switch (action) {
                    //---------
                    //TAMADAS
                    //---------
                    case "tamadas":
                        // ha nincs felhasznalhato agens
                        if (map.getCurrentPlayer().GetInventory().GetAgents().isEmpty()) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Válassz egy játékost!");
                        Player playerClicked = getPlayer();
                        Window.get().setInfo("Valassz egy felhasznalhato agenst!");
                        Agent agentClicked = getAgent();
                        if (map.getCurrentPlayer() == playerClicked)
                            map.getCurrentPlayer().AttackOnSelf(agentClicked);
                        else
                            map.getCurrentPlayer().Attack(playerClicked, agentClicked);
                        update();
                        break;

                    //---------
                    //FEGYVER LOPAS
                    //---------
                    case "fegyverlopas":
                        //ha nem all a mezon senki mas:
                        if (map.getCurrentPlayer().GetField().GetPlayers().size() == 1) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Válassz egy játékost!");
                        Player playerClicked1 = getPlayer();
                        // ha a masiknak nincsenek eszkozei
                        if (playerClicked1.GetInventory().GetGears().isEmpty()) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Válassz egy fegyvert!");
                        //atallitjuk a paneleket a celpont fegyvereire
                        Window.get().stealGearSet(playerClicked1);
                        Gear toSteal = getGear();
                        //visszaallitjuk a paneleket
                        Window.get().stealGearSet(map.getCurrentPlayer());
                        map.getCurrentPlayer().StealGear(playerClicked1, toSteal);
                        update();
                        break;

                    //---------
                    //ANYAG LOPAS
                    //---------
                    case "anyaglopas":
                        //ha nem all a mezon senki mas:
                        if (map.getCurrentPlayer().GetField().GetPlayers().size() == 1) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Válassz egy játékost!");
                        Player playerClicked2 = getPlayer();
                        //ha a celpontnak nincsenek nyersanyagai
                        if (playerClicked2.GetInventory().GetMaterials().isEmpty()) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Válassz egy anyagot!");
                        Window.get().chooseMaterial();
                        Material material = getMaterial();
                        Window.get().materialSet(map.getCurrentPlayer());
                        map.getCurrentPlayer().StealMaterial(playerClicked2, material);
                        update();
                        break;

                    //---------
                    //KOD LETAPOGATASA
                    //---------
                    case "kod letapogatasa":
                        CollectCode();
                        update();
                        break;

                    //---------
                    //FELSZERELES FELVETELE
                    //---------
                    case "felszereles felvetele":
                        CollectGear();
                        update();
                        break;

                    //---------
                    //ANYAG FELVETELE
                    //---------
                    //itt random sorsolodik 2 material, es azt a kettot veszi fel a raktarbol a jatekos
                    case "anyag felvetele":
                        List<Material> req = new ArrayList<>();
                        double rand = Math.random();
                        if (rand < 0.33) {
                            //vagy 2 nukleotidot
                            req.add(new Nukleo());
                            req.add(new Nukleo());
                        } else if (rand < 0.66) {
                            //vagy mindkettobol egyet
                            req.add(new Nukleo());
                            req.add(new Amino());
                        } else {
                            //vagy 2 aminosavat kaphatunk
                            req.add(new Amino());
                            req.add(new Amino());
                        }
                        CollectMaterial(req);
                        update();
                        break;

                    //---------
                    //KOD CRAFTOLASA
                    //---------
                    case "craftolas":
                        //ha nincs megtanult kodja
                        if (map.getCurrentPlayer().GetKnownCodes().isEmpty()) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Mit szeretnél craftolni?");
                        Code clickedCode = getCode();
                        map.getCurrentPlayer().GetInventory().Craft(clickedCode);
                        update();
                        break;

                    //---------
                    //KOR VEGE
                    //---------
                    case "kor vege":
                        //lellenorizzuk a kor vegen, hogy az aktiv jatekos megnyerte e a jatekot

                        winner = getWinner();
                        //beallitjuk, hogy az aktiv jatekos a map jatekos listajaban a kovetkezo legyen
                        setCurrentPlayer(map.players.get((map.players.indexOf(getCurrentPlayer()) + 1) % map.players.size()));
                        map.getCurrentPlayer().Decrase();

                        //a kor vegen a teljes terkepet ki kell rajzolni, nem csak az adott jatekoshoz kapcsolodo mezok valtoznak
                        Window.get().mapUpdateKorvege();
                        //es a paneleket is updatelni kell
                        update();
                        break;
                }
            } while (!action.equals("kor vege"));
        }
    }

    /**
     * A Window update-jet hivja
     * Frissiti a labeleket
     */
    public void update() {
        Window.get().update();
    }

    /**
     * A Window mapUpdate-jet hivja
     * Frissiti a terkepet
     */
    private void mapUpdate() {
        Window.get().mapUpdate();
    }

    /**
     * Megadja a gyoztest
     * Az osszes jatekosra megvizsgalja, hogy megtanultak-e az osszes kodot.
     * Ha igen, kezdemenyezi a jatek befejezeset.
     *
     * @return A gyoztes Player referenciaja, ha nincs akkor null.
     */
    public Player getWinner() {
        for (Player p : map.players) {
            if (p.GetKnownCodesSize() == allCodes.size()) {
                End();
                return p;
            }
        }
        return null;
    }

    /**
     * A jatek veget er
     */
    private void End() {
        Window.get().setInfo("Gratulalunk, nyertel!!:)");
    }

    /**
     * current player gettere
     * @return visszater a jatekossal, akinek a kore megy eppen
     */
    public Player getCurrentPlayer() {
        return map.getCurrentPlayer();
    }

    /**
     * Jelenleg a körön lévő játékos settere
     *
     * @param p visszatér a játékossal
     */
    public void setCurrentPlayer(Player p) {
        map.currentPlayer = p;
    }

    /**
     * A ChooseGear() függvény akkor hívódik meg, amikor a játékos felszerelést szeretne/muszaj neki eldobni ,
     * ha az inventory-jában 3 nál több felszerelés lenne
     * Eldöntheti melyiket, dobja el, és így melyik 3-t tartsa meg.
     */
    public void ChooseGear() {
        if (map.currentPlayer.GetInventory().GetGears().size() > 3) {
            List<Gear> g1 = map.currentPlayer.GetInventory().GetGears();

            Window.get().setInfo("Melyik felszerlést szeretnéd eldobni?");
            Gear clickedGear = getGear();
            g1.remove(clickedGear);
        }
    }

    /**
     * A CollectGear() függvény segítségével lehet felszerelést felvenni az óvóhelyről.
     */
    public void CollectGear() {
        //ha bénult nem tud felvenni
        if (map.getCurrentPlayer().ParalyzedFor() > 0) {
            Window.get().setInfo("Bénultan nem vehetsz fel felszerelést.");
            return;
        }

        // a mezőn lévő felszerelés
        Gear g = map.getCurrentPlayer().GetField().GetGear();

        /*ha valóban van felszerelés a mezőn, a játékos kiválaszthatja, hogy mit szerretne felvenni,
        és ha az megtalálható ott, akkor felveheti
         */
        if (g != null) {
            map.getCurrentPlayer().GetInventory().Add(g);
            ChooseGear();
        }
        //ha nincs felszerelés a mezőn
        else
            Window.get().setInfo("Nem tudsz fegyvert felvenni:((");
        Window.get().update();

    }

    /**
     * A fuggveny az aktualis jatekos inventoryjahoz hozzaadja a kodot,
     * amit az a mezo tartalmazn, amin a jatekos all.
     * Ha a jatekos le van benulva, nem csinal semmit.
     * Ha a jatekos nem laboron all, nem csinal semmit.
     * Ellenorzi, hogy lett-e nyertes.
     */
    public void CollectCode() {
        //ha a jatekos le van benulva
        if (map.currentPlayer.ParalyzedFor() > 0) {
            Window.get().setInfo("Bénultan nem tapogathatsz");
            return;
        }
        Field field = map.currentPlayer.GetField();
        Code code = field.GetCode();
        //ha a mezon nem volt kod
        if (code == null) {
            Window.get().setInfo("Ezen a mezon nincsen kod.");
            return;
        }
        map.currentPlayer.LearnCode(code);
        getWinner();
    }

    /**
     * Anyag felvétele
     */
    public void CollectMaterial(List<Material> required) {
        //megvizsgaljuk, hogy a jatekos le van e benulva
        if (map.getCurrentPlayer().ParalyzedFor() > 0) {
            Window.get().setInfo("Bénultan nem vehetsz fel anyagot.");
            return;
        }

        //a current player altal birtokolt anyagok listaja
        List<Material> owned = map.getCurrentPlayer().GetInventory().GetMaterials();
        //max ennyi lehet nala
        int max = map.getCurrentPlayer().GetInventory().GetMaxMaterials();

        //ha van nala hely ahhoz felszedje, amennyit szeretne
        if (owned.size() <= (max - required.size())) {
            boolean isEnough = map.getCurrentPlayer().GetField().AquireMaterials(required);
            if (isEnough) {
                //Itt nem a laborról kellene eltávolítani a required-et?
                map.getCurrentPlayer().GetField().RemoveMaterials(required);
                for (Material material : required) map.getCurrentPlayer().GetInventory().Add(material);
            } else {
                Window.get().setInfo("Nem tudsz anyagot felvenni erről a mezőről.\n" + "Lehet, hogy nem raktáron állsz, vagy kevesebb anyag\n" + "van a raktárban, mint amennyit felvenni szeretnél. :c");
            }
        } else {
            Window.get().setInfo("Nincs elég hely az inventory-dban.");
        }
    }

    /**
     * A metodus feladata a szalat varakoztatni, amig a jatekos egy akcio label-re nem kattint
     * Amig az action null, addig varakozik a szal, amint kattintas tortent interrupt exception
     * varakozas kozben hibaval kilep, ezt elkapjuk
     * @return visszaadja azt az akciot reprezentalo label szoveget, amire a jatekos kattintott
     */
    public synchronized String getAction() {
        String action = null;
        while (action == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                //a window-tol lekeri az utolsonak kattintott az egy olyan-e, amit vartunk
                //igy meg nem folytatodik a jatek, ha olyanra kattintottunk, amit nem vartunk
                action = Window.get().getAction();
            }
        }
        return action;
    }

    /**
     * A metodus feladata a szalat varakoztatni, amig a jatekos egy agens label-re nem kattintott
     * Amig az agent null, addig varakozik a szal, amint kattintas tortent interrupt exception
     * varakozas kozben hibaval kilep, ezt elkapjuk
     * @return visszaadja azt az agenst amire a jatekos kattintott
     */
    public synchronized Agent getAgent() {
        Agent agent = null;
        while (agent == null) {
            try {
                wait();
                //ha kattintottuk kilep a varakozo allapotbol
            } catch (InterruptedException e) {
                //a window-tol lekeri az utolsonak kattintott az egy olyan-e, amit vartunk
                //igy meg nem folytatodik a jatek, ha olyanra kattintottunk, amit nem vartunk
                agent = Window.get().getAgent();
            }
        }
        return agent;
    }

    /**
     * A metodus feladata a szalat varakoztatni, amig a jatekos egy code label-re nem kattintott
     * Amig a code null, addig varakozik a szal, amint kattintas tortent interrupt exception
     * varakozas kozben hibaval kilep, ezt elkapjuk
     * @return visszaadja azt a code-ot amire a jatekos kattintott
     */
    public synchronized Code getCode() {
        Code code = null;
        while (code == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                //a window-tol lekeri az utolsonak kattintott az egy olyan-e, amit vartunk
                //igy meg nem folytatodik a jatek, ha olyanra kattintottunk, amit nem vartunk
                code = Window.get().getCode();
            }
        }
        return code;
    }

    /**
     * A metodus feladata a szalat varakoztatni, amig a jatekos egy gear label-re nem kattintott
     * Amig a gear null, addig varakozik a szal, amint kattintas tortent interrupt exception
     * varakozas kozben hibaval kilep, ezt elkapjuk
     * @return visszaadja azt a gear-t amire a jatekos kattintott
     */
    public synchronized Gear getGear() {
        Gear gear = null;
        while (gear == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                //a window-tol lekeri az utolsonak kattintott az egy olyan-e, amit vartunk
                //igy meg nem folytatodik a jatek, ha olyanra kattintottunk, amit nem vartunk
                gear = Window.get().getGear();
            }
        }
        return gear;
    }

    /**
     * A metodus feladata a szalat varakoztatni, amig a jatekos egy material label-re nem kattintott
     * Amig a material null, addig varakozik a szal, amint kattintas tortent interrupt exception
     * varakozas kozben hibaval kilep, ezt elkapjuk
     * @return visszaadja azt a material-t amire a jatekos kattintott
     */
    public synchronized Material getMaterial() {
        Material material = null;
        while (material == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                //a window-tol lekeri az utolsonak kattintott az egy olyan-e, amit vartunk
                //igy meg nem folytatodik a jatek, ha olyanra kattintottunk, amit nem vartunk
                material = Window.get().getMaterial();
            }
        }
        return material;
    }

    /**
     * A metodus feladata a szalat varakoztatni, amig a jatekos egy material field-re nem kattintott
     * Amig a field null, addig varakozik a szal, amint kattintas tortent interrupt exception
     * varakozas kozben hibaval kilep, ezt elkapjuk
     * @return visszaadja azt a mezot amire a jatekos kattintott
     */
    public synchronized Field getField() {
        Field field = null;
        while (field == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                //a window-tol lekeri az utolsonak kattintott az egy olyan-e, amit vartunk
                //igy meg nem folytatodik a jatek, ha olyanra kattintottunk, amit nem vartunk
                field = Window.get().getField();
            }
        }
        return field;
    }

    /**
     * A metodus feladata a szalat varakoztatni, amig a jatekos egy player field-re nem kattintott
     * Amig a player null, addig varakozik a szal, amint kattintas tortent interrupt exception
     * varakozas kozben hibaval kilep, ezt elkapjuk
     * @return visszaadja azt a jatekost amire a felhasznalo kattintott
     */
    public synchronized Player getPlayer() {
        Player player = null;
        while (player == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                //a window-tol lekeri az utolsonak kattintott az egy olyan-e, amit vartunk
                //igy meg nem folytatodik a jatek, ha olyanra kattintottunk, amit nem vartunk
                player = Window.get().getPlayer();
            }
        }
        return player;
    }

    /**
     * a parameterkent kapott jatekost meggyilkolka - ez akkor tortenhet, ha valakit baltaval lecsaptak
     * @param p a jatekos, aki meghal
     */
    public void Died(Player p) {
        if (p == null) return;
        //ennyiedik eleme a playerek listának, akit törölni szeretnénk majd,
        // de eleinte -1, ha esetleg id alapján nem talaljuk,
        // akkor azert megse oljon meg egy random jatekost
        int index = -1;
        for (int i = 0; i < map.players.size(); i++) {
            //ha az id megegyezik valamelyik jatekoseval, akkor annak a jatekosnak az indexet allitjuk be
            if (Objects.equals(p.getID(), map.players.get(i).getID())) {
                index = i;
            }
        }
        //ha nem -1 maradt, akkor kitoroljuk a jatekost
        if (index != -1) {
            map.players.remove(index);
        }
    }

}
