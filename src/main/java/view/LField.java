package view;

import model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class LField extends JLabel {
    private Field field;

    /**
     * Kezeli a field labelen torteno kattintas esemenyeket.
     * Beallitja, hogy melyik mezore kattintottak utoljara
     */
    public static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LField source = (LField) e.getSource();
            Window.get().FieldClicked(source.field);
        }
    };

    //A mezok megjelenitesehez hasznalt kulonbozo kepek
    private static ImageIcon negy = new ImageIcon(Objects.requireNonNull(LField.class.getResource("/4.png")));
    private static ImageIcon nyolc = new ImageIcon(Objects.requireNonNull(LField.class.getResource("/8.png")));
    private static ImageIcon negyF = new ImageIcon(Objects.requireNonNull(LField.class.getResource("/4f.png")));
    private static ImageIcon nyolcF = new ImageIcon(Objects.requireNonNull(LField.class.getResource("/8f.png")));

    /**
     * Az osztaly konstruktora.
     *
     * @param a a mezo amelyre az adott label rakerul
     * @param w a label szelessege
     * @param h a label hosszusaga
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
                ImageIcon temp = new ImageIcon(Objects.requireNonNull(LField.class.getResource("/" + path)));
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
