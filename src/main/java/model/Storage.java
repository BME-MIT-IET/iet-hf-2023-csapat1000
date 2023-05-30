package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Storage extends Field {
    private final List<Material> materials;

    /**
     * A raktar konstruktora, letrehozza az anyagok listajat
     * Es feltolti 8 anyaggal, 4 aminosavval, es 4 nukleotiddal
     */
    public Storage(int _i, int _j) {
        super(_i, _j);
        materials = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            materials.add(new Amino());
            materials.add(new Nukleo());
        }
    }

    @Override
    public List<Material> getMaterials() {
        return materials;
    }

    /**
     * Masolatot keszit a felvenni kivant es a raktarban talalhato anyagok listajarol
     * Ezeket iteral végig
     * Iteratort hasznal, hogy biztonsagosan tudjon onnan kivenni, amit eppen vegigjar
     * Ha egyezik a ket tipus, kiveszi mindket helyrol az anyagot es kitor a belso ciklusbol, majd a következő required elemre ugrik
     * Ha nem, akkor a  kovetkezo raktarban talalhato anyagra ugrik
     * Ha maradt elem a required listabol, akkor false-val ter vissza
     * Ha nem maradt, akkor true-val térünk vissza
     *
     * @param m parameterkent kapja a raktarbol a jatekos altal kesobb kivenni kivant anyagok listajat
     * @return es visszater egy boolean valtozoval, attol fuggoen, hogy talalhatoak-e a raktarban a parameterkent kapott anyagoknak megfelelo anyagok
     */
    @Override
    public boolean AquireMaterials(List<Material> m) {
        List<Material> required = new ArrayList<>(m);
        List<Material> owned = new ArrayList<>(materials);

        Iterator<Material> i = required.iterator();
        while (i.hasNext()) {
            Material elso = i.next();
            Iterator<Material> j = owned.iterator();
            while (j.hasNext()) {
                Material masodik = j.next();
                if (masodik.GetType().equals(elso.GetType())) {
                    i.remove();
                    j.remove();
                    break;
                }
            }
        }
        return required.isEmpty();
    }

    /**
     * Feladata a parameterkent kapott anyagok listajanak megfelelo mennyisegu anyagot eltavolitani a raktarbol
     * Masolatot keszit az eltavolitani kivant es a raktarban talalhato anyagok listajarol
     * Ezeket iteral végig
     * Iteratort hasznal, hogy biztonsagosan tudjon onnan kivenni, amit eppen vegigjar
     * Ha egyezik a ket tipus, kiveszi mindket helyrol az anyagot es kitor a belso ciklusbol, majd a következő eltavolitando elemre ugrik
     * Ha nem, akkor a  kovetkezo raktarban talalhato anyagra ugrik
     *
     * @param rid parameterkent kapja a raktarbol a jatekos altal eltavolitani kivant anyagok listajat
     */
    @Override
    public void RemoveMaterials(List<Material> rid) {
        List<Material> required = new ArrayList<>(rid);

        Iterator<Material> i = required.iterator();
        while (i.hasNext()) {
            Material elso = i.next();
            Iterator<Material> j = materials.iterator();
            while (j.hasNext()) {
                Material masodik = j.next();
                if (masodik.GetType().equals(elso.GetType())) {
                    i.remove();
                    j.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void ClearMaterial() {
        materials.clear();
    }

    @Override
    public String getPath() {
        return "storage.png";
    }

    @Override
    public String getName(){
        return "Raktárra léptél. ";
    }
}
