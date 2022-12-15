package es.unex.parsiapp.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.parsiapp.repository.PostRepository;

public class FolderContentViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final PostRepository mRepository;

    public FolderContentViewModelFactory(PostRepository repository) {this.mRepository = repository;}

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new FolderContentViewModel(mRepository);
    }
}
