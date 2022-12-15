package es.unex.parsiapp.ui.gallery;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.parsiapp.repository.PostRepository;

public class FolderViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final PostRepository mRepository;

    public FolderViewModelFactory(PostRepository repository) {this.mRepository = repository;}

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new FolderViewModel(mRepository);
    }
}
