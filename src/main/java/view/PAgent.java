package view;

import model.Player;

public class PAgent extends PlayerPanel {
    @Override
    /**
     * Az agensek labeljeinek frissiteseert felelos.
     * Ezek nem a jatekosra kent aktiv agensek, hanem amiket a jatekos maga keszitett a nyersanyagaibol.
     * Vegigiteral a jatekos osszes kraftolt agensen, es letherhoz hozzajuk egy-egy nekik megfelelo labelt.
     * @param player Az aktualis jatekos, akinek az agenseit megjelenitjuk.
     * */
    public void update(Player player) {
        removeAll();
        for (var item : player.getInventory().GetAgents())
            add(new LAgent(item));
        revalidate();
        repaint();
    }
}
