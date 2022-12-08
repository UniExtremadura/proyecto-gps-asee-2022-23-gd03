package es.unex.parsiapp.model;

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

    @Test
    public void getApiCallType() {
        Columna columna = new Columna();
        columna.setApiCallType(Columna.ApiCallType.USER);

        Columna.ApiCallType apiCallType = columna.getApiCallType();

        assertEquals("El tipo de la llamada a la API no coincide.", apiCallType, Columna.ApiCallType.USER);
        assertNotNull("El tipo de la llamada a la API es nula.", apiCallType);
    }

    public void setColumnaActual() {
        Columna columna = new Columna();
        columna.setNombre("Columna 1");
        columna.setColumnaActual(true);

        assertTrue("La columna no está marcada como la columna actual.", columna.isColumnaActual());
    }

    @Test
    public void isColumnaActual() {
        Columna columna = new Columna();
        columna.setNombre("Columna 1");
        columna.setColumnaActual(true);

        boolean actual = columna.isColumnaActual();

        assertTrue("La columna no está marcada como la columna actual.", actual);
    }
}