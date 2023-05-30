package view;

import model.Player;

public class PAction extends PlayerPanel {
    @Override
    /**
     * A labelek frissitiseert felelös.
     * Eltavolitja az osszes meglevo labelt, es a jatekban szereplo osszes lehetseges muvelethez
     * lethehoz egy labelt.
     * @param palyer Az aktualis jatekos.
     * */
    public void update(Player player) {
        removeAll();
        add(new LAction("tamadas"));
        add(new LAction("fegyverlopas"));
        add(new LAction("anyaglopas"));
        add(new LAction("kod letapogatasa"));
        add(new LAction("felszereles felvetele"));
        add(new LAction("anyag felvetele"));
        add(new LAction("craftolas"));
        add(new LAction("kor vege"));
        revalidate();
        repaint();
    }
}
