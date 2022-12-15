package es.unex.parsiapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.repository.PostRepository;

public class FolderContentViewModel extends ViewModel {

    private final PostRepository mRepository;
    private final LiveData<List<Post>> mPosts;
    private Carpeta carpetaActual;

    public FolderContentViewModel(PostRepository repository){
        mRepository = repository;
        mPosts = repository.getCurrentPostsFolder();
    }

    public void setCarpeta(Carpeta carpeta){
        if(carpetaActual == null || carpetaActual.getIdDb() != carpeta.getIdDb()){
            mRepository.setCarpeta(carpeta);
            carpetaActual = carpeta;
        }
    }

    public LiveData<List<Post>> getPosts(){ return mPosts; }
}
