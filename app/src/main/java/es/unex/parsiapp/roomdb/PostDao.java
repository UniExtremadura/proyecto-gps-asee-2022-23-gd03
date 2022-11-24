package es.unex.parsiapp.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.parsiapp.model.Post;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    public List<Post> getAll();
    @Insert
    public long insert(Post p);
    @Query("DELETE FROM post")
    public void deleteAll();
    @Query("DELETE FROM post WHERE idDb = :id_post")
    public void delete(long id_post);
    @Query("DELETE FROM post WHERE carpetaid = :carpeta_id")
    public void deleteAllPostsFromCarpeta(long carpeta_id);
    @Update
    public int update(Post p);
}
