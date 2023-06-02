package model;

public abstract class Gear implements java.io.Serializable {
    public boolean attacked(Player s, Player t, Agent a) {
        return false;
    }

    public double getPlusSize() {
        return 1;
    }

    public String getType() {
        return null;
    }

    public void applied(Player p) {

    }

    public void use() {
    }

    public int getLife() {
        return 1;
    }

    public boolean isUsed() {
        return false;
    }

    public void resetUsed() {
    }
}
