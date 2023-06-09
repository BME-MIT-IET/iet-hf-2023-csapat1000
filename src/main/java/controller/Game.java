package controller;


import model.*;
import view.Window;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game extends Thread {
    /**
     * egy jatekterkepet tarol.
     */
    private final Map map;
    /**
     * es az osszes megszerezheto genetikai kodot a jatekban.
     */
    private final List<Code> allCodes;
    /**
     * A játékban használt sokszögek oldalainak száma.
     */
    private final int sides = 6;


    /**
     * A Game osztaly konstruktora.
     * inicializalasok tortennek benne
     * @param jatekosok parameterkent kapja, hogy hany jatekos fogja jatszani a jatekot
     */
    public Game(int jatekosok) {
        super();
        //letrehoz, egy 6x6-os jatekterkepet, es ugyanekkor parameterul adja tovabb a jatekosok szamat
        map = new Map(sides, jatekosok);
        //tarolja az osszes megszerezheto genetikai kodot a jatekban
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
     * A terkep gettere.
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
            if (map.getCurrentPlayer().paralyzedFor() > 0) { lepett = map.getCurrentPlayer().getField(); }
            while (lepett == null) {
                lepett = getField();
                // Itt kene hogy booleant csinalni
                if (!map.getCurrentPlayer().move(lepett))
                    lepett = null;
            }
            //lepes sikeres vegrehajtasa utan update, ami frissiti a label-eket
            this.update();
            //majd a terkep frissitese, ez az update, csak az aktiv jatekoshoz kapcsolodo mezoket frissitik a terkepen
            this.mapUpdate();
            //muveletek (tamadas, lopas, anyag felvetel, felszereles felvetel, kraftolas, kod tanulasa)
            String action;
            do {
                action = null;
                Window.get().setInfo(map.currentPlayer.getField().getName() + " Valassz egy akciot, amit vegre szeretnel hajtani");
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
                        if (map.getCurrentPlayer().getInventory().GetAgents().isEmpty()) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Valassz egy jatekost!");
                        Player playerClicked = getPlayer();
                        Window.get().setInfo("Valassz egy felhasznalhato agenst!");
                        Agent agentClicked = getAgent();
                        if (map.getCurrentPlayer() == playerClicked) {
                            map.getCurrentPlayer().attackOnSelf(agentClicked);
                        } else {
                            map.getCurrentPlayer().attack(playerClicked, agentClicked);
                        }
                        update();
                        break;

                    //---------
                    //FEGYVER LOPAS
                    //---------
                    case "fegyverlopas":
                        //ha nem all a mezon senki mas:
                        if (map.getCurrentPlayer().getField().getPlayers().size() == 1) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Valassz egy jatekost!");
                        Player playerClicked1 = getPlayer();
                        // ha a masiknak nincsenek eszkozei
                        if (playerClicked1.getInventory().GetGears().isEmpty()) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Valassz egy fegyvert!");
                        //atallitjuk a paneleket a celpont fegyvereire
                        Window.get().stealGearSet(playerClicked1);
                        Gear toSteal = getGear();
                        //visszaallitjuk a paneleket
                        Window.get().stealGearSet(map.getCurrentPlayer());
                        map.getCurrentPlayer().stealGear(playerClicked1, toSteal);
                        update();
                        break;

                    //---------
                    //ANYAG LOPAS
                    //---------
                    case "anyaglopas":
                        //ha nem all a mezon senki mas:
                        if (map.getCurrentPlayer().getField().getPlayers().size() == 1) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Valassz egy jatekost!");
                        Player playerClicked2 = getPlayer();
                        //ha a celpontnak nincsenek nyersanyagai
                        if (playerClicked2.getInventory().GetMaterials().isEmpty()) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Valassz egy anyagot!");
                        Window.get().chooseMaterial();
                        Material material = getMaterial();
                        Window.get().materialSet(map.getCurrentPlayer());
                        map.getCurrentPlayer().stealMaterial(playerClicked2, material);
                        update();
                        break;

                    //---------
                    //KOD LETAPOGATASA
                    //---------
                    case "kod letapogatasa":
                        collectCode();
                        update();
                        break;

                    //---------
                    //FELSZERELES FELVETELE
                    //---------
                    case "felszereles felvetele":
                        collectGear();
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
                        collectMaterial(req);
                        update();
                        break;

                    //---------
                    //KOD CRAFTOLASA
                    //---------
                    case "craftolas":
                        //ha nincs megtanult kodja
                        if (map.getCurrentPlayer().getKnownCodes().isEmpty()) {
                            update();
                            break;
                        }
                        Window.get().setInfo("Mit szeretnel craftolni?");
                        Code clickedCode = getCode();
                        map.getCurrentPlayer().getInventory().Craft(clickedCode);
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
                        map.getCurrentPlayer().decrase();

                        //a kor vegen a teljes terkepet ki kell rajzolni, nem csak az adott jatekoshoz kapcsolodo mezok valtoznak
                        Window.get().mapUpdateKorvege();
                        //es a paneleket is updatelni kell
                        update();
                        break;
                    default:
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
            if (p.getKnownCodesSize() == allCodes.size()) {
                end();
                return p;
            }
        }
        return null;
    }

    /**
     * A jatek veget er
     */
    private void end() {
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
     * Jelenleg a koron levo jatekos settere
     *
     * @param p visszater a jatekossal
     */
    public void setCurrentPlayer(Player p) {
        map.currentPlayer = p;
    }

    /**
     * A ChooseGear() fuggveny akkor hivodik meg, amikor a jatekos felszerelest szeretne/muszaj neki eldobni ,
     * ha az inventory-jaban 3 nal tobb felszereles lenne
     * Eldontheti melyiket, dobja el, es igy melyik 3-t tartsa meg.
     */
    public void chooseGear() {
        if (map.currentPlayer.getInventory().GetGears().size() > 3) {
            List<Gear> g1 = map.currentPlayer.getInventory().GetGears();

            Window.get().setInfo("Melyik felszerlest szeretned eldobni?");
            Gear clickedGear = getGear();
            g1.remove(clickedGear);
        }
    }

    /**
     * A CollectGear() fuggveny segitsegevel lehet felszerelest felvenni az ovohelyrol.
     */
    public void collectGear() {
        //ha benult nem tud felvenni
        if (map.getCurrentPlayer().paralyzedFor() > 0) {
            Window.get().setInfo("Benultan nem vehetsz fel felszerelest.");
            return;
        }

        // a mezon levo felszereles
        Gear g = map.getCurrentPlayer().getField().getGear();

        /*ha valoban van felszereles a mezon, a jatekos kivalaszthatja, hogy mit szerretne felvenni,
        es ha az megtalalhato ott, akkor felveheti
         */
        if (g != null) {
            map.getCurrentPlayer().getInventory().Add(g);
            chooseGear();
        }
        //ha nincs felszereles a mezon
        else {
            Window.get().setInfo("Nem tudsz fegyvert felvenni:((");
        }
        Window.get().update();

    }

    /**
     * A fuggveny az aktualis jatekos inventoryjahoz hozzaadja a kodot,
     * amit az a mezo tartalmazn, amin a jatekos all.
     * Ha a jatekos le van benulva, nem csinal semmit.
     * Ha a jatekos nem laboron all, nem csinal semmit.
     * Ellenorzi, hogy lett-e nyertes.
     */
    public void collectCode() {
        //ha a jatekos le van benulva
        if (map.currentPlayer.paralyzedFor() > 0) {
            Window.get().setInfo("Benultan nem tapogathatsz");
            return;
        }
        Field field = map.currentPlayer.getField();
        Code code = field.getCode();
        //ha a mezon nem volt kod
        if (code == null) {
            Window.get().setInfo("Ezen a mezon nincsen kod.");
            return;
        }
        map.currentPlayer.learnCode(code);
        getWinner();
    }

    /**
     * Anyag felvetele
     */
    public void collectMaterial(List<Material> required) {
        //megvizsgaljuk, hogy a jatekos le van e benulva
        if (map.getCurrentPlayer().paralyzedFor() > 0) {
            Window.get().setInfo("Benultan nem vehetsz fel anyagot.");
            return;
        }

        //a current player altal birtokolt anyagok listaja
        List<Material> owned = map.getCurrentPlayer().getInventory().GetMaterials();
        //max ennyi lehet nala
        int max = map.getCurrentPlayer().getInventory().GetMaxMaterials();

        //ha van nala hely ahhoz felszedje, amennyit szeretne
        if (owned.size() <= (max - required.size())) {
            boolean isEnough = map.getCurrentPlayer().getField().aquireMaterials(required);
            if (isEnough) {
                //Itt nem a laborrol kellene eltavolitani a required-et?
                map.getCurrentPlayer().getField().removeMaterials(required);
                for (Material material : required) map.getCurrentPlayer().getInventory().Add(material);
            } else {
                Window.get().setInfo("Nem tudsz anyagot felvenni errol a mezorol.\n" + "Lehet, hogy nem raktaron allsz, vagy kevesebb anyag\n" + "van a raktarban, mint amennyit felvenni szeretnel. :c");
            }
        } else {
            Window.get().setInfo("Nincs eleg hely az inventory-dban.");
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
    public void died(Player p) {
        if (p == null) return;
        //ennyiedik eleme a playerek listanak, akit torolni szeretnenk majd,
        // de eleinte -1, ha esetleg id alapjan nem talaljuk,
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
