package es.unex.parsiapp.twitterapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TwitterService {
    int MAX_RESULTS = 20;

    // Llamada que obtiene los tweets a partir de un concepto (Query)
    @GET("https://api.twitter.com/2/tweets/search/recent?expansions=author_id&tweet.fields=author_id,created_at&media.fields=url&user.fields=name,username,profile_image_url&max_results=" + MAX_RESULTS)
    Call<TweetResults> tweetsFromQuery(@Query("query") String query, @Header("Authorization") String authHeader);

    // Llamada que obtiene los tweets recientes de un determinado usuario (la String user es el ID de Twitter de dicho usuario)
    @GET("https://api.twitter.com/2/users/{user}/tweets?tweet.fields=author_id,created_at&expansions=author_id&media.fields=url&user.fields=name,username,profile_image_url&max_results=" + MAX_RESULTS)
    Call<TweetResults> tweetsFromUser(@Path("user") String user, @Header("Authorization") String authHeader);

    // Llamada que obtiene un tweet en base a su ID
    @GET("https://api.twitter.com/2/tweets/{id}?tweet.fields=author_id,created_at&expansions=author_id&media.fields=url&user.fields=name,username,profile_image_url")
    Call<SingleTweet> tweetFromID(@Path("id") String id, @Header("Authorization") String authHeader);

    @GET("https://api.twitter.com/2/users/by/username/{username}")
    Call<UserData> userIDfromUsername(@Path("username") String username, @Header("Authorization") String authHeader);
}
