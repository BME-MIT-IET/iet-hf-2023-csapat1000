package model;

public class Axe extends Gear {
    private boolean used = false;

    @Override
    public void applied(Player p) {
        if (!used) {
            p.died();
            used = true;
        }
    }

    @Override
    public String getType() {
        return "axe";
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}
