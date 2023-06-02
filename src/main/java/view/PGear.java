package view;

import model.Player;

public class PGear extends PlayerPanel {
    @Override
    /**
     * Felelossege a felszerelesekhez tartozo labelek frissiteset kezelni.
     * Vegigiteral a jatekos osszes felszerelesen, es letrehoz egy hozzajuk tartozo labelt.
     * @param player Az aktualis jatekos, akinek a felszereleseit meg kell jeleniteni.
     * */
    public void update(Player player) {
        removeAll();
        for (var item : player.getInventory().GetGears())
            add(new LGear(item));
        revalidate();
        repaint();
    }
}
