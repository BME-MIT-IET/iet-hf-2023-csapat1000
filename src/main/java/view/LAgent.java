package view;

import model.Agent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LAgent extends JLabel {
    private Agent agent;

    /**
     * Kezeli az agent labelen torteno kattintas esemenyeket.
     * Beallitja, hogy melyik agensre kattintottak utoljara
     */
    public final static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LAgent source = (LAgent) e.getSource();
            Window.get().AgentClicked(source.agent);
        }
    };

    /**
     * Az osztaly konstruktora, beallitja a label megjeleneset
     * @param a a jatekos altal birtokolt agensek fajtai
     */
    public LAgent(Agent a) {
        agent = a;
        setText(agent.getClass().getSimpleName());
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }
}
