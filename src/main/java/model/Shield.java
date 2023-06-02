package model;

public class Shield extends Agent {
    @Override
    public int shieldFor() {
        return time;
    }

    @Override
    public Shield clone() {
        return new Shield();
    }
}
