package model;

public abstract class Agent implements java.io.Serializable {
    protected int time = 3;

    public void applied(Player p) {
    }

    public void decrase() {
        time--;
    }

    public int choreaFor() {
        return 0;
    }

    public int paralyzedFor() {
        return 0;
    }

    public int shieldFor() {
        return 0;
    }

    public void moved(Player p, Field f) {
    }

    public Agent clone() {
        return null;
    }
}
