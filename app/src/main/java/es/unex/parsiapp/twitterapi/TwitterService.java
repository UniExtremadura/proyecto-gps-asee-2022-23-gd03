package es.unex.parsiapp.twitterapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TwitterService {

    // Llamada que obtiene los tweets a partir de un concepto (Query)
    @GET("https://api.twitter.com/2/tweets/search/recent?expansions=author_id&tweet.fields=author_id,created_at&media.fields=url&user.fields=name,username,profile_image_url")
    Call<TweetResults> tweetsFromQuery(@Query("query") String query, @Query("max_results") String max_posts, @Header("Authorization") String authHeader);

    // Llamada que obtiene los tweets recientes de un determinado usuario (la String user es el ID de Twitter de dicho usuario)
    @GET("https://api.twitter.com/2/users/{user}/tweets?tweet.fields=author_id,created_at&expansions=author_id&media.fields=url&user.fields=name,username,profile_image_url")
    Call<TweetResults> tweetsFromUser(@Path("user") String user, @Query("max_results") String max_posts, @Header("Authorization") String authHeader);

    // Llamada que obtiene un tweet en base a su ID
    @GET("https://api.twitter.com/2/tweets/{id}?tweet.fields=author_id,created_at&expansions=author_id&media.fields=url&user.fields=name,username,profile_image_url")
    Call<SingleTweet> tweetFromID(@Path("id") String id, @Header("Authorization") String authHeader);

    // Llamada que obtiene el ID de un usuario en base a su nombre (necesario para tweetsFromUser)
    @GET("https://api.twitter.com/2/users/by/username/{username}")
    Call<UserData> userIDfromUsername(@Path("username") String username, @Header("Authorization") String authHeader);
}
