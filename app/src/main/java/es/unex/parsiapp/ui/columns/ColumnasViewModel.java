package es.unex.parsiapp.ui.columns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.repository.PostRepository;

public class ColumnasViewModel extends ViewModel {

    private final PostRepository mRepository;
    private final LiveData<List<Columna>> mColumns;

    public ColumnasViewModel(PostRepository repository) {
        mRepository = repository;
        mColumns = mRepository.getAllColumnas();
    }

    public LiveData<List<Columna>> getColumns() {
        return mColumns;
    }

    public void setColumnaActual(Columna c){
        mRepository.setColumnaActual(c);
    }
}
