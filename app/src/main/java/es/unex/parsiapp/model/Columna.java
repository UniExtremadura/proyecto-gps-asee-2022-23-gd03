package es.unex.parsiapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import es.unex.parsiapp.roomdb.ApiCallTypeConverter;

@Entity(tableName="columna")
public class Columna {

    // El usuario puede elegir entre ver los resultados de una busqueda o de un usuario
    public enum ApiCallType {
      QUERY, USER
    };

    @PrimaryKey(autoGenerate = true)
    private long idDb;
    @ColumnInfo(name="nombre")
    private String nombre;
    @ColumnInfo(name="apiCall")
    private String apiCall;
    @TypeConverters(ApiCallTypeConverter.class)
    private ApiCallType apiCallType;
    @ColumnInfo(name="columnaActual")
    private boolean columnaActual;

    // --- Constructores ---
    public Columna(){}

    public Columna(String nombre, String apiCall){
        this.nombre = nombre;
        this.apiCall = apiCall;
        this.columnaActual = false;
    }

    // --- Métodos get y set ---
    public long getIdDb() {
        return idDb;
    }

    public void setIdDb(long idDb) {
        this.idDb = idDb;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApiCall() {
        return apiCall;
    }

    public void setApiCall(String apiCall) {
        this.apiCall = apiCall;
    }

    public ApiCallType getApiCallType() {
        return apiCallType;
    }

    public void setApiCallType(ApiCallType apiCallType) {
        this.apiCallType = apiCallType;
    }

    public boolean isColumnaActual() {
        return columnaActual;
    }

    public void setColumnaActual(boolean columnaActual) {
        this.columnaActual = columnaActual;
    }
}
