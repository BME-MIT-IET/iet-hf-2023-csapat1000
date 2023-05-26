package gradiens.model;

public class Shield extends Agent {
    @Override
    public int ShieldFor() {
        return time;
    }

    @Override
    public Shield clone() {
        return new Shield();
    }
}
