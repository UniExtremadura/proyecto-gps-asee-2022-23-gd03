package es.unex.parsiapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PostTest {

    @Test
    public void setIdCarpeta() {
        Post post = new Post(1, "1234", 1);
        post.setIdCarpeta(5);

        assertEquals("El id de la carpeta no coincide.", post.getIdCarpeta(), 5);
        assertNotNull("El id de la carpeta es nulo.", post.getIdCarpeta());
    }
}