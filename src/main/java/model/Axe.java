package model;

public class Axe extends Gear {
    private boolean used = false;

    @Override
    public void Applied(Player p) {
        if (!used) {
            p.Died();
            used = true;
        }
    }

    @Override
    public String GetType() {
        return "axe";
    }

    @Override
    public boolean IsUsed() {
        return used;
    }
}
