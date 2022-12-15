package es.unex.parsiapp.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.parsiapp.repository.PostRepository;

public class MenuLateralViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final PostRepository mRepository;

    public MenuLateralViewModelFactory(PostRepository repository) { this.mRepository = repository; }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MenuLateralViewModel(mRepository);
    }
}
