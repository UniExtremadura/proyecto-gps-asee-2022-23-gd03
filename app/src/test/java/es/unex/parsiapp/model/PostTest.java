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

    @Test
    public void setIdDb() {
        Post post = new Post("1");
        long idDbPost = 5;
        post.setIdDb(idDbPost);

        assertEquals("El idDb del post no coincide.", post.getIdDb(), 5);
        assertNotNull("El idDb del post es nulo.", post.getIdDb());
    }
}