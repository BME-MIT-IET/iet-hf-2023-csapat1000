package gradiens.model;

public abstract class Gear implements java.io.Serializable {
    public boolean Attacked(Player s, Player t, Agent a) {
        return false;
    }

    public double GetPlusSize() {
        return 1;
    }

    public String GetType() {
        return null;
    }

    public void Applied(Player p) {

    }

    public void Use() {
    }

    public int getLife() {
        return 1;
    }

    public boolean IsUsed() {
        return false;
    }

    public void ResetUsed() {
    }
}
