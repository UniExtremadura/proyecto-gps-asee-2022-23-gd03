package es.unex.parsiapp.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.parsiapp.model.Columna;

@Dao
public interface ColumnaDao {
    @Query("SELECT * FROM columna")
    public List<Columna> getAll();
    @Query("SELECT * FROM columna WHERE idDb = :columna_id")
    public Columna getColumna(long columna_id);
    @Insert
    public long insert(Columna c);
    @Query("DELETE FROM columna")
    public void deleteAll();
    @Query("DELETE FROM columna WHERE idDb = :columna_id")
    public void deleteColumnaByID(long columna_id);
    @Query("SELECT * FROM columna WHERE columnaActual = 1")
    public Columna getColumnaActual();
    @Update
    public int update(Columna c);
}
