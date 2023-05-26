package view;

import model.Player;

public class PMaterial extends PlayerPanel {
    @Override
    /**
     * A nyersanyagokhoz tartozo labeleknek a frissiteset vegrehajto fuggveny.
     * A parameterkent kapott jatekosnak vegigiteral a nyersanyagain, es letrehoz egy-egy
     * hozzajuk tartozo labelt.
     * @param player Az aktualis jatekos, akinek a nyersanyagait ki kell rajzolni.
     * */
    public void update(Player player) {
        removeAll();
        for (var item : player.GetInventory().GetMaterials())
            add(new LMaterial(item));
        revalidate();
        repaint();
    }
}
