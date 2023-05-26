package gradiens.view;

import gradiens.model.Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LCode extends JLabel {
    private Code code;

    /**
     * Kezeli a code labelen történő kattintás eseményeket.
     * Beállítja, hogy melyik kódra kattintottak utoljára
     */
    public static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LCode source = (LCode) e.getSource();
            Window.get().CodeClicked(source.code);
        }
    };

    /**
     * Az osztály konstruktora, beállítja a label megjelenését
     * @param a a játékos által birtokolt kódok fajtái
     */
    public LCode(Code a) {
        code = a;
        setText("Code: " + code.GetResult().getClass().getSimpleName());
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }
}
