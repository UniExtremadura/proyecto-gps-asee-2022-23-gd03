package es.unex.parsiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.roomdb.ParsiDatabase;

public class DeleteActivity extends AppCompatActivity {

    private String elementTypeToDelete;
    private long elementId;
    private TextView tview;
    private String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        this.elementTypeToDelete = this.getIntent().getStringExtra("deletedElement");
        this.elementId = this.getIntent().getLongExtra("id", elementId);

        tview = (TextView) findViewById(R.id.delete_textview);

        setForDelete();
    }

    public void setForDelete(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(DeleteActivity.this);
                if(elementTypeToDelete.equals("Folder")) {
                    Carpeta c = database.getCarpetaDao().getFolder(elementId);
                    mensaje = "¿Seguro que quieres borrar la carpeta " + c.getNombre() + "?";
                }else if (elementTypeToDelete.equals("Column")){
                    // todo
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        tview.setText(mensaje);
                    }
                });
            }
        });
    }

    public void onCancelDeleteButton(View v){
        Intent intent = new Intent(DeleteActivity.this, MenuLateralActivity.class);
        startActivity(intent);
    }

    public void onConfirmDeleteButton(View v){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(DeleteActivity.this);
                if(elementTypeToDelete.equals("Folder")) {
                    Carpeta c = database.getCarpetaDao().getFolder(elementId);
                    database.getPostDao().deleteAllPostsFromCarpeta(elementId);
                    database.getCarpetaDao().deleteFolderByID(elementId);
                    mensaje = "Se ha eliminado la carpeta " + c.getNombre();
                }else if (elementTypeToDelete.equals("Column")){
                    // todo
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DeleteActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}