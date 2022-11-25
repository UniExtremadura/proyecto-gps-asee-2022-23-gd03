package es.unex.parsiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.unex.parsiapp.model.Post;

public class ListAdapterPost extends RecyclerView.Adapter<ListAdapterPost.ViewHolder> {
    private List<Post> mData;
    private LayoutInflater mInlfater;
    private Context context;
    final ListAdapterPost.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Post item);
    }

    public ListAdapterPost(List<Post> postList, Context context, ListAdapterPost.OnItemClickListener listener){
        this.mInlfater = LayoutInflater.from(context);
        this.context = context;
        this.mData = postList;
        this.listener = listener;
    }

    //Obtiene el numero de post que hay en una lista
    @Override
    public int getItemCount(){ return mData.size();}

    //Establece el diseño que tiene que tener cada post al mostrarse
    @Override
    public ListAdapterPost.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInlfater.inflate(R.layout.list_tweet, null);
        return new ListAdapterPost.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdapterPost.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    //Reestablece el contenidode la variable mData, es decir una nueva lista de posts
    public void setItems(List<Post> postList) { mData = postList;}


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Elementos de la UI
        ShapeableImageView userImage;
        TextView nombre, userName, time, tweet, tweetID;
        ImageButton share, save;

        ViewHolder(View itemView){
            super(itemView);
            // Obtencion de los elementos de la UI
            userImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.nameView);
            userName = itemView.findViewById(R.id.userNameView);
            time = itemView.findViewById(R.id.timeView);
            tweet = itemView.findViewById(R.id.tweetView);
            tweetID = itemView.findViewById(R.id.tweetID);
            share = itemView.findViewById(R.id.share);
            save = itemView.findViewById(R.id.save);
        }

        void bindData(@NonNull final Post item) {
            // Llamada a Picasso para generar la imagen en base a la URL de la foto de perfil
            Picasso.get()
                    .load(item.getProfilePicture())
                    .error(R.mipmap.ic_launcher_round)
                    .fit()
                    .into(userImage)
            ;
            nombre.setText(item.getAuthorUsername());
            userName.setText("@" + item.getAuthorUsername());
            time.setText(item.getTimestamp());
            tweet.setText(item.getContenido());
            tweetID.setText(item.getId());
            // Establece el ID del post en los botones de share y save
            share.setTag(R.string.idShare, item.getId());
            save.setTag(R.string.idSave, item.getId());
            save.setTag(R.string.idSaveDb, item.getIdDb());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}