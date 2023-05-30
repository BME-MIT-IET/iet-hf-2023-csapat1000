package view;

import model.Player;

import javax.swing.*;

public class PlayerPanel extends JPanel {
    public PlayerPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    public void update(Player player){ }
}
