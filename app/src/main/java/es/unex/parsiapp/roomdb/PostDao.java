package es.unex.parsiapp.roomdb;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.parsiapp.model.Post;

@Dao
public interface PostDao {
    // SELECTS
    @Query("SELECT * FROM post WHERE idDb = :id_post")
    public Post getPost(long id_post);
    @Query("SELECT * FROM post WHERE id = :id_post")
    public Post getPostById(String id_post);
    @Query("SELECT * FROM post WHERE carpetaid = -1")
    public LiveData<List<Post>> getAllLiveData();
    @Query("SELECT * FROM post WHERE carpetaid = :carpeta_id")
    public LiveData<List<Post>> getAllPostsFromCarpetaLiveData(long carpeta_id);

    // INSERTS
    @Insert(onConflict = REPLACE)
    public long insert(Post p);
    @Insert(onConflict = REPLACE)
    void bulkInsert(List<Post> postList);

    // DELETES
    @Query("DELETE FROM post WHERE idDb = :id_post")
    public void delete(long id_post);
    @Query("DELETE FROM post WHERE carpetaid = :carpeta_id")
    public void deleteAllPostsFromCarpeta(long carpeta_id);
    @Query("DELETE FROM post WHERE carpetaid = -1")
    public void deleteAllPostsWithoutCarpeta();

    // UPDATE
    @Update
    public int update(Post p);
}
