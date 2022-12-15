package es.unex.parsiapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.repository.PostRepository;

public class CreateFolderViewModel extends ViewModel {

    private final PostRepository mRepository;
    private final LiveData<Carpeta> mCarpeta;

    public CreateFolderViewModel(PostRepository repository){
        mRepository = repository;
        mCarpeta = mRepository.getCarpetaBeingEdited();
    }

    public void setCarpetaBeingEdited(long id_carpeta){
        mRepository.setCarpetaBeingEdited(id_carpeta);
    }

    public LiveData<Carpeta> getCarpetaBeingEdited(){ return mCarpeta; }

    public void createFolder(Carpeta c) { mRepository.createFolder(c); }

    public void editFolder(Carpeta c) { mRepository.editFolder(c); }
}
