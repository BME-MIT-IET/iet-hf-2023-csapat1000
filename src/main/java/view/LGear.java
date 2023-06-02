package view;

import model.Gear;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LGear extends JLabel {
    private Gear gear;

    /**
     * Kezeli a gear labelen torteno kattintas esemenyeket.
     * Beallitja, hogy melyik felszerelesre kattintottak utoljara
     */
    public final static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LGear source = (LGear) e.getSource();
            Window.get().GearClicked(source.gear);
        }
    };


    /**
     * Az osztaly konstruktora, beallitja a label megjeleneset
     * @param a a jatekos altal birtokolt felszerelesek fajtai
     */
    public LGear(Gear a) {
        gear = a;
        setText(gear.getClass().getSimpleName());
        if(gear.isUsed())
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
