package es.unex.parsiapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.parsiapp.model.Usuario;
import es.unex.parsiapp.repository.PostRepository;

public class LoginViewModel extends ViewModel {

    private final PostRepository mRepository;
    private final LiveData<Usuario> mUser;

    public LoginViewModel(PostRepository repository){
        mRepository = repository;
        mUser = mRepository.getUser();
    }

    public void setUser(String username){
        mRepository.setUser(username);
    }

    public LiveData<Usuario> getUser(){
        return mUser;
    }
}
