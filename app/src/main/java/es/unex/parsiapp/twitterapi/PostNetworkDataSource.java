package es.unex.parsiapp.twitterapi;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.util.AppExecutors;


public class PostNetworkDataSource {
    private static final String LOG_TAG = PostNetworkDataSource.class.getSimpleName();
    private static PostNetworkDataSource sInstance;

    // LiveData
    private final MutableLiveData<Post[]> mDownloadedPosts;

    private PostNetworkDataSource() {mDownloadedPosts = new MutableLiveData<>();}

    public synchronized static PostNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new PostNetworkDataSource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }

    public LiveData<Post[]> getCurrentPosts(){return mDownloadedPosts;}

    public void fetchPosts(Columna c, String max_posts){
        Log.d(LOG_TAG, "Fetch posts started");
        AppExecutors.getInstance().networkIO().execute(new PostNetworkLoaderRunnable(c, max_posts, posts -> mDownloadedPosts.postValue(posts.toArray(new Post[0]))));
    }

}
