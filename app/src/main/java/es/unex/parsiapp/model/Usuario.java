package es.unex.parsiapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="usuario")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private long idDb;
    @ColumnInfo(name="username")
    private String username;
    @ColumnInfo(name="password")
    private String password;

    /**
     * Constructor de usuario a partir del Username, una contraseña y un nombre de perfil
     * @param username El nombre de usuario que se quiera utilizar. Tiene que ser único.
     * @param password La contraseña que se haya elegido
     */
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- Métodos get y set ---
    public long getIdDb() {
        return idDb;
    }

    public void setIdDb(long idDb) { this.idDb = idDb; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
