package es.unex.parsiapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import es.unex.parsiapp.model.Post;

public class PostTest {

    @Test
    public void getIdCarpeta() {
        Post post = new Post(1, "1234", 1);
        post.setIdCarpeta(5);

        long idCarpetaRecuperado = post.getIdCarpeta();

        assertEquals("El id de la carpeta no coincide.", idCarpetaRecuperado, 5);
        assertNotNull("El id de la carpeta es nulo.", idCarpetaRecuperado);
    }
}