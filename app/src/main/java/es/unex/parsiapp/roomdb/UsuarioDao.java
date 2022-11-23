package es.unex.parsiapp.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import es.unex.parsiapp.model.Usuario;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario WHERE idDb = :id_usuario")
    public Usuario getUsuario(long id_usuario);
    @Query("SELECT * FROM usuario WHERE username = :username_")
    public Usuario getUsuarioFromUsername(String username_);
    @Insert
    public long insert(Usuario user);
    @Update
    public int update(Usuario user);
}
