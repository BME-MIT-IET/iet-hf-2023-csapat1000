package model;

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
        s = "ures";
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

    //felszereles getter
    public Gear getGear() {
        return null;
    }

    // gear eltavolitasa
    public void removeGear() {
    }

    public Code getCode() {
        return null;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }

    public List<Field> getNeighbours() {
        return neighbours;
    }

    public boolean aquireMaterials(List<Material> m) {
        return false;
    }

    public void removeMaterials(List<Material> m) {
    }

    public void addGear(Gear g) {
    }

    public boolean aquireGear(Gear g) {
        return false;
    }

    public List<Player> getPlayers() {
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

    public void clearMaterial() {
    }

    public String getPath(){
        return null;
    }

    public String getName(){
        return "ures mezore leptel. ";
    }
}
