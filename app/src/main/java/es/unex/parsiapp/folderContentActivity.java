package es.unex.parsiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.parsiapp.databinding.FragmentFolderContentBinding;
import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.roomdb.ParsiDatabase;
import es.unex.parsiapp.twitterapi.SingleTweet;
import es.unex.parsiapp.twitterapi.TwitterService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class folderContentActivity extends AppCompatActivity {
    private List<Post> listposts;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.twitter.com/2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private long idFolder;
    ImageButton b;
    private String bearerTokenApi = "AAAAAAAAAAAAAAAAAAAAAN17jAEAAAAARPbZdHUXnMf%2F1qOKDcvaADYaD8Y%3DCJ2WH2ItpWhqKEvdwIz7hWu6qnUU9UlbYe0LEQtd7E7EfvJRU8";
    private FragmentFolderContentBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_folder_content);

        showContentFolder();
    }

    public void showContentFolder(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(folderContentActivity.this);
                Carpeta item = (Carpeta) getIntent().getSerializableExtra("folderContent");
                idFolder = item.getIdDb();
                List<Post> listaPostDB;
                listposts = new ArrayList<>();

                listaPostDB = database.getCarpetaDao().getAllPostsFromCarpeta(idFolder);

                TwitterService twitterService = retrofit.create(TwitterService.class);

                for(Post p: listaPostDB){
                    twitterService.tweetFromID(p.getId(),"Bearer " + bearerTokenApi).enqueue(new Callback<SingleTweet>() {
                        @Override
                        public void onResponse(Call<SingleTweet> call, Response<SingleTweet> response) {
                            System.out.println(response.errorBody());
                            SingleTweet tweet = response.body();
                            // Conversion a lista de Posts de los tweets recibidos
                            Post post = tweet.toPost();
                            post.setIdDb(p.getIdDb());
                            listposts.add(post);

                            ListAdapterPostSaved listAdapter = new ListAdapterPostSaved(listposts, folderContentActivity.this, new ListAdapterPostSaved.OnItemClickListener(){
                                @Override
                                public void onItemClick(Post item) {
                                    detailPostFromFolder(item);
                                }
                            });
                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(folderContentActivity.this));
                                    recyclerView.setAdapter(listAdapter);
                                }
                            });
                        };

                        @Override
                        public void onFailure(Call<SingleTweet> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    public void detailPostFromFolder(Post item){
        Intent intent = new Intent(folderContentActivity.this, tweetDetailsActivity.class);
        intent.putExtra("Post", item);
        intent.putExtra("Saved", 1);
        startActivity(intent);
    }



    // Accion al pulsar el boton de "guardar post"
    public void addPostToCarpeta(View v) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(folderContentActivity.this);
                b = (ImageButton) v;
                AlertDialog.Builder popupFolders = new AlertDialog.Builder(folderContentActivity.this);

                popupFolders.setTitle("Borrar post de carpeta").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Obtencion del ID del post
                        ImageButton imgButton = (ImageButton) v;
                        long post_id = (long) imgButton.getTag(R.string.idSaveDb);
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                database.getPostDao().delete(post_id);
                            }
                        });
                        Toast.makeText(folderContentActivity.this, "Se ha borrado con éxito.", Toast.LENGTH_SHORT).show();
                        showContentFolder();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog alertDialog = popupFolders.create();
                        alertDialog.show();
                    }
                });
            }
        });
    }

    // Accion al pulsar el boton de "compartir post"
    public void compartirPost(View v){
        // Accion de compartir
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Poner aqui enlace del tweet");
        intent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(intent, null);
        startActivity(shareIntent);

    }
}