package model;

public class Chorea extends Agent {
    @Override
    public int choreaFor() {
        return time;
    }

    @Override
    public Agent clone() {
        return new Chorea();
    }
}
