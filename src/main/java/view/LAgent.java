package view;

import model.Agent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LAgent extends JLabel {
    private Agent agent;

    /**
     * Kezeli az agent labelen történő kattintás eseményeket.
     * Beállítja, hogy melyik ágensre kattintottak utoljára
     */
    public static MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            LAgent source = (LAgent) e.getSource();
            Window.get().AgentClicked(source.agent);
        }
    };

    /**
     * Az osztály konstruktora, beállítja a label megjelenését
     * @param a a játékos által birtokolt ágensek fajtái
     */
    public LAgent(Agent a) {
        agent = a;
        setText(agent.getClass().getSimpleName());
        addMouseListener(adapter);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.WHITE);
    }
}
