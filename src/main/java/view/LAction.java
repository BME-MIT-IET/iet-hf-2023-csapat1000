package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LAction extends JLabel {
    private String action;

    /**
     * Kezeli az action labelen torteno kattintas esemenyeket.
     * Beallitja, hogy melyik akciora kattintottak utoljara
     */
    public static final MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LAction source = (LAction) e.getSource();
            Window.get().ActionClicked(source.action);
        }
    };

    /**
     * Az osztaly konstruktora, beallitja a label megjeleneset
     * @param a a jatekos altal vegezheto cselekvesek fajtai
     */
    public LAction(String a) {
        action = a;
        setText(action);
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }
}
