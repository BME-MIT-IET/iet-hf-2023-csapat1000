package view;

import model.Gear;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LGear extends JLabel {
    private Gear gear;

    /**
     * Kezeli a gear labelen történő kattintás eseményeket.
     * Beállítja, hogy melyik felszerelésre kattintottak utoljára
     */
    public static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LGear source = (LGear) e.getSource();
            Window.get().GearClicked(source.gear);
        }
    };


    /**
     * Az osztály konstruktora, beállítja a label megjelenését
     * @param a a játékos által birtokolt felszerelések fajtái
     */
    public LGear(Gear a) {
        gear = a;
        setText(gear.getClass().getSimpleName());
        if(gear.IsUsed())
        {
            setFont(this.getFont().deriveFont(Font.ITALIC));
            setForeground(Color.red);
        }
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }

    ;
}
