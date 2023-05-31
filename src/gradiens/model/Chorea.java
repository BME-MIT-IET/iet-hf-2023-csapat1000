package gradiens.model;

public class Chorea extends Agent {
    @Override
    public int ChoreaFor() {
        return time;
    }

    @Override
    public Agent clone() {
        return new Chorea();
    }
}
