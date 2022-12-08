package es.unex.parsiapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PostTest {
    
    @Test
    public void getIdDb() {
        Post post = new Post("1");
        long idDbPost = 5;
        post.setIdDb(idDbPost);

        long idDbRecuperado = post.getIdDb();

        assertEquals("El idDb del post no coincide.", idDbRecuperado, 5);
        assertNotNull("El idDb del post es nulo.", idDbRecuperado);
    }

    @Test
    public void setId() {
        Post post = new Post("1");
        post.setId("5");

        assertEquals("El id del post no coincide.", post.getId(), "5");
        assertNotNull("El id del post es nulo.", post.getId());
    }

    @Test
    public void getId() {
        Post post = new Post("1");
        post.setId("5");

        String idRecuperado = post.getId();

        assertEquals("El id del post no coincide.", idRecuperado, "5");
        assertNotNull("El id del post es nulo.", idRecuperado);
    }

    @Test
    public void setAuthorId() {
        Post post = new Post("1");
        post.setAuthorId("5");

        assertEquals("El id del autor no coincide.", post.getAuthorId(), "5");
        assertNotNull("El id del autor es nulo.", post.getAuthorId());
    }

    @Test
    public void getAuthorId() {
        Post post = new Post("1");
        post.setAuthorId("5");

        String idAutorRecuperado = post.getAuthorId();

        assertEquals("El id del autor no coincide.", idAutorRecuperado, "5");
        assertNotNull("El id del autor es nulo.", idAutorRecuperado);
    }

    @Test
    public void setAuthorUsername() {
        Post post = new Post("1");
        post.setAuthorUsername("Andrés");

        assertEquals("El nombre del autor no coincide.", post.getAuthorUsername(), "Andrés");
        assertNotNull("El nombre del autor es nulo.", post.getAuthorUsername());
    }

    @Test
    public void getAuthorUsername() {
        Post post = new Post("1");
        post.setAuthorUsername("Andrés");

        String nombreAutorRecuperado = post.getAuthorUsername();

        assertEquals("El nombre del autor no coincide.", nombreAutorRecuperado, "Andrés");
        assertNotNull("El nombre del autor es nulo.", nombreAutorRecuperado);
    }

    @Test
    public void setContenido() {
        Post post = new Post("1");
        post.setContenido("Contenido de prueba para los tests unitarios");

        assertEquals("El contenido no coincide.", post.getContenido(), "Contenido de prueba para los tests unitarios");
        assertNotNull("El contenido es nulo.", post.getContenido());
    }

    @Test
    public void getContenido() {
        Post post = new Post("1");
        post.setContenido("Contenido de prueba para los tests unitarios");

        String contenidoRecuperado = post.getContenido();

        assertEquals("El contenido no coincide.", contenidoRecuperado, "Contenido de prueba para los tests unitarios");
        assertNotNull("El contenido es nulo.", contenidoRecuperado);
    }

    //@Test
    public void setProfilePicture() {
    }

    //@Test
    public void getProfilePicture() {
    }

    @Test
    public void setTimestamp() {
        Post post = new Post("1");
        post.setTimestamp("29/11/2022 20:19:10");

        assertEquals("El timestamp no coincide.", post.getTimestamp(), "29/11/2022 20:19:10");
        assertNotNull("El timestamp es nulo.", post.getTimestamp());
    }

    @Test
    public void getTimestamp() {
        Post post = new Post("1");
        post.setTimestamp("29/11/2022 20:19:10");

        String timestampRecuperado = post.getTimestamp();

        assertEquals("El timestamp no coincide.", timestampRecuperado, "29/11/2022 20:19:10");
        assertNotNull("El timestamp es nulo.", timestampRecuperado);
    }
}