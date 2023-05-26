package model;


import view.Window;

public class Cloak extends Gear {
    @Override
    public boolean Attacked(Player s, Player t, Agent a) {
        boolean temp = Math.random() < 0.832;
        if(temp){
            Window.get().setInfo("A támadás sikertelen!");
        }
        return temp;
    }

    @Override
    public String GetType() {
        return "cloak";
    }
}
