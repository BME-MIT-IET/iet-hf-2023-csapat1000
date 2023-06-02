package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestGear {
    private Glove glove;
    private Axe axe;
    private Player p1;
    private Player p2;

    @BeforeEach
    public void setup() {
        p1 = mock(Player.class);
        p2 = mock(Player.class);

        glove = new Glove();
        axe = new Axe();
    }

    @Test
    public void testGloveOnInint() {
        //Alapallapot
        assertEquals(3,glove.getLife());
        assertFalse(glove.IsUsed());
    }

    @Test
    public void testGloveOnSuccessfulUse() {
        Agent agent = mock(Agent.class);
        Inventory p1Inv = mock(Inventory.class);

        List<Gear> gears = new ArrayList<>();
        gears.add(glove);

        when(p1.GetInventory()).thenReturn(p1Inv);
        when(p1Inv.GetGears()).thenReturn(gears);

        assertFalse(glove.Attacked(p1,p2,agent));
    }

    @Test
    public void testGloveUse() {
        //Alapallapot
        assertEquals(3,glove.getLife());
        assertFalse(glove.IsUsed());

        glove.Use();

        assertEquals(2,glove.getLife());
        assertTrue(glove.IsUsed());
    }

    @Test
    public void testAxeOnInit() {
        assertFalse(axe.IsUsed());
    }

    @Test
    public void testAxeUse() {
        Mockito.doNothing().when(p1).Died();

        axe.Applied(p1);

        assertTrue(axe.IsUsed());
    }
}
