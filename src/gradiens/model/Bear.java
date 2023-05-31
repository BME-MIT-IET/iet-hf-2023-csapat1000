package gradiens.model;

public class Bear extends Agent {
    @Override
    public void Decrase() {
    }

    @Override
    public int ChoreaFor() {
        return 1;
    }

    @Override
    public void Moved(Player p, Field f) {
        f.ClearMaterial();
        for (var target : f.GetPlayers()) {
            if (target != p)
                p.Attack(target, new Bear());
        }
    }

    @Override
    public Agent clone() {
        return new Bear();
    }
}

