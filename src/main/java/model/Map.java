package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map implements Serializable {
    public Field[][] fields;
    public List<Player> players = new ArrayList<>();
    public Player currentPlayer;

    public Map(int oldal, int jatekosok) {
        fields = new Field[oldal][oldal];
        for (int i = 0; i < jatekosok; i++) {
            players.add(new Player());
            players.get(i).setID(i);
        }

        createMap(oldal);

        Random rnd = new Random();
        for (var item : players) {
            item.SetField(fields[rnd.nextInt(fields.length)][rnd.nextInt(fields[0].length)]);
        }
    }

    /**
     * Letrehozza az adott meretu negyzetes mapet
     *
     * @param oldal az negyzet oldalanak hossza
     */
    public void createMap(int oldal) {
        // Initializ map
        for (int i = 0; i < oldal; i++)
            for (int j = 0; j < oldal; j++)
                fields[i][j] = new Field(i, j);

        // Set special fields
        List<Field> speckok = new ArrayList<>();

        // Add labs
        AddFieldSafe(speckok, new Lab(0, 0, getOblivionCode()), oldal);
        AddFieldSafe(speckok, new Lab(0, 0, getChoreaCode()), oldal);
        AddFieldSafe(speckok, new Lab(0, 0, getParalyzeCode()), oldal);
        AddFieldSafe(speckok, new Lab(0, 0, getShieldCode()), oldal);
        AddFieldSafe(speckok, new BearLab(0, 0, getShieldCode()), oldal);

        // Add storages
        for (int k = 0; k < 3; k++)
            AddFieldSafe(speckok, new Storage(0, 0), oldal);

        // Add shelters
        AddFieldSafe(speckok, new Shelter(0, 0, new Axe()), oldal);
        AddFieldSafe(speckok, new Shelter(0, 0, new Axe()), oldal);
        AddFieldSafe(speckok, new Shelter(0, 0, new Glove()), oldal);
        AddFieldSafe(speckok, new Shelter(0, 0, new Glove()), oldal);
        AddFieldSafe(speckok, new Shelter(0, 0, new Bag()), oldal);
        AddFieldSafe(speckok, new Shelter(0, 0, new Cloak()), oldal);

        // Set neighbours
        for (int s = 0; s < oldal; s++) {
            for (int o = 0; o < oldal; o++) {
                fields[s][o].setID(String.valueOf(s * oldal + o));
                if (s != 0) fields[s][o].addNeighbour(fields[s - 1][o]);
                if (s != oldal - 1) fields[s][o].addNeighbour(fields[s + 1][o]);
                if (o != 0) fields[s][o].addNeighbour(fields[s][o - 1]);
                if (o != oldal - 1) fields[s][o].addNeighbour(fields[s][o + 1]);
                // A[0][0]-s elem sima negyzet
                if (s + o % 2 != 0) {
                    // bal felso
                    if (s != 0 && o != 0) fields[s][o].addNeighbour(fields[s - 1][o - 1]);
                    // bal also
                    if (s != oldal - 1 && o != 0) fields[s][o].addNeighbour(fields[s + 1][o - 1]);
                    // jobb felso
                    if (s != 0 && o != oldal - 1) fields[s][o].addNeighbour(fields[s - 1][o + 1]);
                    // jobb also
                    if (s != oldal - 1 && o != oldal - 1) fields[s][o].addNeighbour(fields[s + 1][o + 1]);
                }
            }
        }
    }

    /**
     * Field hozzaadas
     *
     * @param speckok
     * @param f
     * @param oldal
     */
    private void AddFieldSafe(List<Field> speckok, Field f, int oldal) {
        Random rnd = new Random();
        int i, j;
        do {
            i = rnd.nextInt(oldal);
            j = rnd.nextInt(oldal);
        } while (speckok.contains(fields[i][j]));
        fields[i][j] = f;
        f.i = i;
        f.j = j;
        speckok.add(f);
    }

    /**
     * oblivion kod letrehozas
     *
     * @return
     */
    public Code getOblivionCode() {
        return new Code(new ArrayList<>() {{
            add(new Amino());
            add(new Amino());
            add(new Nukleo());
        }}, new Oblivion());
    }

    /**
     * chorea kod letrehozas
     *
     * @return
     */
    public Code getChoreaCode() {
        return new Code(new ArrayList<>() {{
            add(new Amino());
            add(new Nukleo());
            add(new Nukleo());
        }}, new Chorea());
    }

    /**
     * benito agens letrehozas
     *
     * @return
     */
    public Code getParalyzeCode() {
        return new Code(new ArrayList<>() {{
            add(new Nukleo());
            add(new Nukleo());
            add(new Nukleo());
        }}, new Paralyze());
    }


    /**
     * Shield kos letrehozas
     *
     * @return
     */
    public Code getShieldCode() {
        return new Code(new ArrayList<>() {{
            add(new Amino());
            add(new Amino());
            add(new Amino());
        }}, new Shield());
    }

    /**
     * Jelenlegi jatekos lekerese
     *
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Jelenleg a palyan levo jatekos settere
     */
    public void setCurrentPlayer(Player p) {
        currentPlayer = p;
    }
}
