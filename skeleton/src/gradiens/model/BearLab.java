package gradiens.model;

import java.util.ArrayList;
import java.util.List;

public class BearLab extends Lab {

    public BearLab(int _i, int _j, Code c) {
        super(_i, _j, c);
    }

    @Override
    public void AddPlayer(Player p) {
        super.AddPlayer(p);
        List<Gear> backup = new ArrayList<>(p.GetInventory().GetGears());
        Player temp = new Player();
        temp.GetInventory().Add(new Glove());
        temp.GetInventory().Add(new Glove());
        temp.GetInventory().Add(new Glove());
        p.GotAttacked(temp, new Bear());
        p.GetInventory().SetGears(backup);
    }

    @Override
    public String getPath() {
        return "bearlab.png";
    }
}
