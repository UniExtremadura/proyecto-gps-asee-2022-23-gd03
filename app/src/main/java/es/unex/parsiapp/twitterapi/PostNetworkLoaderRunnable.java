package es.unex.parsiapp.twitterapi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import es.unex.parsiapp.BuildConfig;
import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.util.AppExecutors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostNetworkLoaderRunnable implements Runnable{
    private final OnPostsLoadedListener mOnPostsLoadedListener;
    private final Columna mColumna;
    private final String mMaxPosts;

    private final String bearerTokenApi;
    // Objeto Retrofit para realizar llamadas a la API
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.twitter.com/2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private Call<TweetResults> callObject;

    public PostNetworkLoaderRunnable(Columna columna, String maxPosts, OnPostsLoadedListener onPostsLoadedListener){
        bearerTokenApi = BuildConfig.api_token;
        mOnPostsLoadedListener = onPostsLoadedListener;
        mColumna = columna;
        mMaxPosts = maxPosts;
    }

    @Override
    public void run() {
        callObject = null;
        final String[] userId = new String[1];
        TwitterService service = retrofit.create(TwitterService.class);
        String query = mColumna.getApiCall();

        // Obtener el tipo de llamada a la API
        if(mColumna.getApiCallType() == Columna.ApiCallType.QUERY){
            callObject = service.tweetsFromQuery(query, mMaxPosts, "Bearer " + bearerTokenApi);
            executeQuery();
        } else if (mColumna.getApiCallType() == Columna.ApiCallType.USER){
            // Se debe de obtener primero el nombre de usuario
            service.userIDfromUsername(query, "Bearer " + bearerTokenApi).enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    UserData udata = response.body();
                    if (udata == null) {
                        System.out.println("No se ha encontrado al usuario especificado");
                        return;
                    } else {
                        userId[0] = udata.getData().getId();
                        callObject = service.tweetsFromUser(userId[0], mMaxPosts, "Bearer " + bearerTokenApi);
                        executeQuery();
                    }
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }

    public void executeQuery(){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<TweetResults> response = callObject.execute();
                    List<Post> posts = response.body() == null ? new ArrayList<>() : response.body().toPostList();
                    AppExecutors.getInstance().mainThread().execute(() -> mOnPostsLoadedListener.onPostsLoaded(posts));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
