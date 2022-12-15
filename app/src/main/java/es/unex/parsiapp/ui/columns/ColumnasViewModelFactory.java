package es.unex.parsiapp.ui.columns;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.parsiapp.repository.PostRepository;

public class ColumnasViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final PostRepository mRepository;

    public ColumnasViewModelFactory(PostRepository repository) {this.mRepository = repository;}

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ColumnasViewModel(mRepository);
    }
}
