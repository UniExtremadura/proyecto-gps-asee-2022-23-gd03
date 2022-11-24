package es.unex.parsiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unex.parsiapp.model.Columna;

public class ListAdapterColumna extends RecyclerView.Adapter<ListAdapterColumna.ViewHolder>{
    private List<Columna> mData;
    private LayoutInflater mInlfater;
    private Context context;
    final ListAdapterColumna.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Columna item);
    }

    public ListAdapterColumna(List<Columna> columnList, Context context, ListAdapterColumna.OnItemClickListener listener){
        this.mInlfater = LayoutInflater.from(context);
        this.context = context;
        this.mData = columnList;
        this.listener = listener;
    }

    //Obtiene el numero de columnas que hay en una lista
    @Override
    public int getItemCount(){ return mData.size();}

    //Establece el diseño que tiene que tener cada columna al mostrarse
    @Override
    public ListAdapterColumna.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInlfater.inflate(R.layout.list_column, null);
        return new ListAdapterColumna.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdapterColumna.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    //Reestablece el contenidode la variable mData, es decir una nueva lista de columna
    public void setItems(List<Columna> columnaList) { mData = columnaList;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Elementos de la UI
        TextView nombre;
        Button bEdit, bRemove;

        ViewHolder(View itemView) {
            super(itemView);
            // Obtencion de elementos de la UI
            nombre = itemView.findViewById(R.id.nameColumnView);
            bEdit = itemView.findViewById(R.id.editColumn);
            bRemove = itemView.findViewById(R.id.deleteColumn);
        }

        void bindData(@NonNull final Columna item) {

            nombre.setText(item.getNombre());

            // Establece el ID de la columna en el boton de editar
            bEdit.setTag(R.string.idEdit, item.getIdDb());

            // Establece el ID de la columna en el boton de borrar
            bRemove.setTag(R.string.idDelete, item.getIdDb());
            if (item.isColumnaActual()) {
                ImageView imageView = (ImageView) itemView.findViewById(R.id.iconImageView);
                imageView.setImageResource(R.drawable.ic_baseline_home_24);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
