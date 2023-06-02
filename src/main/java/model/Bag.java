package model;

public class Bag extends Gear {
    @Override
    public double getPlusSize() {
        return 1.2;
    }

    @Override
    public String getType() {
        return "bag";
    }
}
