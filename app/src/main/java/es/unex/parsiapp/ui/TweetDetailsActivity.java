package es.unex.parsiapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.unex.parsiapp.MyApplication;
import es.unex.parsiapp.R;
import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.util.AppContainer;

public class TweetDetailsActivity extends AppCompatActivity {

    private ImageButton save;
    private TweetDetailsViewModel mViewModel;
    private List<Carpeta> allFolders;

    // --- Métodos de Callback ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tweet);

        Post item = (Post) getIntent().getSerializableExtra("Post");
        int sa = (int) getIntent().getSerializableExtra("Saved");
        ShapeableImageView userImage = findViewById(R.id.iconImageViewDetailTweet);
        TextView nombre = findViewById(R.id.nameViewDetailTweet);
        TextView userName = findViewById(R.id.userNameViewDetailTweet);
        TextView tweet = findViewById(R.id.tweetViewDetailTweet);
        TextView tweetID = findViewById(R.id.tweetID);
        ImageButton share = findViewById(R.id.shareDetailTweet);
        save = findViewById(R.id.saveDetailTweet);

        Picasso.get()
                .load(item.getProfilePicture())
                .error(R.mipmap.ic_launcher_round)
                .fit()
                .into(userImage)
        ;
        nombre.setText(item.getAuthorUsername());
        userName.setText("@" + item.getAuthorUsername());
        tweet.setText(item.getContenido());
        tweetID.setText(item.getId());
        // Establece el ID del post en los botones de share y save
        share.setTag(R.string.idShare, item.getId());
        if(sa==1){
            save.setImageResource(R.drawable.saved);
        }
        save.setTag(R.string.idSave, item.getId());

        AppContainer appContainer = ((MyApplication) this.getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.factory).get(TweetDetailsViewModel.class);

        mViewModel.getCarpetas().observe(this, carpetas -> {
            allFolders = carpetas;
        });
    }

    public void addPostToCarpetaTweet(View v){
        String[] nameFolders = new String[allFolders.size()];

        for(int i = 0; i < allFolders.size(); i++) {
            nameFolders[i] = allFolders.get(i).getNombre();
        }

        save = (ImageButton) v;
        AlertDialog.Builder popupFolders = new AlertDialog.Builder(TweetDetailsActivity.this);

        popupFolders.setTitle("Seleccione una carpeta").setItems(nameFolders, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savePostInFolder(v, allFolders, which);
            }
        });

        AlertDialog alertDialog = popupFolders.create();
        alertDialog.show();

    }

    // Guarda un post
    public void savePostInFolder(View v, List<Carpeta> folders, int which){
        String data = "Se ha guardado en la carpeta " + folders.get(which).getNombre();
        Toast.makeText(TweetDetailsActivity.this, data, Toast.LENGTH_SHORT).show();
        long folder_id = folders.get(which).getIdDb();

        // Obtencion del ID del post
        ImageButton imgButton = (ImageButton) v;
        String post_id = (String) imgButton.getTag(R.string.idSave);

        mViewModel.savePost(post_id, folder_id);
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