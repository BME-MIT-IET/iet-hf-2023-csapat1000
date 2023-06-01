package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestField {
    private Field normalField;
    private Field normalField2;

    @BeforeEach
    public void setupFields() {
        normalField = new Field(0,0);
        normalField2 = new Field(0,1);
    }

    private void setupNeighbours() {
        normalField.addNeighbour(normalField2);
        normalField2.addNeighbour(normalField);
    }

    private void putPlayerOnField(Field f) {
        Player p = new Player();
        p.SetField(f);
    }

    @Test
    public void testNormalFieldOnInit() {
        //A normal mezon nincs sem felszereles, sem kod
        assertNull(normalField.GetGear());
        assertNull(normalField.GetCode());

        //Mivel nem a map hozta letre, nincsen szomszedja sem
        assertEquals(0,normalField.GetNeighbours().size());

        //Alapertelmezetten nem all rajta jatekos
        assertEquals(0,normalField.GetPlayers().size());
    }

    @Test
    public void testPlayerSteppingOnField() {
        //Mezok szomszedossaganak beallitasa
        setupNeighbours();

        //Jatekos elhelyezese a mezore
        putPlayerOnField(normalField);

        //Lepes szimulalasa
        Player playerOnField = normalField.GetPlayers().get(0);
        playerOnField.Move(normalField2);

        assertEquals(0,normalField.GetPlayers().size());
        assertEquals(1,normalField2.GetPlayers().size());
    }
}
