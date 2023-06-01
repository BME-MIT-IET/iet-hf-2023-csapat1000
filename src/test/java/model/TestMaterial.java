package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMaterials {
    @Test
    public void testAllMaterialTypes() {
        Amino amino = new Amino();
        Nukleo nukleo = new Nukleo();

        assertEquals("amino", amino.GetType());
        assertEquals("nukleo", nukleo.GetType());
    }
}
