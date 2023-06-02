package model;

public class Shelter extends Field {
    private Gear gear;

    public Shelter(int _i, int _j, Gear g) {
        super(_i, _j);
        gear = g;
    }

    @Override
    public boolean aquireGear(Gear g) {
        return g.getType().equals(gear.getType());
    }

    @Override
    public Gear getGear() {
        return this.gear;
    }

    @Override
    public void removeGear() {
        gear = null;
    }

    @Override
    public void addGear(Gear g) {
        if (gear != null)
            return;
        gear = g;
    }

    @Override
    public String getPath() {
        return "shelter.png";
    }

    @Override
    public String getName() {
        return "ovohelyre leptel. ";
    }
}
