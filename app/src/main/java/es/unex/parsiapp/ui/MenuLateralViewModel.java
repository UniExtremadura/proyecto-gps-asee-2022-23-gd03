package es.unex.parsiapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.repository.PostRepository;

public class MenuLateralViewModel extends ViewModel {

    private final PostRepository mRepository;
    private final LiveData<List<Carpeta>> mCarpetas;

    public MenuLateralViewModel(PostRepository repository){
        mRepository = repository;
        mCarpetas = mRepository.getAllFolders();
    }

    public LiveData<List<Carpeta>> getCarpetas(){
        return mCarpetas;
    }

    public void savePost(long post_id, long folder_id){
        mRepository.savePost(post_id, folder_id);
    }
}
