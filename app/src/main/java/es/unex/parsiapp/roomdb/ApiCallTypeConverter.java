package es.unex.parsiapp.roomdb;

import androidx.room.TypeConverter;

import es.unex.parsiapp.model.Columna;

// Conversor para el enumerado "ApiCallType" de columna
public class ApiCallTypeConverter {
    @TypeConverter
    public static String toString(Columna.ApiCallType apiCallType){
        return apiCallType == null ? null : apiCallType.name();
    }

    @TypeConverter
    public static Columna.ApiCallType toApiCallType(String apiCallType){
        return apiCallType == null ? null : Columna.ApiCallType.valueOf(apiCallType);
    }
}
