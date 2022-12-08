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