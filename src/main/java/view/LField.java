package view;

import model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LField extends JLabel {
    private Field field;

    /**
     * Kezeli a field labelen történő kattintás eseményeket.
     * Beállítja, hogy melyik mezőre kattintottak utoljára
     */
    public static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LField source = (LField) e.getSource();
            Window.get().FieldClicked(source.field);
        }
    };

    //A mezők megjelenítéséhez használt különböző képek
    private static ImageIcon negy = new ImageIcon("4.png");
    private static ImageIcon nyolc = new ImageIcon("8.png");
    private static ImageIcon negyF = new ImageIcon("4f.png");
    private static ImageIcon nyolcF = new ImageIcon("8f.png");

    /**
     * Az osztály konstruktora.
     *
     * @param a a mező amelyre az adott label rákerül
     * @param w a label szélessége
     * @param h a label hosszúsága
     */
    public LField(Field a, int w, int h) {
        boolean lathato = Window.get().getGame().getCurrentPlayer().getVisited().contains(a);

        negy.setImage(negy.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        nyolc.setImage(nyolc.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        negyF.setImage(negyF.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        nyolcF.setImage(nyolcF.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        field = a;

        if (lathato) {
            // Lathato, sima mezo
            if (field.getPath() == null) {
                if ((field.i + field.j) % 2 == 1) setIcon(nyolc);
                else setIcon(negy);
            }
            // Lathato, nem sima mezo
            else {
                String path = field.getPath();
                ImageIcon temp = new ImageIcon(path);
                temp.setImage(temp.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
                setIcon(temp);
            }
        } else {
            if ((field.i + field.j) % 2 == 1) setIcon(nyolcF);
            else setIcon(negyF);
        }

        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
