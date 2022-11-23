package es.unex.parsiapp.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Post;

@Database(entities = {Carpeta.class, Post.class}, version = 2)
public abstract class ParsiDatabase extends RoomDatabase {
    private static ParsiDatabase instance;

    public static ParsiDatabase getInstance(Context context) {
        if (instance == null) {
            // .fallbackToDestructiveMigration destruye la BD cuando se cambia de version
            instance = Room.databaseBuilder(context, ParsiDatabase.class, "parsi.db").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract CarpetaDao getCarpetaDao();
    public abstract PostDao getPostDao();
}
