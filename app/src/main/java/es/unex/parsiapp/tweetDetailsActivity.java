package es.unex.parsiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.roomdb.ParsiDatabase;

public class tweetDetailsActivity extends AppCompatActivity {
    private ShapeableImageView userImage;
    private TextView nombre, userName, tweet, tweetID;
    private ImageButton share, save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tweet);

        Post item = (Post) getIntent().getSerializableExtra("Post");
        int sa = (int) getIntent().getSerializableExtra("Saved");
        userImage = findViewById(R.id.iconImageViewDetailTweet);
        nombre = findViewById(R.id.nameViewDetailTweet);
        userName = findViewById(R.id.userNameViewDetailTweet);
        tweet = findViewById(R.id.tweetViewDetailTweet);
        tweetID = findViewById(R.id.tweetID);
        share = findViewById(R.id.shareDetailTweet);
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
    }
}