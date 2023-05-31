package view;

import model.Material;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LMaterial extends JLabel {

    private Material material;

    /**
     * Kezeli a material labelen torteno kattintas esemenyeket.
     * Beallitja, hogy melyik nyersanyagra kattintottak utoljara
     */
    public static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LMaterial source = (LMaterial) e.getSource();
            Window.get().MaterialClicked(source.material);
        }
    };

    /**
     * Az osztaly konstruktora, beallitja a label megjeleneset
     * @param a a jatekos altal birtokolt nyersanyagok
     */
    public LMaterial(Material a) {
        material = a;
        setText(material.getClass().getSimpleName());
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }
}
