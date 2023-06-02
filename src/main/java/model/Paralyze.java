package model;

public class Paralyze extends Agent {
    @Override
    public int paralyzedFor() {
        return time;
    }

    @Override
    public Agent clone() {
        return new Paralyze();
    }
}