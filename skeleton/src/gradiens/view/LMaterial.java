package gradiens.view;

import gradiens.model.Material;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LMaterial extends JLabel {

    private Material material;

    /**
     * Kezeli a material labelen történő kattintás eseményeket.
     * Beállítja, hogy melyik nyersanyagra kattintottak utoljára
     */
    public static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LMaterial source = (LMaterial) e.getSource();
            Window.get().MaterialClicked(source.material);
        }
    };

    /**
     * Az osztály konstruktora, beállítja a label megjelenését
     * @param a a játékos által birtokolt nyersanyagok
     */
    public LMaterial(Material a) {
        material = a;
        setText(material.getClass().getSimpleName());
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }
}
