package es.unex.parsiapp;

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
}