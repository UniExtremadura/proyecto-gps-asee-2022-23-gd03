package es.unex.parsiapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import es.unex.parsiapp.model.Columna;

public class ColumnaTest {

    @Test
    public void setColumnaActual() {
        Columna columna = new Columna();
        columna.setNombre("Columna 1");
        columna.setColumnaActual(true);

        assertTrue("La columna no está marcada como la columna actual.", columna.isColumnaActual());
    }

}