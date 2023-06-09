package model;

public class Oblivion extends Agent {
    public Oblivion() {
        time = 1;
    }

    @Override
    public void applied(Player p) {
        p.forget();
    }

    @Override
    public Oblivion clone() {
        return new Oblivion();
    }
}
