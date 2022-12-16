package es.unex.parsiapp.util;

import android.content.Context;

import es.unex.parsiapp.repository.PostRepository;
import es.unex.parsiapp.roomdb.ParsiDatabase;
import es.unex.parsiapp.twitterapi.PostNetworkDataSource;

public class AppContainer {

    private ParsiDatabase database;
    private PostNetworkDataSource networkDataSource;
    public PostRepository repository;

    // Factory
    public ViewModelFactory factory;


    public AppContainer(Context context){
        database = ParsiDatabase.getInstance(context);
        networkDataSource = PostNetworkDataSource.getInstance();
        repository = PostRepository.getInstance(database.getPostDao(), database.getCarpetaDao(), database.getColumnaDao(), database.getUsuarioDao(), networkDataSource);
        factory = new ViewModelFactory(repository);
    }

}
