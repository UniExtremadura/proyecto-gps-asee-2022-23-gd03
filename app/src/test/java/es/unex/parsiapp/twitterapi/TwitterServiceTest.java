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
    public void tweetsFromQuery() throws IOException {

        twitterService = retrofit.create(TwitterService.class);

        Call<TweetResults> call = twitterService.tweetsFromQuery("mundial", "10", "Bearer " + bearerTokenApi);

        Response<TweetResults> response = call.execute();

        TweetResults tweetResults = response.body();
        listposts = tweetResults.toPostList();

        assertTrue(response != null);
        assertTrue(response.isSuccessful());
        assertNotNull(listposts);
        assertTrue(listposts.size() == 10);
    }
}