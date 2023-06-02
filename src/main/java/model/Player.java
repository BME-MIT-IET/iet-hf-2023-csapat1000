package model;

import view.Window;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements java.io.Serializable {
    // Az ID-t a konstruktor beallitja a lastID-nel eggyel nagyobb szamra,
    // majd utana atallitja a lastID-t a sajat ID-jere.
    // Ezert mindig lehet tudni, hogy mekkora volt az "elozo" ID,
    // amikor letrehozunk egy uj jatekost.
    /*static int lastID = 0;
    private int ID;*/
    private int ID;
    private Field field;
    private final List<Agent> activeAgents;
    private final Inventory inventory;
    private final List<Code> knownCodes;
    private final List<Field> visited;

    public Player() {
        activeAgents = new ArrayList<>();
        inventory = new Inventory();
        knownCodes = new ArrayList<>();
        visited = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return String.valueOf(ID);
    }

    public void setID(int id) {
        ID = id;
    }

    /**
     * Hozzaadja a jatekoshoz a parameterkent kapott kodot.
     * Ha a jatekos  mar tartalmazza ezt a kodot, nem tortenik semmit.
     *
     * @param c a felvenni kivant kod.
     */
    public void learnCode(Code c) {
        // minden kodbol legeljebb egy szerepelhet!
        if (knownCodes.stream().anyMatch(code -> code.getClass() == c.getClass())) {
            Window.get().setInfo("Mar megtanultad ezt a kodot!");
            return;
        }
        knownCodes.add(c);
    }

    /**
     * Kitorol mindent a knowncodesbol, igy a jatekos elfelejti az osszes kodot amit eddig megtanult.
     */
    public void forget() {
        knownCodes.clear();
    }

    /**
     * knowncodes meretenek a getttere
     */
    public int getKnownCodesSize() {
        return knownCodes.size();
    }

    /**
     * knowncodes getttere
     */
    public List<Code> getKnownCodes() {
        return knownCodes;
    }

    /**
     * A jatekost az altala kivalasztott mezore helyezi,
     * abban az esetben ha ez megfelel a jatekszabalyoknak
     *
     * @param f a jatekos altal valasztott mezo
     */
    public boolean move(Field f) {
        if (paralyzedFor() > 0) {
            return false;
        }
        List<Field> n = field.getNeighbours();
        if (choreaFor() > 0) {
            f = n.get(new Random().nextInt(n.size()));
        }
        if (!n.contains(f))
            return false;
        f.addPlayer(this);
        visited.add(f);
        field.removePlayer(this);
        field = f;
        activeAgents.forEach(agent -> agent.moved(this, field));
        return true;
    }

    /**
     * a player-re hato aktiv agensek listajahoz ad hozza egyet
     *
     * @param a agens, amit a jatekosra kennek
     */
    public void addAgent(Agent a) {
        activeAgents.add(a);
        a.applied(this);
    }

    /**
     * field settere
     * hozzaadja a mezot a jatekos lsitajahoz, amiben a mar bejart mezokezt tarolja
     */
    public void setField(Field f) {
        field = f;
        f.addPlayer(this);
        visited.add(f);
    }

    /**
     * field gettere
     */
    public Field getField() {
        return field;
    }


    /**
     * A jatekos altal megkezdett tamadasokat kezelo fuggveny
     * Vezerli azt, amikor egy virologus megtamad egy masikat
     * Ilyenkor a vedo minden vedofelszereleset vegignezi, es meghivja a GotAttacked fuggvenyeiket
     *
     * @param t A megtamadott jatekos
     * @param a A tamadasra hasznalt agens
     */
    public void attack(Player t, Agent a) {
        if (this.paralyzedFor() > 0) {
            Window.get().setInfo("Benultan nem lehet masra agenst kenni!");
            return;
        }
        t.gotAttacked(this, a);
        inventory.RemoveAgent(a);
    }


    /**
     * A jatekos ellen megkezdett tamadasokat kezelo fuggveny
     * Az osszes lehetseges vedofelszerelesen vegigmegy
     * Az osszes lehetseges vedoagensen vegig megy
     *
     * @param s A tamadast megkezdo jatekos
     * @param a A tamadasra hasznalt agens
     */
    public void gotAttacked(Player s, Agent a) {
        // Kivedte shield agensel
        for (var item : activeAgents) {
            if (item.shieldFor() > 0) {
                Window.get().setInfo("A tamadas sikertelen");
                return;
            }
        }

        Inventory sInv = s.getInventory();

        // Vegigmegy a megtamadott itemein
        for (var item : inventory.GetGears())
            if (item.attacked(s, this, a)) {
                // Visszaallitja az s itemeit
                sInv.clearGears();
                inventory.clearGears();
                return;
            }
        addAgent(a);
        sInv.clearGears();
        inventory.clearGears();

    }


    /**
     * Vegig megy az osszes jatekosra jelenleg hato agensen
     * Megkeresi azt az agenset, amely legtovabb benitja a jatekos
     * Ha nincs ilyen agens akkor 0-val ter vissza
     *
     * @return a benulas vegeig hatra levo korok szama
     */
    public int paralyzedFor() {
        int max = 0, temp;
        for (var item : activeAgents) {
            temp = item.paralyzedFor();
            if (temp > max)
                max = temp;
        }
        return max;
    }

    /**
     * Vegig megy az osszes jatekosra jelenleg hato agensen
     * Megkeresi azt az agenset, amely legtovabb tancoltatja jatekost
     * Ha nincs ilyen agens akkor 0-val ter vissza
     *
     * @return a tancolas elbomlasaig hatralevo korok szama
     */
    public int choreaFor() {
        int max = 0, temp;
        for (var item : activeAgents) {
            temp = item.choreaFor();
            if (temp > max)
                max = temp;
        }
        return max;
    }

    /**
     * inventory gettere
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Ellenorzi, hogy a ket jatekos ugyanazon mezon all-e, ha nem akkor return
     * Ellenori, hogy a masik jatekos valoban le van-e benulva, ha nincs akkor return
     * Ellenorzi, hogy az ellopni kivant anyag benne van-e a benult jatekos inventory-jaban, ha nincs akkor return
     * Ha nincs tele a jatekos inventory-ja akkor eltavolitja a benult jatekosebol az anyagot, es a mienkhez adja
     * Ha tele van, akkor return
     *
     * @param t        parameterul kapja a jatekost, akitol lopni szeretnenk
     * @param required illetve az anyagot, amit el szeretnenk lopni
     */
    public void stealMaterial(Player t, Material required) {
        if (this.paralyzedFor() > 0) {
            Window.get().setInfo("Le vagy benulva, nem csinalhatsz semmit!");
            return;
        }
        if (field != t.getField()) {
            Window.get().setInfo("Nem ugyanazon a mezon alltok!");
            return;
        }
        if (!(t.paralyzedFor() > 0)) {
            System.out.println("A jatekos akitol lopni szeretnel nincs lebenulva");
            return;
        }
        if (!t.getInventory().Contains(required)) {
            Window.get().setInfo("A jatekos akitol lopni szeretnel nem rendelkezik az anyaggal, amit el szeretnel venni tole");
            return;
        }

        int max = inventory.GetMaxMaterials();
        List<Material> owned1 = inventory.GetMaterials();
        if (owned1.size() <= max - 1) {
            t.getInventory().RemoveMaterial(required);
            inventory.Add(required);
        } else {
            Window.get().setInfo("Nincs eleg hely az inventory-dban az ellopni kivant anyag tarolasahoz.");
        }

    }

    /**
     * Akkor hivodik meg, amikor a jatekos el akar lopni egy felszerelest egy lebenult jatekostol
     *
     * @param t a jatekos akitol lopni szeretne
     * @param g a felszereles amit el szeretne lopni
     */
    public void stealGear(Player t, Gear g) {
        if (this.paralyzedFor() > 0) {
            Window.get().setInfo("Le vagy benulva, nem csinalhatsz semmit!");
            return;
        }
        if (field != t.getField()) {
            Window.get().setInfo("Nem ugyanazon a mezon alltok!");
            return;
        }
        if (!(t.paralyzedFor() > 0)) {
            Window.get().setInfo("A jatekos akitol lopni szeretnel nincs lebenulva");
            return;
        }
        if (!t.getInventory().Contains(g)) {
            Window.get().setInfo("A jatekos akitol lopni szeretnel nem rendelkezik az felszerelessel, amit el szeretnel venni tole");
            return;
        }

        Player p1 = t.getField().getPlayers().get(1);

        //ha zsakot lop el, le kell vonni a masik jatekostol a felesleges materialokat
        if (g.getPlusSize() > 1) {
            t.getInventory().RemoveGear(g);
            t.getInventory().GetMaterials();
            if (t.getInventory().GetMaterials().size() > t.getInventory().GetMaxMaterials()) {
                int a = t.getInventory().GetMaterials().size() - t.getInventory().GetMaxMaterials();
                List<Material> b = new ArrayList<>();
                for (int i = 0; i < a; i++) {
                    b.add(t.getInventory().GetMaterials().get(t.getInventory().GetMaxMaterials() + i));
                }
                t.getInventory().RemoveMaterials(b);
            } else {
                p1.getInventory().Add(g);
                if (p1.getInventory().GetGears().size() > 3) {
                    Window.get().getGame().chooseGear();
                }
            }
        }
        //ha nem zsakot lop el
        if (t.getInventory().GetGears().contains(g)) {
            t.getInventory().RemoveGear(g);
            p1.getInventory().Add(g);
            if (p1.getInventory().GetGears().size() > 3) {
                Window.get().getGame().chooseGear();
            }
        }
    }


    /**
     * Csokkenti a jatekosra aktivan hato agensek elbomlasaig hatra levo idejet
     * Minden ilyen agens time attributumat eggyel csokkenti
     */
    public void decrase() {
        for (var item : activeAgents)
            item.decrase();
    }

    /**
     * Kezeli, amikor a jatekos sajat magara ken agenst.
     * Ilyenkor nem hasznalja a tamadas elleni lehetosegeit, hanem mindenkepp elfogadja a kenest.
     * <p>
     * A jatekos benult allapotban is megprobalhat tamadni
     */
    public void attackOnSelf(Agent a) {
        if (this.paralyzedFor() > 0) {
            Window.get().setInfo("Sikertelen tamadas");
            return;
        }

        this.addAgent(a);
    }

    /**
     * A jatekos altal kert agenst keresi meg es torli az inventoryjabol.
     * Az input hibak kezeleseert is felelos
     *
     * @return a kivalsztott agenst adja vissza, ha erre van lehetosege
     * Ha olyan agenst szeretnenk kerni, amivel nem rendelkezunk akkor null erteket ad vissza
     * <p>
     * A jatekos olyan agenseket is valaszthat, amivel nem rendelkezik az adott pillanatban, ekkor a rendszer jelzi,
     * hogy ilyet nem kepes csinalni.
     */
    public Agent chooseAgent() {
        List<Agent> agents = this.getInventory().GetAgents();
        if (agents.size() == 0) {
            System.out.println("Nincsen felhasznalhato agensed!");
            return null;
        }
        System.out.println("Milyen agenset szeretnel magadra kenni?");
        System.out.println("1. Benito");
        System.out.println("2. Vedo");
        System.out.println("3. Felejto");
        System.out.println("4. Vitustanc");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice = 0;
        while (choice < 1 || choice > 4) {
            try {
                choice = Integer.parseInt(reader.readLine().trim());
            } catch (Exception e) {
                System.out.println("Nem sikerult olvasni a szamot.");
            }
            if (choice < 1 || choice > 4)
                System.out.println("Ilyen valasztasi lehetoseg nincs.");
            Agent a;
            if (choice == 1) {
                for (int i = 0; i < agents.size(); i++) {
                    if (agents.get(i).paralyzedFor() > 0) {
                        a = agents.get(i);
                        agents.remove(i);
                        return a;
                    }
                }
            }
            if (choice == 2) {
                for (int i = 0; i < agents.size(); i++) {
                    if (agents.get(i).shieldFor() > 0) {
                        a = agents.get(i);
                        agents.remove(i);
                        return a;
                    }
                }
            }
            if (choice == 3) {
                for (int i = 0; i < agents.size(); i++) {
                    if (agents.get(i).paralyzedFor() == 0 && agents.get(i).choreaFor() == 0 && agents.get(i).shieldFor() == 0) {
                        a = agents.get(i);
                        agents.remove(i);
                        a.applied(this);
                        return null;
                    }
                }
            }
            if (choice == 4) {
                for (int i = 0; i < agents.size(); i++) {
                    if (agents.get(i).choreaFor() > 0) {
                        a = agents.get(i);
                        agents.remove(i);
                        return a;
                    }
                }
            }
        }
        System.out.println("Nincs ilyen agensed!");
        return null;
    }

    /**
     * megvizsgaljuk, hogy a jatekos le van-e benulva, ha igen, akkor return
     * ha van annyi hely az inventory-ban, hogy beleferjenek a felvenni kivant anyagok, ha nem, akkor return
     * Akkor hivjuk, ha a jatekos anyagot vesz fel
     * leellenorizzuk, hogy valoban van-e annyi anyag a raktarban, mint amennyit felvenni szeretnenk
     * ha igen, akkor elvesszuk a raktarbol, es hozzaadjuk az inventory-hoz az anyagokat
     * egyebkent return
     */
    public void collectMaterial(List<Material> required) {
        if (this.paralyzedFor() > 0) {
            System.out.println("Benultan nem vehetsz fel anyagot.");
            return;
        }

        List<Material> owned = inventory.GetMaterials();
        int max = inventory.GetMaxMaterials();

        if (owned.size() <= (max - required.size())) {
            boolean isEnough = field.aquireMaterials(required);
            if (isEnough) {
                //Itt nem a laborrol kellene eltavolitani a required-et?
                field.removeMaterials(required);
                for (Material material : required) inventory.Add(material);
                return;
            } else {
                System.out.println("Nem tudsz anyagot felvenni errol a mezorol.\n" + "Lehet, hogy nem raktaron allsz, vagy kevesebb anyag\n" + "van a raktarban, mint amennyit felvenni szeretnel. :c");
                return;
            }
        } else {
            System.out.println("Nincs eleg hely az inventory-dban.");
            return;
        }
    }

    /**
     * a jatekos eddig meglatogatott mezoihez ad egy uj elemet.
     */
    public void addVisited(Field f) {
        if (f != null && !visited.contains(f))
            visited.add(f);
    }

    /**
     * Megtamad egy adott jatekost egy kivalasztott eszkozzel. A p parameter a cel jatekos, a g pedig az az eszkoz, amivel tamadja.
     * Ez a tamado eszkoz Applied(p: Player) metodusat fogja meghivni, ami a legtobb eszkoznel nem csinal semmit, csak amivel lehet tamadni (jelenleg ez csak az Axe).
     *
     * @param p A jatekos akit megtamadunk
     * @param g Az eszkoz amivel meg lesz tamadva
     */
    public void attackGear(Player p, Gear g) {
        g.applied(p);
    }

    public void died() {
        Window.get().getGame().died(this);
    }

    public List<Field> getVisited() {
        return visited;
    }

    /**
     * Hozzaadja a jatekos inventorijahoz a parameterkent kapott nyersanyagot.
     *
     * @param m Az inventorihoz hozzaadando nyersanyag.
     */
    public void addMaterial(Material m) {
        inventory.Add(m);
    }
}
