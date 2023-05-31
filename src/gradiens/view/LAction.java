package gradiens.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LAction extends JLabel {
    private String action;

    /**
     * Kezeli az action labelen történő kattintás eseményeket.
     * Beállítja, hogy melyik akcióra kattintottak utoljára
     */
    public static final MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LAction source = (LAction) e.getSource();
            Window.get().ActionClicked(source.action);
        }
    };

    /**
     * Az osztály konstruktora, beállítja a label megjelenését
     * @param a a játékos által végezhető cselekvések fajtái
     */
    public LAction(String a) {
        action = a;
        setText(action);
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }
}
