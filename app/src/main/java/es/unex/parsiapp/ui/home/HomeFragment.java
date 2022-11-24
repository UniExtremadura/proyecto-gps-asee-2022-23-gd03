package es.unex.parsiapp.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import es.unex.parsiapp.AppExecutors;
import es.unex.parsiapp.ListAdapterPost;
import es.unex.parsiapp.R;
import es.unex.parsiapp.databinding.FragmentHomeBinding;
import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.roomdb.ParsiDatabase;
import es.unex.parsiapp.tweetDetailsActivity;
import es.unex.parsiapp.twitterapi.TweetResults;
import es.unex.parsiapp.twitterapi.TwitterService;
import es.unex.parsiapp.twitterapi.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private List<Post> listposts; // Lista de posts en Home
    private FragmentHomeBinding binding; // Binding
    private String bearerTokenApi = "AAAAAAAAAAAAAAAAAAAAAN17jAEAAAAARPbZdHUXnMf%2F1qOKDcvaADYaD8Y%3DCJ2WH2ItpWhqKEvdwIz7hWu6qnUU9UlbYe0LEQtd7E7EfvJRU8"; // Token API
    // Objeto Retrofit para realizar llamadas a la API
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.twitter.com/2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private SwipeRefreshLayout refresh;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Obtener view
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Mostrar tweets
        showTweetsFromColumna(root);

        return root;
    }

    // Muestra los tweets realizando una llamada a la API
    public void showTweetsFromColumna(View root){
        // --- API --- //
        // NO se pueden hacer llamadas a la API en el hilo principal
        TwitterService twitterService = retrofit.create(TwitterService.class);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(getContext());

                // Obtencion columna actual
                Columna c = database.getColumnaDao().getColumnaActual();
                if(c != null) {
                    String query = c.getApiCall();
                    // Hacer un .enqueue es equivalente a llamarla en un hilo separado
                    if(c.getApiCallType() == Columna.ApiCallType.QUERY){
                        tweetsFromQuery(twitterService, query, root);
                    } else if (c.getApiCallType() == Columna.ApiCallType.USER){
                        tweetsFromUser(twitterService, query, root);
                    }
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            TextView t = (TextView) root.findViewById(R.id.addColumn);
                            t.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showPost(Post item, View root){
        Intent intent = new Intent(root.getContext(), tweetDetailsActivity.class);
        intent.putExtra("Post", item);
        intent.putExtra("Saved", 0);
        startActivity(intent);
    }

    public void tweetsFromQuery(TwitterService twitterService, String query, View root){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String max_posts = sharedPreferences.getString("max_posts", "20");

        twitterService.tweetsFromQuery(query, "Bearer " + bearerTokenApi).enqueue(new Callback<TweetResults>() {
            @Override
            public void onResponse(Call<TweetResults> call, Response<TweetResults> response) {
                onResponseTweets(response, root);
            }

            @Override
            public void onFailure(Call<TweetResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void tweetsFromUser(TwitterService twitterService, String query, View root){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String[] userId = {null};

        twitterService.userIDfromUsername(query, "Bearer " + bearerTokenApi).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                UserData udata = response.body();
                if(udata == null){
                    Toast.makeText(getContext(), "No se ha encontrado el usuario especificado en la columna", Toast.LENGTH_SHORT).show();
                } else {
                    userId[0] = udata.getData().getId();
                    twitterService.tweetsFromUser(userId[0], "Bearer " + bearerTokenApi).enqueue(new Callback<TweetResults>() {
                        @Override
                        public void onResponse(Call<TweetResults> call, Response<TweetResults> response) {
                            onResponseTweets(response, root);
                        }

                        @Override
                        public void onFailure(Call<TweetResults> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onResponseTweets(Response<TweetResults> response, View root){
        TweetResults tweetResults = response.body();
        try {
            // Conversion a lista de Posts de los tweets recibidos
            listposts = tweetResults.toPostList();

            // Actualizar vista
            ListAdapterPost listAdapter = new ListAdapterPost(listposts, root.getContext(), new ListAdapterPost.OnItemClickListener() {
                @Override
                public void onItemClick(Post item) {
                    showPost(item, root);
                }
            });            RecyclerView recyclerView = root.findViewById(R.id.listRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(listAdapter);
        } catch (Exception e){
            System.out.println("No se han encontrado tweets");
        }
    }
}