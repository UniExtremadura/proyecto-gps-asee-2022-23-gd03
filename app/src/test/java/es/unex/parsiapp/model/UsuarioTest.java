package es.unex.parsiapp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UsuarioTest {

    @Test
    public void setIdDb() {
        Usuario usuario = new Usuario("andres", "1234");
        usuario.setIdDb(5);

        assertEquals("El idDb del usuario no coincide.", usuario.getIdDb(), 5);
        assertNotNull("El idDb del usuario es nulo.", usuario.getIdDb());
    }

    @Test
    public void getIdDb() {
        Usuario usuario = new Usuario("andres", "1234");
        usuario.setIdDb(5);

        long idDbRecuperado = usuario.getIdDb();

        assertEquals("El idDb del usuario no coincide.", idDbRecuperado, 5);
    }

    @Test
    public void setUsername() {
        Usuario usuario = new Usuario("andres", "1234");
        usuario.setUsername("andresgl");

        assertEquals("El nombre de usuario del usuario no coincide.", usuario.getUsername(), "andresgl");
        assertNotNull("El nombre de usuario del usuario es nulo.", usuario.getUsername());
    }

    @Test
    public void getUsername() {
        Usuario usuario = new Usuario("andres", "1234");
        usuario.setUsername("andresgl");

        String usernameRecuperado = usuario.getUsername();

        assertEquals("El nombre de usuario del usuario no coincide.", usernameRecuperado, "andresgl");
        assertNotNull("El nombre de usuario del usuario es nulo.", usernameRecuperado);
    }

    @Test
    public void setPassword() {
        Usuario usuario = new Usuario("andres", "1234");
        usuario.setPassword("5678");

        assertEquals("El nombre de usuario del usuario no coincide.", usuario.getPassword(), "5678");
        assertNotNull("El nombre de usuario del usuario es nulo.", usuario.getPassword());
    }

    @Test
    public void getPassword() {
        Usuario usuario = new Usuario("andres", "1234");
        usuario.setPassword("5678");

        String passwordRecuperada = usuario.getPassword();

        assertEquals("El nombre de usuario del usuario no coincide.", passwordRecuperada, "5678");
        assertNotNull("El nombre de usuario del usuario es nulo.", passwordRecuperada);
    }
}