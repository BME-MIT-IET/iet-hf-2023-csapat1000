package view;

import model.Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LCode extends JLabel {
    private Code code;

    /**
     * Kezeli a code labelen torteno kattintas esemenyeket.
     * Beallitja, hogy melyik kodra kattintottak utoljara
     */
    public static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LCode source = (LCode) e.getSource();
            Window.get().CodeClicked(source.code);
        }
    };

    /**
     * Az osztaly konstruktora, beallitja a label megjeleneset
     * @param a a jatekos altal birtokolt kodok fajtai
     */
    public LCode(Code a) {
        code = a;
        setText("Code: " + code.GetResult().getClass().getSimpleName());
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }
}
