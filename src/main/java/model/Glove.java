package model;

import view.Window;

import java.util.List;

public class Glove extends Gear {
    private int life = 3;
    private boolean used = false;

    @Override
    public boolean attacked(Player s, Player t, Agent a) {

        // Vegigmegy a tamado itemein
        List<Gear> sGears = s.getInventory().GetGears();
        for (Gear elso : sGears) {
            // Ha talal olyat mint sajat maga, akkor kiveszi a tamadotol
            if (elso.getType().equals(this.getType()) && !elso.isUsed()) {
                elso.use();
                return false;
            }
        }

        // Ha nem talalt, akkor kivedte es visszatamadott
        Window.get().setInfo("Visszatamadtak!!!!!!");
        s.gotAttacked(t, a);
        return true;
    }

    @Override
    public String getType() {
        return "glove";
    }

    @Override
    public void use() {
        used = true;
        life--;
    }

    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public void resetUsed() {
        used = false;
    }

    @Override
    public int getLife() {
        return life;
    }
}
