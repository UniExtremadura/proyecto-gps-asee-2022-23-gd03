package es.unex.parsiapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName="post")
public class Post implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long idDb; // ID de la BD
    @ColumnInfo(name="id")
    private String id;  // ID única del Twit
    @ColumnInfo(name="carpetaid")
    private long idCarpeta; // ID de la BD de la carpeta que contiene al post
    @Ignore
    private String authorId; // ID del autor
    @Ignore
    private String authorUsername; // Nombre de usuario del autor
    @Ignore
    private String contenido;  // Contenido textual del Twit (no creo que haga falta guardarlo)
    @Ignore
    private String profilePicture; // URL de la foto de perfil del autor
    @Ignore
    private String timestamp; // Fecha y hora de creacion del tweet

    // --- Constructores ---
    @Ignore
    public Post(String id){
        this.id = id;
    }

    @Ignore
    public Post(String id, long idCarpeta){
        this.id = id;
        this.idCarpeta = idCarpeta;
    }

    public Post(long idDb, String id, long idCarpeta){
        this.idDb = idDb;
        this.id = id;
        this.idCarpeta = idCarpeta;
    }

    // --- Métodos get y set ---
    public long getIdDb() {return this.idDb;}

    public void setIdDb(long idDb) {this.idDb = idDb;}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {this.id = id;}

    public long getIdCarpeta() {return idCarpeta;}

    public void setIdCarpeta(long idCarpeta) {this.idCarpeta = idCarpeta;}

    public String getAuthorId() {return authorId;}

    public void setAuthorId(String authorId) {this.authorId = authorId;}

    public String getAuthorUsername() {return authorUsername;}

    public void setAuthorUsername(String authorUsername) {this.authorUsername = authorUsername;}

    public String getContenido() { return this.contenido; }

    public void setContenido(String contenido) { this.contenido = contenido;}

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Método para compartir el post a otras apps mediante un Intent Implícito
     */
    public void compartir(){
        // todo
        // No sé si esto hay que hacerlo desde la parte gráfica porque basicamente es
        // hacer un intent implícito
    }

    /**
     * Método que tiene el propio post para guardarse en la carpeta que se indique.
     * @param carpeta La carpeta en la que se quiere guardar el Post
     */
    public void guardarEnCarpeta(@NonNull Carpeta carpeta){
        carpeta.anadirPost(this);
    }

    public String toString(){
        return "POST - ID: " + this.id + " - AUTOR ID: " + this.authorId + " - USERNAME: " + this.authorUsername + " - CONTENIDO: " + this.contenido;
    }

}
