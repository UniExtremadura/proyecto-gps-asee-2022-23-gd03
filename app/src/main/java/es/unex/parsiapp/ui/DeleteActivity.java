package es.unex.parsiapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.atomic.AtomicBoolean;

import es.unex.parsiapp.MyApplication;
import es.unex.parsiapp.R;
import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.util.AppContainer;

public class DeleteActivity extends AppCompatActivity {

    private String elementTypeToDelete;
    private long elementId;
    private TextView tview;
    private String mensaje;
    private DeleteViewModel mViewModel;
    private Carpeta carpetaToDelete;
    private Columna columnaToDelete;

    // --- Métodos de Callback ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        this.elementTypeToDelete = this.getIntent().getStringExtra("deletedElement");
        this.elementId = this.getIntent().getLongExtra("id", elementId);

        tview = (TextView) findViewById(R.id.delete_textview);

        AppContainer appContainer = ((MyApplication) this.getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.factory).get(DeleteViewModel.class);

        AtomicBoolean alreadyObserved = new AtomicBoolean(false);

        if(elementTypeToDelete.equals("Folder")) {
            mViewModel.getCarpetaToDelete().observe(this, carpeta -> {
                if(!alreadyObserved.get()) {
                    carpetaToDelete = carpeta;
                    mensaje = "¿Seguro que quieres borrar la carpeta " + carpetaToDelete.getNombre() + "?";
                    tview.setText(mensaje);
                    alreadyObserved.set(true);
                }
            });
            mViewModel.setCarpetaToDelete(elementId);
        } else if (elementTypeToDelete.equals("Column")) {
            mViewModel.getColumnaToDelete().observe(this, columna -> {
                if(!alreadyObserved.get()) {
                    columnaToDelete = columna;
                    mensaje = "¿Seguro que quieres borrar la columna " + columnaToDelete.getNombre() + "?";
                    tview.setText(mensaje);
                    alreadyObserved.set(true);
                }
            });
            mViewModel.setColumnaToDelete(elementId);
        }
    }

    // --- Otros Métodos ---

    // Al presionar "Cancelar"
    public void onCancelDeleteButton(View v){
        Intent intent = new Intent(DeleteActivity.this, MenuLateralActivity.class);
        startActivity(intent);
    }

    // Al presionar "Confirmar"
    public void onConfirmDeleteButton(View v){
        if(elementTypeToDelete.equals("Folder")) {
            mViewModel.deleteCarpeta(elementId);
            mensaje = "Se ha eliminado la carpeta " + carpetaToDelete.getNombre();
        }else if (elementTypeToDelete.equals("Column")){
            mViewModel.deleteColumna(elementId);
            mensaje = "Se ha eliminado la columna " + columnaToDelete.getNombre();
        }
        Toast.makeText(DeleteActivity.this, mensaje, Toast.LENGTH_SHORT).show();
        finish();
    }
}