package es.unex.parsiapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import es.unex.parsiapp.model.Columna;

public class ColumnaTest {

    @Test
    public void getIdDb() {
        Columna columna = new Columna();
        long idDbColumna = 5;
        columna.setIdDb(idDbColumna);

        long idDbRecuperado = columna.getIdDb();

        assertEquals("El idDb recuperado no coincide.", idDbRecuperado, 5);
        assertNotNull("El idDb recuperado es nulo.", idDbRecuperado);
    }


    @Test
    public void getApiCall() {
        Columna columna = new Columna();
        columna.setApiCall("Llamada a la API de prueba.");

        String llamadaRecuperada = columna.getApiCall();

        assertEquals("La llamada a la API no coincide.", llamadaRecuperada, "Llamada a la API de prueba.");
        assertNotNull("La llamada a la API es nula.", llamadaRecuperada);
    }


}