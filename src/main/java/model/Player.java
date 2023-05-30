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
    public void LearnCode(Code c) {
        // minden kodbol legeljebb egy szerepelhet!
        if (knownCodes.stream().anyMatch(code -> code.getClass() == c.getClass())) {
            Window.get().setInfo("Mar megtanultad ezt a kodot!");
            return;
        }
        knownCodes.add(c);
    }

    /**
     * Kitöröl mindent a knowncodesból, így a játékos elfelejti az összes kódot amit eddig megtanult.
     */
    public void Forget() {
        knownCodes.clear();
    }

    /**
     * knowncodes méretének a getttere
     */
    public int GetKnownCodesSize() {
        return knownCodes.size();
    }

    /**
     * knowncodes getttere
     */
    public List<Code> GetKnownCodes() {
        return knownCodes;
    }

    /**
     * A játékost az általa kiválasztott mezőre helyezi,
     * abban az esetben ha ez megfelel a játékszabályoknak
     *
     * @param f a játékos által választott mező
     */
    public boolean Move(Field f) {
        if (ParalyzedFor() > 0) {
            return false;
        }
        List<Field> n = field.GetNeighbours();
        if (ChoreaFor() > 0) {
            f = n.get(new Random().nextInt(n.size()));
        }
        if (!n.contains(f))
            return false;
        f.AddPlayer(this);
        visited.add(f);
        field.RemovePlayer(this);
        field = f;
        activeAgents.forEach(agent -> agent.Moved(this, field));
        return true;
    }

    /**
     * a player-re hato aktiv agensek listajahoz ad hozza egyet
     *
     * @param a agens, amit a jatekosra kennek
     */
    public void AddAgent(Agent a) {
        activeAgents.add(a);
        a.Applied(this);
    }

    /**
     * field settere
     * hozzáadja a mezőt a játékos lsitájához, amiben a már bejárt mezőkezt tárolja
     */
    public void SetField(Field f) {
        field = f;
        f.AddPlayer(this);
        visited.add(f);
    }

    /**
     * field gettere
     */
    public Field GetField() {
        return field;
    }


    /**
     * A játékos által megkezdett támadásokat kezelő függvény
     * Vezérli azt, amikor egy virológus megtámad egy másikat
     * Ilyenkor a védő minden védőfelszerelését végignézi, és meghívja a GotAttacked függvényeiket
     *
     * @param t A megtámadott játékos
     * @param a A támadásra használt ágens
     */
    public void Attack(Player t, Agent a) {
        if (this.ParalyzedFor() > 0) {
            Window.get().setInfo("Benultan nem lehet masra agenst kenni!");
            return;
        }
        t.GotAttacked(this, a);
        inventory.RemoveAgent(a);
    }


    /**
     * A játékos ellen megkezdett támadásokat kezelő függvény
     * Az összes lehetséges védőfelszerelésen végigmegy
     * Az összes lehetséges védőágensen végig megy
     *
     * @param s A támadást megkezdő játékos
     * @param a A támadásra használt ágens
     */
    public void GotAttacked(Player s, Agent a) {
        // Kivedte shield agensel
        for (var item : activeAgents) {
            if (item.ShieldFor() > 0) {
                Window.get().setInfo("A támadás sikertelen");
                return;
            }
        }

        Inventory sInv = s.GetInventory();

        // Vegigmegy a megtamadott itemein
        for (var item : inventory.GetGears())
            if (item.Attacked(s, this, a)) {
                // Visszaallitja az s itemeit
                sInv.clearGears();
                inventory.clearGears();
                return;
            }
        AddAgent(a);
        sInv.clearGears();
        inventory.clearGears();

    }


    /**
     * Végig megy az összes játékosra jelenleg ható ágensen
     * Megkeresi azt az ágenset, amely legtovább bénítja a játékos
     * Ha nincs ilyen ágens akkor 0-val tér vissza
     *
     * @return a bénulás végéig hátra lévő körök száma
     */
    public int ParalyzedFor() {
        int max = 0, temp;
        for (var item : activeAgents) {
            temp = item.ParalyzedFor();
            if (temp > max)
                max = temp;
        }
        return max;
    }

    /**
     * Végig megy az összes játékosra jelenleg ható ágensen
     * Megkeresi azt az ágenset, amely legtovább táncoltatja játékost
     * Ha nincs ilyen ágens akkor 0-val tér vissza
     *
     * @return a táncolás elbomlásáig hátralevő körök száma
     */
    public int ChoreaFor() {
        int max = 0, temp;
        for (var item : activeAgents) {
            temp = item.ChoreaFor();
            if (temp > max)
                max = temp;
        }
        return max;
    }

    /**
     * inventory gettere
     */
    public Inventory GetInventory() {
        return inventory;
    }

    /**
     * Ellenőrzi, hogy a két játékos ugyanazon mezőn áll-e, ha nem akkor return
     * Ellenőri, hogy a másik játékos valóban le van-e bénulva, ha nincs akkor return
     * Ellenőrzi, hogy az ellopni kívánt anyag benne van-e a bénult játékos inventory-jában, ha nincs akkor return
     * Ha nincs tele a játékos inventory-ja akkor eltávolítja a bénult játékoséból az anyagot, és a miénkhez adja
     * Ha tele van, akkor return
     *
     * @param t        paraméterül kapja a játékost, akitől lopni szeretnénk
     * @param required illetve az anyagot, amit el szeretnénk lopni
     */
    public void StealMaterial(Player t, Material required) {
        if (this.ParalyzedFor() > 0) {
            Window.get().setInfo("Le vagy bénulva, nem csinálhatsz semmit!");
            return;
        }
        if (field != t.GetField()) {
            Window.get().setInfo("Nem ugyanazon a mezőn álltok!");
            return;
        }
        if (!(t.ParalyzedFor() > 0)) {
            System.out.println("A játékos akitől lopni szeretnél nincs lebénulva");
            return;
        }
        if (!t.GetInventory().Contains(required)) {
            Window.get().setInfo("A játékos akitől lopni szeretnél nem rendelkezik az anyaggal, amit el szeretnél venni tőle");
            return;
        }

        int max = inventory.GetMaxMaterials();
        List<Material> owned1 = inventory.GetMaterials();
        if (owned1.size() <= max - 1) {
            t.GetInventory().RemoveMaterial(required);
            inventory.Add(required);
        } else {
            Window.get().setInfo("Nincs elég hely az inventory-dban az ellopni kívánt anyag tárolásához.");
        }

    }

    /**
     * Akkor hívódik meg, amikor a játékos el akar lopni egy felszerelést egy lebénult játékostól
     *
     * @param t a játékos akitől lopni szeretne
     * @param g a felszerelés amit el szeretne lopni
     */
    public void StealGear(Player t, Gear g) {
        if (this.ParalyzedFor() > 0) {
            Window.get().setInfo("Le vagy bénulva, nem csinálhatsz semmit!");
            return;
        }
        if (field != t.GetField()) {
            Window.get().setInfo("Nem ugyanazon a mezőn álltok!");
            return;
        }
        if (!(t.ParalyzedFor() > 0)) {
            Window.get().setInfo("A játékos akitől lopni szeretnél nincs lebénulva");
            return;
        }
        if (!t.GetInventory().Contains(g)) {
            Window.get().setInfo("A játékos akitől lopni szeretnél nem rendelkezik az felszereléssel, amit el szeretnél venni tőle");
            return;
        }

        Player p1 = t.GetField().GetPlayers().get(1);

        //ha zsákot lop el, le kell vonni a másik játékostól a felesleges materiálokat
        if (g.GetPlusSize() > 1) {
            t.GetInventory().RemoveGear(g);
            t.GetInventory().GetMaterials();
            if (t.GetInventory().GetMaterials().size() > t.GetInventory().GetMaxMaterials()) {
                int a = t.GetInventory().GetMaterials().size() - t.GetInventory().GetMaxMaterials();
                List<Material> b = new ArrayList<>();
                for (int i = 0; i < a; i++) {
                    b.add(t.GetInventory().GetMaterials().get(t.GetInventory().GetMaxMaterials() + i));
                }
                t.GetInventory().RemoveMaterials(b);
            } else {
                p1.GetInventory().Add(g);
                if (p1.GetInventory().GetGears().size() > 3) {
                    Window.get().getGame().ChooseGear();
                }
            }
        }
        //ha nem zsákot lop el
        if (t.GetInventory().GetGears().contains(g)) {
            t.GetInventory().RemoveGear(g);
            p1.GetInventory().Add(g);
            if (p1.GetInventory().GetGears().size() > 3) {
                Window.get().getGame().ChooseGear();
            }
        }
    }


    /**
     * Csökkenti a játékosra aktívan ható ágensek elbomlásáig hátra lévő idejét
     * Minden ilyen ágens time attribútumát eggyel csökkenti
     */
    public void Decrase() {
        for (var item : activeAgents)
            item.Decrase();
    }

    /**
     * Kezeli, amikor a játékos saját magára ken ágenst.
     * Ilyenkor nem használja a támadás elleni lehetőségeit, hanem mindenképp elfogadja a kenést.
     * <p>
     * A játékos bénult állapotban is megpróbálhat támadni
     */
    public void AttackOnSelf(Agent a) {
        if (this.ParalyzedFor() > 0) {
            Window.get().setInfo("Sikertelen támadás");
            return;
        }

        this.AddAgent(a);
    }

    /**
     * A játékos által kért ágenst keresi meg és törli az inventoryjából.
     * Az input hibák kezeléséért is felelős
     *
     * @return a kiválsztott ágenst adja vissza, ha erre van lehetősége
     * Ha olyan ágenst szeretnénk kérni, amivel nem rendelkezünk akkor null értéket ad vissza
     * <p>
     * A játékos olyan ágenseket is választhat, amivel nem rendelkezik az adott pillanatban, ekkor a rendszer jelzi,
     * hogy ilyet nem képes csinálni.
     */
    public Agent ChooseAgent() {
        List<Agent> agents = this.GetInventory().GetAgents();
        if (agents.size() == 0) {
            System.out.println("Nincsen felhasználható ágensed!");
            return null;
        }
        System.out.println("Milyen ágenset szeretnél magadra kenni?");
        System.out.println("1. Bénító");
        System.out.println("2. Védő");
        System.out.println("3. Felejtő");
        System.out.println("4. Vitustánc");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice = 0;
        while (choice < 1 || choice > 4) {
            try {
                choice = Integer.parseInt(reader.readLine().trim());
            } catch (Exception e) {
                System.out.println("Nem sikerult olvasni a számot.");
            }
            if (choice < 1 || choice > 4)
                System.out.println("Ilyen választási lehetőség nincs.");
            Agent a;
            if (choice == 1) {
                for (int i = 0; i < agents.size(); i++) {
                    if (agents.get(i).ParalyzedFor() > 0) {
                        a = agents.get(i);
                        agents.remove(i);
                        return a;
                    }
                }
            }
            if (choice == 2) {
                for (int i = 0; i < agents.size(); i++) {
                    if (agents.get(i).ShieldFor() > 0) {
                        a = agents.get(i);
                        agents.remove(i);
                        return a;
                    }
                }
            }
            if (choice == 3) {
                for (int i = 0; i < agents.size(); i++) {
                    if (agents.get(i).ParalyzedFor() == 0 && agents.get(i).ChoreaFor() == 0 && agents.get(i).ShieldFor() == 0) {
                        a = agents.get(i);
                        agents.remove(i);
                        a.Applied(this);
                        return null;
                    }
                }
            }
            if (choice == 4) {
                for (int i = 0; i < agents.size(); i++) {
                    if (agents.get(i).ChoreaFor() > 0) {
                        a = agents.get(i);
                        agents.remove(i);
                        return a;
                    }
                }
            }
        }
        System.out.println("Nincs ilyen ágensed!");
        return null;
    }

    /**
     * megvizsgaljuk, hogy a jatekos le van-e benulva, ha igen, akkor return
     * ha van annyi hely az inventory-ban, hogy beleférjenek a felvenni kívánt anyagok, ha nem, akkor return
     * Akkor hívjuk, ha a játékos anyagot vesz fel
     * leellenőrizzük, hogy valóban van-e annyi anyag a raktárban, mint amennyit felvenni szeretnénk
     * ha igen, akkor elvesszük a raktárból, és hozzáadjuk az inventory-hoz az anyagokat
     * egyébként return
     */
    public void CollectMaterial(List<Material> required) {
        if (this.ParalyzedFor() > 0) {
            System.out.println("Bénultan nem vehetsz fel anyagot.");
            return;
        }

        List<Material> owned = inventory.GetMaterials();
        int max = inventory.GetMaxMaterials();

        if (owned.size() <= (max - required.size())) {
            boolean isEnough = field.AquireMaterials(required);
            if (isEnough) {
                //Itt nem a laborról kellene eltávolítani a required-et?
                field.RemoveMaterials(required);
                for (Material material : required) inventory.Add(material);
                return;
            } else {
                System.out.println("Nem tudsz anyagot felvenni erről a mezőről.\n" + "Lehet, hogy nem raktáron állsz, vagy kevesebb anyag\n" + "van a raktárban, mint amennyit felvenni szeretnél. :c");
                return;
            }
        } else {
            System.out.println("Nincs elég hely az inventory-dban.");
            return;
        }
    }

    /**
     * a játékos eddig meglátogatott mezőihez ad egy új elemet.
     */
    public void addVisited(Field f) {
        if (f != null && !visited.contains(f))
            visited.add(f);
    }

    /**
     * Megtámad egy adott játékost egy kiválasztott eszközzel. A p paraméter a cél játékos, a g pedig az az eszköz, amivel támadja.
     * Ez a támadó eszköz Applied(p: Player) metódusát fogja meghívni, ami a legtöbb eszköznél nem csinál semmit, csak amivel lehet támadni (jelenleg ez csak az Axe).
     *
     * @param p A jatekos akit megtamadunk
     * @param g Az eszkoz amivel meg lesz tamadva
     */
    public void AttackGear(Player p, Gear g) {
        g.Applied(p);
    }

    public void Died() {
        Window.get().getGame().Died(this);
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
