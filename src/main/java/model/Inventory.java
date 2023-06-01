package model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory implements java.io.Serializable {
    private final List<Agent> agents;
    private List<Gear> gears;
    private List<Material> materials;

    public Inventory() {
        agents = new ArrayList<>();
        gears = new ArrayList<>();
        materials = new ArrayList<>();
    }

    public void Craft(Code c) {
        List<Material> required = c.GetRequired();
        List<Material> requiredBak = new ArrayList<>(c.GetRequired());
        List<Material> materialsBak = new ArrayList<>(materials);

        // Vegigmegy az elerheto es a szukseges anyagokon
        // iteratort hasznal, hogy biztonsagosan tudjon onnan kivenni, amit eppen vegigjar
        Iterator<Material> i = required.iterator();
        while (i.hasNext()) {
            Iterator<Material> j = materials.iterator();
            // Az iteratorral elerheto kovetkezo elem, ez lepteti is
            Material elso = i.next();
            while (j.hasNext()) {
                Material masodik = j.next();
                // Egyezik a ket tipus
                if (masodik.GetType().equals(elso.GetType())) {
                    // Kiveszi mindket helyrol az anyagot es kitor a belso ciklusbol,
                    // a kovetkezo szukseges elemre ugorva
                    i.remove();
                    j.remove();
                    break;
                }
            }
        }
        c.setRequired(requiredBak);
        // Ha maradt elem a szukseges listabol, akkor visszaalitjuk a nalunk levo elemeket
        if (!required.isEmpty())
            materials = materialsBak;
            // Ha nem volt, akkor nem allitjuk vissza, de az eredmeny masolatat hozzaadjuk az inventoryhoz
        else {
//            agents.add(c.GetResult().clone());
            agents.add(c.GetResult().clone());
        }
    }

    public void Add(Material m) {
        materials.add(m);
    }

    public void Add(Gear g) {
        gears.add(g);
    }

    public void RemoveGear(Gear g) {
        gears.remove(g);
    }

    public List<Agent> GetAgents() {
        return agents;
    }

    public List<Material> GetMaterials() {
        return materials;
    }

    /**
     * vegigiteral az inventory-ban tarolt anyagokon, es a tipusat a parameterul kapott anyagehoz hasonlitja
     * ha egyezest talal, akkor eltavolitja a listabol az adott anyagot, es visszater
     * biztonsag kedveert a ciklus utan is visszater
     *
     * @param m parameterul kap egy Material-t, amit el kell tavolitani az inventory-bol
     */
    public void RemoveMaterial(Material m) {
        for (int i = 0; i < materials.size(); i++) {
            if (m.GetType().equals(materials.get(i).GetType())) {
                materials.remove(i);
                return;
            }
        }
    }

    public void RemoveMaterials(List<Material> m) {
        for (var item : m)
            materials.remove(item);
    }


    /**
     * Vegigiteral az inventory-ban tarolt anyagokon, es megnezi, hogy talalhato-e kozte olyan
     * amit parameterul kapott. Ha igen, akkor true-val ter vissza.
     * Amennyiben vegigiteralt a listan anelkul, hogy egy egyezest is talalt volna
     * false-val ter vissza
     *
     * @param m Materialt kap parameterul
     * @return visszater azzal, hogy az inventory-ban talalhato-e olyan anyag, amit parameterul kapott
     */
    public boolean Contains(Material m) {
        for (Material material : materials) {
            if (m.GetType().equals(material.GetType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vegigiteral az inventory-ban tarolt felszereleseken, es megnezi, hogy talalhato-e kozte olyan
     * amit parameterul kapott. Ha igen, akkor true-val ter vissza.
     * Amennyiben vegigiteralt a listan anelkul, hogy egy egyezest is talalt volna
     * false-val ter vissza
     *
     * @param g Gear-t kap parameterul
     * @return visszater azzal, hogy az inventory-ban talalhato-e olyan felszereles, amit parameterul kapott
     */
    public boolean Contains(Gear g) {
        for (Gear gear : gears) {
            if (g.GetType().equals(gear.GetType())) {
                return true;
            }
        }
        return false;
    }


    public int GetMaxMaterials() {
        int db = 8;
        for (var item : gears)
            db *= item.GetPlusSize();
        return db;
    }

    public void RemoveAgent(Agent a) {
        agents.remove(a);
    }

    public List<Gear> GetGears() {
        return gears;
    }

    public void clearGears() {
        gears.forEach(Gear::ResetUsed);
        gears = gears.stream().filter(a -> a.getLife() > 0).collect(Collectors.toList());
    }

    public void SetGears(List<Gear> gears) {
        this.gears = gears;
    }
}
