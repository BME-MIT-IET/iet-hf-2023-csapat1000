package model;

import java.util.ArrayList;
import java.util.List;

public class BearLab extends Lab {

    public BearLab(int _i, int _j, Code c) {
        super(_i, _j, c);
    }

    @Override
    public void AddPlayer(Player p) {
        super.AddPlayer(p);
        List<Gear> backup = new ArrayList<>(p.getInventory().GetGears());
        Player temp = new Player();
        temp.getInventory().Add(new Glove());
        temp.getInventory().Add(new Glove());
        temp.getInventory().Add(new Glove());
        p.gotAttacked(temp, new Bear());
        p.getInventory().SetGears(backup);
    }

    @Override
    public String getPath() {
        return "bearlab.png";
    }
}
