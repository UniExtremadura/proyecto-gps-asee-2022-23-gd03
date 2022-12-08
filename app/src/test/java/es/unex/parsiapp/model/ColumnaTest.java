package es.unex.parsiapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ColumnaTest {

    @Test
    public void isColumnaActual() {
        Columna columna = new Columna();
        columna.setNombre("Columna 1");
        columna.setColumnaActual(true);

        boolean actual = columna.isColumnaActual();

        assertTrue("La columna no está marcada como la columna actual.", actual);
    }
}