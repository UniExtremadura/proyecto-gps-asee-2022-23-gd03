package es.unex.parsiapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.repository.PostRepository;

public class CreateColumnViewModel extends ViewModel {

    private final PostRepository mRepository;
    private final LiveData<Columna> mColumna;

    public CreateColumnViewModel(PostRepository repository){
        mRepository = repository;
        mColumna = mRepository.getColumnBeingEdited();
    }

    public void setColumnBeingEdited(long id_columna){
        mRepository.setColumnBeingEdited(id_columna);
    }

    public LiveData<Columna> getColumnBeingEdited(){
        return mColumna;
    }

    public void createColumna(Columna c){
        mRepository.createColumna(c);
    }

    public void editColumna(Columna c){
        mRepository.editColumna(c);
    }
}
