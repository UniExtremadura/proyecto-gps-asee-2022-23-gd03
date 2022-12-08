package es.unex.parsiapp.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CarpetaTest {

    private List<Post> listaPosts;

    @Test
    public void getNombre() {
        Carpeta carpeta = new Carpeta();
        String nombreCarpeta = "Carpeta 1";
        carpeta.setNombre(nombreCarpeta);

        String nombreRecuperado = carpeta.getNombre();

        assertEquals("El nombre recuperado no coincide.", nombreRecuperado, "Carpeta 1");
        assertNotNull("El nombre recuperado es nulo.", nombreRecuperado);
    }

    @Test
    public void getIdDb() {
        Carpeta carpeta = new Carpeta();
        long idDbCarpeta = 5;
        carpeta.setIdDb(idDbCarpeta);

        long idDbRecuperado = carpeta.getIdDb();

        assertEquals("El idDb recuperado no coincide.", idDbRecuperado, 5);
        assertNotNull("El idDb recuperado es nulo.", idDbRecuperado);
    }
}