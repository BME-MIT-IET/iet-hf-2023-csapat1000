package model;

public class Paralyze extends Agent {
    @Override
    public int ParalyzedFor() {
        return time;
    }

    @Override
    public Agent clone() {
        return new Paralyze();
    }
}