package es.unex.parsiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unex.parsiapp.model.Carpeta;

public class ListAdapterFolder extends RecyclerView.Adapter<ListAdapterFolder.ViewHolder> {
    private List<Carpeta> mData;
    private LayoutInflater mInlfater;
    private Context context;
    final ListAdapterFolder.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Carpeta item);
    }

    public ListAdapterFolder(List<Carpeta> folderList, Context context, ListAdapterFolder.OnItemClickListener listener){
        this.mInlfater = LayoutInflater.from(context);
        this.context = context;
        this.mData = folderList;
        this.listener = listener;
    }

    //Obtiene el numero de carpeta que hay en una lista
    @Override
    public int getItemCount(){ return mData.size();}

    //Establece el diseño que tiene que tener cada carpeta al mostrarse
    @Override
    public ListAdapterFolder.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInlfater.inflate(R.layout.list_folder, null);
        return new ListAdapterFolder.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdapterFolder.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    //Reestablece el contenidode la variable mData, es decir una nueva lista de carpeta
    public void setItems(List<Carpeta> folderList) { mData = folderList;}


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Elementos de la UI
        TextView nombre;
        Button bEdit, bRemove;

        ViewHolder(View itemView){
            super(itemView);
            // Obtencion de elementos de la UI
            nombre = itemView.findViewById(R.id.nameFolderView);
            bEdit = itemView.findViewById(R.id.editFolder);
            bRemove = itemView.findViewById(R.id.deleteFolder);
        }

        void bindData(@NonNull final Carpeta item) {

            nombre.setText(item.getNombre());

            // Establece el nombre y ID de la carpeta en el boton de editar
            bEdit.setTag(R.string.idEdit, item.getNombre());
            bEdit.setTag(R.string.idFolder, item.getIdDb());

            // Establece el ID de la carpeta en el boton de borrar
            bRemove.setTag(R.string.idDelete, item.getIdDb());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
