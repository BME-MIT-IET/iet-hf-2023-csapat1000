package model;

import view.Window;

import java.util.List;

public class Glove extends Gear {
    private int life = 3;
    private boolean used = false;

    @Override
    public boolean Attacked(Player s, Player t, Agent a) {

        // Vegigmegy a tamado itemein
        List<Gear> sGears = s.GetInventory().GetGears();
        for (Gear elso : sGears) {
            // Ha talal olyat mint sajat maga, akkor kiveszi a tamadotol
            if (elso.GetType().equals(this.GetType()) && !elso.IsUsed()) {
                elso.Use();
                return false;
            }
        }

        // Ha nem talalt, akkor kivedte es visszatamadott
        Window.get().setInfo("Visszatámadtak!!!!!!");
        s.GotAttacked(t, a);
        return true;
    }

    @Override
    public String GetType() {
        return "glove";
    }

    @Override
    public void Use() {
        used = true;
        life--;
    }

    @Override
    public boolean IsUsed() {
        return used;
    }

    @Override
    public void ResetUsed() {
        used = false;
    }

    @Override
    public int getLife() {
        return life;
    }
}
