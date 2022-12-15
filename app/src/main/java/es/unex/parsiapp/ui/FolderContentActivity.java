package es.unex.parsiapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.parsiapp.MyApplication;
import es.unex.parsiapp.R;
import es.unex.parsiapp.listadapter.ListAdapterPostSaved;
import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.repository.PostRepository;
import es.unex.parsiapp.roomdb.ParsiDatabase;
import es.unex.parsiapp.util.AppContainer;
import es.unex.parsiapp.util.AppExecutors;


public class FolderContentActivity extends AppCompatActivity {

    ImageButton b;
    private PostRepository mRepository;
    private FolderContentViewModel mViewModel;
    private ListAdapterPostSaved mAdapter;

    // --- Métodos de Callback ---

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_folder_content);

        ParsiDatabase db = ParsiDatabase.getInstance(this);

        AppContainer appContainer = ((MyApplication) this.getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.FCfactory).get(FolderContentViewModel.class);

        mAdapter = new ListAdapterPostSaved(mViewModel.getPosts().getValue(), FolderContentActivity.this, new ListAdapterPostSaved.OnItemClickListener(){
            @Override
            public void onItemClick(Post item) {
                detailPostFromFolder(item);
            }
        });

        mViewModel.getPosts().observe(this, posts -> {
            mAdapter.swap(posts);
        });

        showContentFolder();
    }

    // --- Otros métodos ---

    // Muestra el contenido de la carpeta
    public void showContentFolder(){
        Carpeta item = (Carpeta) getIntent().getSerializableExtra("folderContent");
        mViewModel.setCarpeta(item);
        onPostsLoaded();
    }

    // Actualiza la UI con los posts
    public void onPostsLoaded(){
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FolderContentActivity.this));
        recyclerView.setAdapter(mAdapter);
    }

    // Ir a los detalles de un Post desde una carpeta
    public void detailPostFromFolder(Post item){
        Intent intent = new Intent(FolderContentActivity.this, TweetDetailsActivity.class);
        intent.putExtra("Post", item);
        intent.putExtra("Saved", 1);
        startActivity(intent);
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


    // Accion al pulsar el boton de "eliminar post"
    public void deletePostFromCarpeta(View v){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(FolderContentActivity.this);
                b = (ImageButton) v;
                AlertDialog.Builder popupFolders = new AlertDialog.Builder(FolderContentActivity.this);

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
                        Toast.makeText(FolderContentActivity.this, "Se ha borrado con éxito.", Toast.LENGTH_SHORT).show();
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
}