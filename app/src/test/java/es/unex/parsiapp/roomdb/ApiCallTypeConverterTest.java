package es.unex.parsiapp.roomdb;

import junit.framework.TestCase;

import org.junit.Test;

import es.unex.parsiapp.model.Columna;

public class ApiCallTypeConverterTest extends TestCase {

    @Test
    public void testTestToString() {
        String resultado = ApiCallTypeConverter.toString(Columna.ApiCallType.QUERY);

        assertNotNull(resultado);
    }

    @Test
    public void testToApiCallType() {
        Columna.ApiCallType resultado = ApiCallTypeConverter.toApiCallType("USER");

        assertNotNull(resultado);
    }
}