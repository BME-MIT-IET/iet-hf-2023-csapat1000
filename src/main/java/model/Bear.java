package model;

public class Bear extends Agent {
    @Override
    public void decrase() {
    }

    @Override
    public int choreaFor() {
        return 1;
    }

    @Override
    public void moved(Player p, Field f) {
        f.clearMaterial();
        for (var target : f.getPlayers()) {
            if (target != p)
                p.attack(target, new Bear());
        }
    }

    @Override
    public Agent clone() {
        return new Bear();
    }
}

