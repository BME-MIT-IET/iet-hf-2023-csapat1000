package controller;

import model.Map;
import model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import static org.mockito.Mockito.when;


public class TestGame {

    private final static int numOfPlayers = 2;
    private static Game game;
    private static Map map;

    @BeforeEach
    public void setupGame() {
        game = new Game(numOfPlayers);
        map = game.getMap();
    }

    @Test
    public void testGameInit() {
        //Map letrejott-e
        assertNotNull(map);

        //Jatekosok ellenorzese
        assertEquals(2,map.players.size());
        assertNotEquals(3,map.players.size());

        //Alapertelmezetten az elso jatekos jon
        assertEquals(0, game.getCurrentPlayer().getID());
    }

    @Test
    public void testPlayerDying() {
        //Az elso jatekos halalanak szimulalasa
        game.Died(map.players.get(0));

        assertEquals(1,map.players.size());
    }

    @Test
    public void testInvalidPlayerDying() {
        //Ez a jatekos nem szerepel az aktiv jatekosok kozott, igy nem tud meghalni
        Player invalidPlayer = new Player();
        invalidPlayer.setID(3);

        game.Died(invalidPlayer);
        assertEquals(2,map.players.size());
    }


}
