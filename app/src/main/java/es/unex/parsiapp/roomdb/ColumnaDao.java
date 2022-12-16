package es.unex.parsiapp.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.parsiapp.model.Columna;

@Dao
public interface ColumnaDao {
    // SELECT
    @Query("SELECT * FROM columna")
    public List<Columna> getAll();
    @Query("SELECT * FROM columna WHERE columnaActual = 1")
    public Columna getColumnaActual();
    @Query("SELECT * FROM columna")
    public LiveData<List<Columna>> getAllFromLiveData();
    @Query("SELECT * FROM columna WHERE idDb = :columna_id")
    public LiveData<Columna> getColumnaLiveData(long columna_id);

    // INSERT
    @Insert
    public long insert(Columna c);

    // DELETES
    @Query("DELETE FROM columna WHERE idDb = :columna_id")
    public void deleteColumnaByID(long columna_id);

    // UPDATE
    @Update
    public int update(Columna c);
}
