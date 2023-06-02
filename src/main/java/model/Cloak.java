package model;


import view.Window;

public class Cloak extends Gear {
    @Override
    public boolean attacked(Player s, Player t, Agent a) {
        boolean temp = Math.random() < 0.832;
        if(temp){
            Window.get().setInfo("A tamadas sikertelen!");
        }
        return temp;
    }

    @Override
    public String getType() {
        return "cloak";
    }
}
