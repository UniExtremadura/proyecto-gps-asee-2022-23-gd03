package es.unex.parsiapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.repository.PostRepository;

public class DeleteViewModel extends ViewModel {
    private final PostRepository mRepository;
    private final LiveData<Columna> mColumna;
    private final LiveData<Carpeta> mCarpeta;

    public DeleteViewModel(PostRepository repository){
        mRepository = repository;
        mColumna = mRepository.getColumnaToDelete();
        mCarpeta = mRepository.getCarpetaToDelete();
    }

    public void setColumnaToDelete(long id_columna){
        mRepository.setColumnaToDelete(id_columna);
    }

    public void setCarpetaToDelete(long id_carpeta){
        mRepository.setCarpetaToDelete(id_carpeta);
    }

    public LiveData<Columna> getColumnaToDelete(){
        return mColumna;
    }

    public LiveData<Carpeta> getCarpetaToDelete(){
        return mCarpeta;
    }

    public void deleteColumna(long id_columna){
        mRepository.deleteColumna(id_columna);
    }

    public void deleteCarpeta(long id_carpeta){
        mRepository.deleteFolder(id_carpeta);
    }
}
