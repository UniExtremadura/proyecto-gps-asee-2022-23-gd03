package es.unex.parsiapp.model;

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
    @ColumnInfo(name="authorid")
    private String authorId; // ID del autor
    @ColumnInfo(name="authorUsername")
    private String authorUsername; // Nombre de usuario del autor
    @ColumnInfo(name="contenido")
    private String contenido;  // Contenido textual del Twit (no creo que haga falta guardarlo)
    @ColumnInfo(name="profilePicture")
    private String profilePicture; // URL de la foto de perfil del autor
    @ColumnInfo(name="timestamp")
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

    @Ignore
    public Post(long idDb, String id, long idCarpeta){
        this.idDb = idDb;
        this.id = id;
        this.idCarpeta = idCarpeta;
    }

    public Post(long idDb, String id, long idCarpeta, String authorId, String authorUsername, String contenido, String profilePicture, String timestamp){
        this.idDb = idDb;
        this.id = id;
        this.idCarpeta = idCarpeta;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.contenido = contenido;
        this.profilePicture = profilePicture;
        this.timestamp = timestamp;
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

    public String toString(){
        return "POST - ID: " + this.id + " - AUTOR ID: " + this.authorId + " - USERNAME: " + this.authorUsername + " - CONTENIDO: " + this.contenido;
    }

}
