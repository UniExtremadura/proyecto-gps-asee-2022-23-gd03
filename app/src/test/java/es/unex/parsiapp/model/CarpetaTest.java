package es.unex.parsiapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CarpetaTest {

    private List<Post> listaPosts;

    @Test
    public void setNombre() {
        Carpeta carpeta = new Carpeta();
        String nombreCarpeta = "Carpeta 1";
        carpeta.setNombre(nombreCarpeta);

        assertEquals("El nombre de la carpeta no coincide.", carpeta.getNombre(), "Carpeta 1");
        assertNotNull("El nombre de la carpeta es nulo.", carpeta.getNombre());
    }
}