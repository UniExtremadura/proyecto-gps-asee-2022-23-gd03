package es.unex.parsiapp.twitterapi;

import java.util.List;

import es.unex.parsiapp.model.Post;

public interface OnPostsLoadedListener {
    public void onPostsLoaded(List<Post> posts);
}
