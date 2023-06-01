package view;

import model.Player;

public class PCode extends PlayerPanel {
    @Override
    /**
     * A megtanult kodokat kezelo labeleket frissito fuggveny.
     * Vegigiteral a jatekos minden megtanult kodjan, es letrehoz hozzasjuk egy-egy labelt.
     * @param player Az aktualis jatekos, akinek a megtanult kodjait meg kell jeleniteni.
     * */
    public void update(Player player) {
        removeAll();
        for (var item : player.GetKnownCodes())
            add(new LCode(item));
        revalidate();
        repaint();
    }
}
