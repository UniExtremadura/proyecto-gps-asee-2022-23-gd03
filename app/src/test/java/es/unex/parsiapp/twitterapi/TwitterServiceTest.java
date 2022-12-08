package es.unex.parsiapp.twitterapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import es.unex.parsiapp.model.Post;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwitterServiceTest {

    private TwitterService twitterService;
    private String bearerTokenApi = "AAAAAAAAAAAAAAAAAAAAAN17jAEAAAAARPbZdHUXnMf%2F1qOKDcvaADYaD8Y%3DCJ2WH2ItpWhqKEvdwIz7hWu6qnUU9UlbYe0LEQtd7E7EfvJRU8";
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.twitter.com/2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private List<Post> listposts;

    @Test
    public void tweetsFromUser() throws IOException {

        twitterService = retrofit.create(TwitterService.class);

        Call<UserData> callUser = twitterService.userIDfromUsername("elonmusk", "Bearer " + bearerTokenApi);

        Response<UserData> responseUser = callUser.execute();

        UserData userData = responseUser.body();;

        Call<TweetResults> callPost = twitterService.tweetsFromUser(userData.getData().getId(), "10", "Bearer " + bearerTokenApi);

        Response<TweetResults> responsePost = callPost.execute();

        TweetResults tweetResults = responsePost.body();
        listposts = tweetResults.toPostList();

        assertTrue(responsePost != null);
        assertTrue(responsePost.isSuccessful());
        assertNotNull(listposts);
        assertTrue(listposts.size() == 10);
    }

    @Test
    public void userIDfromUsername() throws IOException {

        twitterService = retrofit.create(TwitterService.class);

        Call<UserData> call = twitterService.userIDfromUsername("elonmusk", "Bearer " + bearerTokenApi);

        Response<UserData> response = call.execute();

        assertTrue(response.isSuccessful());
        assertNotNull(response);
    }
}