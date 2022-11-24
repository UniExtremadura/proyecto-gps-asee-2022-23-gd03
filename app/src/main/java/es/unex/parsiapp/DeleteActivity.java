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

                }else if (elementTypeToDelete.equals("Column")){
                    Columna c = database.getColumnaDao().getColumna(elementId);
                    mensaje = "¿Seguro que quieres borrar la columna " + c.getNombre() + "?";
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

                }else if (elementTypeToDelete.equals("Column")){
                    Columna c = database.getColumnaDao().getColumna(elementId);
                    database.getColumnaDao().deleteColumnaByID(elementId);
                    mensaje = "Se ha eliminado la columna " + c.getNombre();
                    if(c.isColumnaActual()){
                        try {
                            Columna newActual = database.getColumnaDao().getAll().get(0);
                            newActual.setColumnaActual(true);
                            database.getColumnaDao().update(newActual);
                            mensaje += ". Se ha establecido la columna " + newActual.getNombre() + " como columna actual";
                        }
                        catch (Exception e){

                        }
                    }
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