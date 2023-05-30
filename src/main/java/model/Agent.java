package model;

public abstract class Agent implements java.io.Serializable {
    protected int time = 3;

    public void Applied(Player p) {
    }

    public void Decrase() {
        time--;
    }

    public int ChoreaFor() {
        return 0;
    }

    public int ParalyzedFor() {
        return 0;
    }

    public int ShieldFor() {
        return 0;
    }

    public void Moved(Player p, Field f) {
    }

    public Agent clone() {
        return null;
    }
}
