package es.unex.parsiapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="carpeta")
public class Carpeta implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private long idDb; // ID en la BD
    @ColumnInfo(name="nombre")
    private String nombre;

    // --- Constructores ---
    public Carpeta(){}

    public Carpeta(String nombre) {
        this.nombre = nombre;
    }

    @Ignore
    public Carpeta(long id, String nombre){
        this.idDb = id;
        this.nombre = nombre;
    }

    // --- Métodos get y set ---
    public void setNombre(String nombre){this.nombre = nombre;}

    public String getNombre() { return this.nombre; }

    public void setIdDb(long idDb){
        this.idDb = idDb;
    }

    public long getIdDb(){ return this.idDb; }
}
