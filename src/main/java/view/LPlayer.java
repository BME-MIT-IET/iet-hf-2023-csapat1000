package view;

import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class LPlayer extends JLabel {
    private Player player;

    /**
     * Kezeli a player labelen torteno kattintas esemenyeket.
     * Beallitja, hogy melyik kacsara kattintottak utoljara
     */
    public final static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LPlayer source = (LPlayer) e.getSource();
            Window.get().PlayerClicked(source.player);
        }
    };

    /**
     * Az osztaly konstruktora, beallitja a label megjeleneset
     * @param a a jatekban resztvevo jatekosok
     */
    public LPlayer(Player a) {
        player = a;
        ImageIcon pic = new ImageIcon(Objects.requireNonNull(LPlayer.class.getResource("/p" + (a.getID() + 1) + ".png")));
        pic.setImage(pic.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        setIcon(pic);
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
