package model;

public class Shelter extends Field {
    private Gear gear;

    public Shelter(int _i, int _j, Gear g) {
        super(_i, _j);
        gear = g;
    }

    @Override
    public boolean AquireGear(Gear g) {
        return g.GetType().equals(gear.GetType());
    }

    @Override
    public Gear GetGear() {
        return this.gear;
    }

    @Override
    public void RemoveGear() {
        gear = null;
    }

    @Override
    public void AddGear(Gear g) {
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
