package gradiens.model;

import java.util.ArrayList;
import java.util.List;

public class Field implements java.io.Serializable {
    private final List<Player> players;
    private final List<Field> neighbours;
    private String ID;
    public int i, j;
    public String s;


    /**
     * Fiels konstruktor
     * @param _i hanyadik sorban van a pajan
     * @param _j hanyadik oszlopban van a pajan
     */
    public Field(int _i, int _j) {
        players = new ArrayList<>();
        neighbours = new ArrayList<>();
        i = _i;
        j = _j;
        s = "Üres";
    }

    String getStringn(){
        return s;
    }

    @Override
    public String toString() {
        return ID;
    }

    public void setID(String id) {
        ID = id;
    }

    //felszerelés getter
    public Gear GetGear() {
        return null;
    }

    // gear eltávolítása
    public void RemoveGear() {
    }

    public Code GetCode() {
        return null;
    }

    public void AddPlayer(Player p) {
        players.add(p);
    }

    public void RemovePlayer(Player p) {
        players.remove(p);
    }

    public List<Field> GetNeighbours() {
        return neighbours;
    }

    public boolean AquireMaterials(List<Material> m) {
        return false;
    }

    public void RemoveMaterials(List<Material> m) {
    }

    public void AddGear(Gear g) {
    }

    public boolean AquireGear(Gear g) {
        return false;
    }

    public List<Player> GetPlayers() {
        return players;
    }

    public List<Material> getMaterials() {
        return null;
    }

    /**
     * A parameterkent kapott mezot felveszi a mezo szomszedai koze
     */
    public void addNeighbour(Field f) {
        neighbours.add(f);
    }

    public void ClearMaterial() {
    }

    public String getPath(){
        return null;
    }

    public String getName(){
        return "Üres mezőre léptél. ";
    }
}
