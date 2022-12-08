package es.unex.parsiapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Post;

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
    public void setIdDb() {
        Carpeta carpeta = new Carpeta();
        long idDbCarpeta = 5;
        carpeta.setIdDb(idDbCarpeta);

        assertEquals("El idDb de la carpeta no coincide.", carpeta.getIdDb(), 5);
        assertNotNull("El idDb de la carpeta es nulo.", carpeta.getIdDb());
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

    @Test
    public void anadirPost() {
        listaPosts = new ArrayList<Post>();

        Post p1 = new Post(1, "1234", 1);
        Post p2 = new Post(2, "5678", 2);

        listaPosts.add(p1);
        listaPosts.add(p2);

        assertTrue("El tamaño de la lista de posts no es el esperado.", listaPosts.size() == 2);
        assertFalse("La lista de posts está vacía.", listaPosts.isEmpty());
        assertNotEquals("El primer elemento de la lista es nulo.", listaPosts.get(0), null);
    }

    @Test
    public void quitarPost() {
        listaPosts = new ArrayList<Post>();

        Post p1 = new Post("1");
        Post p2 = new Post("2");

        listaPosts.add(p1);
        listaPosts.add(p2);

        listaPosts.remove(0);

        assertTrue("El tamaño de la lista de posts no es el esperado.", listaPosts.size() == 1);
    }
}