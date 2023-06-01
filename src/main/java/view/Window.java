package view;

import controller.Game;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Window extends JFrame {
    private static Window instance;
    private Game game;
    private JPanel pMap;
    private JPanel[][] fieldPanels;
    private JLabel pInfoText;
    private ArrayList<PlayerPanel> panels;
    private JPanel pInfoPanel;
    private PlayerPanel pAgent;
    private PlayerPanel pGear;
    private PlayerPanel pAction;
    private PlayerPanel pMaterial;
    private PlayerPanel pCode;

    private final int w = 100, h = 100;

    private Gear gear;

    /**
     * Visszaadja az utoljara kattintott geart es torli minden utoljara kattintott elemet
     *
     * @return Az utoljara kattintott geart, vagy null-t, ha nem gearre volt utoljara kattintva
     */
    public Gear getGear() {
        Gear temp = gear;
        clearClicks();
        return temp;
    }

    private Player player;

    /**
     * Visszaadja az utoljara kattintott Playert es torli minden utoljara kattintott elemet
     *
     * @return Az utoljara kattintott playert, vagy null-t, ha nem playerre volt utoljara kattintva
     */
    public Player getPlayer() {
        Player temp = player;
        clearClicks();
        return temp;
    }

    private Field field;

    /**
     * Visszaadja az utoljara kattintott Fieldet es torli minden utoljara kattintott elemet
     *
     * @return Az utoljara kattintott fieldet, vagy null-t, ha nem fieldre volt utoljara kattintva
     */
    public Field getField() {
        Field temp = field;
        clearClicks();
        return temp;
    }

    private Code code;

    /**
     * Visszaadja az utoljara kattintott Codeot es torli minden utoljara kattintott elemet
     *
     * @return Az utoljara kattintott codeot, vagy null-t, ha nem codera volt utoljara kattintva
     */
    public Code getCode() {
        Code temp = code;
        clearClicks();
        return temp;
    }

    private String action;

    /**
     * Visszaadja az utoljara kattintott actiont es torli minden utoljara kattintott elemet
     *
     * @return Az utoljara kattintott actiont, vagy null-t, ha nem actionre volt utoljara kattintva
     */
    public String getAction() {
        String temp = action;
        clearClicks();
        return temp;
    }

    private Agent agent;

    /**
     * Visszaadja az utoljara kattintott actiont es torli minden utoljara kattintott elemet
     *
     * @return Az utoljara kattintott actiont, vagy null-t, ha nem actionre volt utoljara kattintva
     */
    public Agent getAgent() {
        Agent temp = agent;
        clearClicks();
        return temp;
    }

    private Material material;

    /**
     * Visszaadja az utoljara kattintott Materialt es torli minden utoljara kattintott elemet
     *
     * @return Az utoljara kattintott materialt, vagy null-t, ha nem materialra volt utoljara kattintva
     */
    public Material getMaterial() {
        Material temp = material;
        clearClicks();
        return temp;
    }

    /**
     * Beallitja az info panel szoveget
     *
     * @param text A szoveg, amire allitani kell
     */
    public void setInfo(String text) {
        pInfoText.setText(text);
    }

    /**
     * Torli az osszes utoljara kattintott elemet,
     * hogy nehogy egy regebben kattintott elemet automatikusan megegyen a kovetkezo interakcional
     */
    public void clearClicks() {
        player = null;
        field = null;
        gear = null;
        code = null;
        action = null;
        agent = null;
        material = null;
    }

    /**
     * Private konstruktor, hogy ne lehessen kozvetlen peldanyositani
     */
    private Window() {
    }

    /**
     * Visszaadja az egyetlen singleton peldanyt, ha nincs meg akkor letrehozza
     *
     * @return A letrehozott vagy eltartolt egyetlen singleton peldany
     */
    public static Window get() {
        if (instance == null)
            instance = new Window();
        return instance;
    }

    /**
     * Kulon ablakban megkerdezi a jatekost a jatek indulasakor, hogy hanyszor hanyas legyen a palya es hany jatekos legyen
     *
     * @param title Az ablak cime
     */
    public void menu(String title) {
        //formatter a jatekosok szamat varo szovegdoboznak. Feladata, hogy csak szamot, illetve hogy 2-nel kisebb, vagy 8-nal nagyobb szamokat ne lehessen beirni.
        NumberFormat format1 = NumberFormat.getIntegerInstance();
        NumberFormatter formatter1 = new NumberFormatter(format1);
        formatter1.setValueClass(Integer.class);
        formatter1.setMinimum(2);
        formatter1.setMaximum(8);
        formatter1.setAllowsInvalid(false);

        setTitle(title);
        JFrame elso = new JFrame();
        elso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        elso.setSize(500, 300);
        elso.setLocation(500, 200);

        //az ablak 1 panelbol fog allni, amiben BoxLayout elrendezessel szerepelnek a tartalmak
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.setBorder(new EmptyBorder(new Insets(30, 10, 30, 10)));

        //label hozzaadasa
        JLabel label = new JLabel();
        label.setText("Add meg, hogy hany jatekos szeretne jatszani a jatekot (min 2, max 8)");
        panel.add(label);

        //szovegdoboz ahol megadjuk, hogy hany jatekossal szeretnenk jatszani
        JFormattedTextField field = new JFormattedTextField(formatter1);
        field.setColumns(4);
        panel.add(field);

        //start gomb, hatasara a jatek tabla ablaka ugrik fel
        JButton button = new JButton("Indit");
        button.addActionListener(e -> {
            int jatekosok = (int) field.getValue();
            setup(title, jatekosok);     //es atadjuk a jateknak, hogy milyen adatokat kaptunk a felhasznalotol
            get().setVisible(true);
            elso.setVisible(false);
        });
        panel.add(button);
        elso.add(panel);
        elso.setVisible(true);
    }

    /**
     * @param title     - ez a cim fog szerepelni az ablak tetejen
     * @param jatekosok - az menu ablakabol megkaptuk, hogy hany jatekos fog jatszani
     */
    public void setup(String title, int jatekosok) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        //tulajdonsagok hozzaadasa
        setSize(1200, 800);
        setLocation(0, 0);

        //uj jatek letrehozasa
        game = new Game(jatekosok); //letrehozzuk a game osztalyt
        //Init fuggvenyek meghivasa
        pMapInit(layout);                       //
        panelsInit(layout);
        game.start();
        //updateli a mapet
        update();
    }

    /**
     * A terkep inicializalasa. Beallitja a mereteket, hozzaadja a fieldeket.
     * @param layout
     */
    private void pMapInit(GridBagLayout layout) {
        pMap = new JPanel();                        //inicializaljuk a terkep paneljat
        GridBagConstraints c = new GridBagConstraints();
        //tulajdonsagok hozzaadasa
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = GridBagConstraints.REMAINDER;
        c.weightx = 4;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        layout.setConstraints(pMap, c);


        layout = new GridBagLayout();
        pMap.setLayout(layout);
        add(pMap);                                  //hozzaadjuk a panelt az elkeszitett elrendezessel az ablakhoz
        Field[][] fields = game.getMap().fields;
        fieldPanels = new JPanel[fields.length][fields[0].length];
        c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        GridBagConstraints cLField = new GridBagConstraints();

        //fieldek elhelyezese
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fieldPanels[i][j] = new JPanel();
                c.gridx = i;
                c.gridy = j;
                if ((i + j) % 2 == 0)
                    fieldPanels[i][j].setBackground(Color.RED);
                layout.setConstraints(fieldPanels[i][j], c);
                pMap.add(fieldPanels[i][j]);

                fieldPanels[i][j].setLayout(new GridBagLayout());
                fieldPanels[i][j].add(new LField(fields[i][j], w, h), cLField);

                if (game.getMap().currentPlayer.getField() == fields[i][j]) {
                    fieldPanels[i][j].remove(0);
                    fieldPanels[i][j].add(new LPlayer(game.getMap().currentPlayer));
                }
            }
        }
    }

    /**
     * A terkep updateelesehez szukseges fuggveny
     */
    public void mapUpdate() {
        Field[][] fields = game.getMap().fields;

        for (int i = 0; i < game.getCurrentPlayer().getVisited().size(); i++) {
            int x = game.getCurrentPlayer().getVisited().get(i).i;
            int y = game.getCurrentPlayer().getVisited().get(i).j;
            fieldPanels[x][y].removeAll();
            fieldPanels[x][y].add(new LField(fields[x][y], w, h), new GridBagConstraints());
            if (game.getMap().currentPlayer.getField() == fields[x][y]) {
                fieldPanels[x][y].removeAll();
                // Hozzaad minden mezon levo jatekost, egymas melle
                for (var player : fields[x][y].GetPlayers())
                    fieldPanels[x][y].add(new LPlayer(player));
            }

        }
    }

    /**
     * A terkep updateelesehez szukseges fuggveny. Jatekosok hozzaadasa
     */
    public void mapUpdateKorvege() {
        Field[][] fields = game.getMap().fields;

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fieldPanels[i][j].removeAll();
                fieldPanels[i][j].add(new LField(fields[i][j], w, h), new GridBagConstraints());
                if (game.getMap().currentPlayer.getField() == fields[i][j]) {
                    fieldPanels[i][j].removeAll();
                    // Hozzaad minden mezon levo jatekost, egymas melle
                    for (var player : fields[i][j].GetPlayers())
                        fieldPanels[i][j].add(new LPlayer(player));
                }
            }
        }
    }

    public JPanel[][] getFieldPanels() {
        return fieldPanels;
    }

    /**
     * A panelek incializalasahoz szukseges fuggveny
     * @param layout
     */
    private void panelsInit(GridBagLayout layout) {
        pInfoPanel = new JPanel();

        panels = new ArrayList<>();

        //panelek hozzaadasa
        panels.add(pAction = new PAction());
        panels.add(pAgent = new PAgent());
        panels.add(pGear = new PGear());
        panels.add(pMaterial = new PMaterial());
        panels.add(pCode = new PCode());

        GridBagConstraints c = new GridBagConstraints();


        //info text hozzaadasa
        pInfoText = new JLabel();

        //tulajdonsagok hozzaadasa
        pInfoPanel.setBackground(new Color(173, 216, 230));
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.6;
        c.fill = GridBagConstraints.BOTH;
        layout.setConstraints(pInfoPanel, c);
        add(pInfoPanel);
        pInfoPanel.add(pInfoText);

        c.weighty = 1;
        for (int i = 0; i < panels.size(); i++) {
            c.gridy = i + 1;
            // Torlendo
            Color col = new Color((i + 1) * 255 / panels.size());
            panels.get(i).setBackground(col);
            // Torlendo vege
            layout.setConstraints(panels.get(i), c);

            add(panels.get(i));
        }
    }

    /**
     * Fuggveny a mezo kattintasahoz
     * @param a a mezo amire kattintott a jatekos
     */
    public void FieldClicked(Field a) {
        clearClicks();
        field = a;
        if (game.getState() == Thread.State.WAITING)
            game.interrupt();
    }

    /**
     * Fuggveny a jatekos kattintasahoz
     * @param a a jatekos amire kattintott a jatekos
     */
    public void PlayerClicked(Player a) {
        clearClicks();
        player = a;
        if (game.getState() == Thread.State.WAITING)
            game.interrupt();
    }

    /**
     * Fuggveny az agens kattintasahoz
     * @param a az agens amire kattintott a jatekos
     */
    public void AgentClicked(Agent a) {
        clearClicks();
        agent = a;
        if (game.getState() == Thread.State.WAITING)
            game.interrupt();
    }

    /**
     * Fuggveny az akciok kattintasahoz
     * @param a az akcio amire kattintott a jatekos
     */
    public void ActionClicked(String a) {
        clearClicks();
        action = a;
        if (game.getState() == Thread.State.WAITING)
            game.interrupt();
    }
    /**
     * Fuggveny az anyag kattintasahoz
     * @param a az anyag amire kattintott a jatekos
     */
    public void MaterialClicked(Material a) {
        clearClicks();
        material = a;
        if (game.getState() == Thread.State.WAITING) {
            game.interrupt();
        }
    }
    /**
     * Fuggveny a kod kattintasahoz
     * @param a a kod amire kattintott a jatekos
     */
    public void CodeClicked(Code a) {
        clearClicks();
        code = a;
        if (game.getState() == Thread.State.WAITING)
            game.interrupt();
    }

    /**
     * Fuggveny a felszereles kattintasahoz
     * @param a a fegyver amire kattintott a jatekos
     */
    public void GearClicked(Gear a) {
        clearClicks();
        gear = a;
        if (game.getState() == Thread.State.WAITING)
            game.interrupt();
    }

    /**
     * updateli a paneleket a jelenlegi jatekos tulajdonsagainak megfeleloen
     */
    public void update() {
        Player player = game.getCurrentPlayer();
        for (var item : panels)
            item.update(player);
    }

    /**
     * A parameterkent kapott jatekosnak kilistazza az eszkozeit, hogy a tamado
     * kivalaszthassa, mit szeretne ellopni.
     *
     * @param target Jatekos, akinek ellopjak az eszkozeit.
     */
    public void stealGearSet(Player target) {
        pGear.update(target);
    }

    /**
     * A paneleket frissiti, hogy kirajzoljon egy aminosavat es egy nukleotidot.
     */
    public void chooseMaterial() {
        Player temp = new Player();
        temp.addMaterial(new Nukleo());
        temp.addMaterial(new Amino());
        pMaterial.update(temp);
    }

    /**
     * A paneleket frissiti, kirajzolja egy jatekos nyersanyagait.
     *
     * @param p A jatekos, akinek a nyersanyagait ki kell rajzolni.
     */
    public void materialSet(Player p) {
        pMaterial.update(p);
    }

    /**
     * Jatek gettere
     *
     * @return jatek
     */
    public Game getGame() {
        return game;
    }
}
