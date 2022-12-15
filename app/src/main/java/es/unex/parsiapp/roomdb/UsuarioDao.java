package es.unex.parsiapp.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import es.unex.parsiapp.model.Usuario;

@Dao
public interface UsuarioDao {
    // SELECT
    @Query("SELECT * FROM usuario WHERE idDb = :id_usuario")
    public Usuario getUsuario(long id_usuario);
    @Query("SELECT * FROM usuario WHERE username = :username_")
    public Usuario getUsuarioFromUsername(String username_);
    @Query("SELECT * FROM usuario WHERE username = :username_")
    public LiveData<Usuario> getUsuarioFromUsernameLiveData(String username_);

    // INSERT
    @Insert
    public long insert(Usuario user);

    // UPDATE
    @Update
    public int update(Usuario user);
}
