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
    public void setIdDb() {
        Carpeta carpeta = new Carpeta();
        long idDbCarpeta = 5;
        carpeta.setIdDb(idDbCarpeta);

        assertEquals("El idDb de la carpeta no coincide.", carpeta.getIdDb(), 5);
        assertNotNull("El idDb de la carpeta es nulo.", carpeta.getIdDb());
    }
}