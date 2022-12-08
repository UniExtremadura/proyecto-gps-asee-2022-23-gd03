package es.unex.parsiapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import es.unex.parsiapp.model.Columna;

public class ColumnaTest {

    public enum ApiCallType {
        QUERY, USER
    };

    @Test
    public void setIdDb() {
        Columna columna = new Columna();
        long idDbColumna = 5;
        columna.setIdDb(idDbColumna);

        assertEquals("El idDb de la columna no coincide.", columna.getIdDb(), 5);
        assertNotNull("El idDb de la columna es nulo.", columna.getIdDb());
    }

    @Test
    public void getNombre() {
        Columna columna = new Columna();
        String nombreColumna = "Columna 1";
        columna.setNombre(nombreColumna);

        String nombreRecuperado = columna.getNombre();

        assertEquals("El nombre de la columna no coincide.", nombreRecuperado, "Columna 1");
        assertNotNull("El nombre de la columna es nulo.", nombreRecuperado);
    }

    @Test
    public void setNombre() {
        Columna columna = new Columna();
        String nombreColumna = "Columna 1";
        columna.setNombre(nombreColumna);

        assertEquals("El nombre de la columna no coincide.", columna.getNombre(), "Columna 1");
        assertNotNull("El nombre de la columna es nulo.", columna.getNombre());
    }

    @Test
    public void setApiCall() {
        Columna columna = new Columna();
        columna.setApiCall("Llamada a la API de prueba.");

        assertEquals("La llamada a la API no coincide.", columna.getApiCall(), "Llamada a la API de prueba.");
        assertNotNull("La llamada a la API es nula.", columna.getApiCall());
    }

    @Test
    public void setApiCallType() {
        Columna columna = new Columna();
        columna.setApiCallType(Columna.ApiCallType.USER);

        assertEquals("El tipo de la llamada a la API no coincide.", columna.getApiCallType(), Columna.ApiCallType.USER);
        assertNotNull("El tipo de la llamada a la API es nula.", columna.getApiCallType());
    }
}