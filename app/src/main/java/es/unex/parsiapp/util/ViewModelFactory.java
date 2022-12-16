package es.unex.parsiapp.util;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.parsiapp.repository.PostRepository;
import es.unex.parsiapp.ui.CreateColumnViewModel;
import es.unex.parsiapp.ui.CreateFolderViewModel;
import es.unex.parsiapp.ui.DeleteViewModel;
import es.unex.parsiapp.ui.FolderContentViewModel;
import es.unex.parsiapp.ui.LoginViewModel;
import es.unex.parsiapp.ui.MenuLateralViewModel;
import es.unex.parsiapp.ui.RegisterViewModel;
import es.unex.parsiapp.ui.TweetDetailsViewModel;
import es.unex.parsiapp.ui.columns.ColumnasFragment;
import es.unex.parsiapp.ui.columns.ColumnasViewModel;
import es.unex.parsiapp.ui.gallery.FolderViewModel;
import es.unex.parsiapp.ui.home.HomeViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final PostRepository mRepository;

    public ViewModelFactory(PostRepository repository) {this.mRepository = repository;}

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if(ColumnasViewModel.class.isAssignableFrom(modelClass)){
            return (T) new ColumnasViewModel(mRepository);
        } else if(FolderViewModel.class.isAssignableFrom(modelClass)){
            return (T) new FolderViewModel(mRepository);
        } else if(HomeViewModel.class.isAssignableFrom(modelClass)){
            return (T) new HomeViewModel(mRepository);
        } else if(CreateColumnViewModel.class.isAssignableFrom(modelClass)){
            return (T) new CreateColumnViewModel(mRepository);
        } else if(CreateFolderViewModel.class.isAssignableFrom(modelClass)){
            return (T) new CreateFolderViewModel(mRepository);
        } else if(DeleteViewModel.class.isAssignableFrom(modelClass)){
            return (T) new DeleteViewModel(mRepository);
        } else if(FolderContentViewModel.class.isAssignableFrom(modelClass)){
            return (T) new FolderContentViewModel(mRepository);
        } else if(LoginViewModel.class.isAssignableFrom(modelClass)){
            return (T) new LoginViewModel(mRepository);
        } else if(MenuLateralViewModel.class.isAssignableFrom(modelClass)){
            return (T) new MenuLateralViewModel(mRepository);
        } else if(RegisterViewModel.class.isAssignableFrom(modelClass)){
            return (T) new RegisterViewModel(mRepository);
        } else if(TweetDetailsViewModel.class.isAssignableFrom(modelClass)){
            return (T) new TweetDetailsViewModel(mRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
