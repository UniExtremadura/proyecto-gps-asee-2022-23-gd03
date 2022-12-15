package es.unex.parsiapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.repository.PostRepository;

public class HomeViewModel extends ViewModel {

    private final PostRepository mRepository;
    private final LiveData<List<Post>> mPosts;
    private String mmax_posts;


    public HomeViewModel(PostRepository repository) {
        mRepository = repository;
        mPosts = repository.getCurrentPostsHome();
    }

    public void setColumna(String max_posts){
        mmax_posts = max_posts;
        mRepository.setColumna(mmax_posts);
    }

    public LiveData<List<Post>> getPosts() {
        return mPosts;
    }
}