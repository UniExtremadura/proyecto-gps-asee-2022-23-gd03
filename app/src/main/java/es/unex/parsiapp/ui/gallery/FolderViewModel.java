package es.unex.parsiapp.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.repository.PostRepository;

public class FolderViewModel extends ViewModel {

    private final PostRepository mRepository;
    private final LiveData<List<Carpeta>> mFolders;

    public FolderViewModel(PostRepository repository) {
        mRepository = repository;
        mFolders = mRepository.getAllFolders();
    }

    public LiveData<List<Carpeta>> getFolders() {
        return mFolders;
    }
}