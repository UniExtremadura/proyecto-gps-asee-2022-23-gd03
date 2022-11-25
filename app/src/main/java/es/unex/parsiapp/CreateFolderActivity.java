package es.unex.parsiapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.roomdb.ParsiDatabase;

public class CreateFolderActivity extends AppCompatActivity {

    private Carpeta editedFolder = null;

    /* Metodos de Callback */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_folder);

        /* Si createFolder es true, se creara una nueva carpeta. Si es false se editara una carpeta
            ya existente y se cargaran los datos de esta en la IU */
        boolean createFolder = true;
        createFolder = getIntent().getBooleanExtra("create", createFolder);

        // Si se esta editando una columna, se prepara la IU para cargar los datos
        if (!createFolder){
            long id_folder = 1;
            id_folder = getIntent().getLongExtra("idfolder", id_folder);
            setForEditFolder(id_folder);
        }
    }

    public void setForEditFolder(long id_folder){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ParsiDatabase database = ParsiDatabase.getInstance(CreateFolderActivity.this);

                editedFolder = database.getCarpetaDao().getFolder(id_folder);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        EditText fname = (EditText) findViewById(R.id.folderName);
                        fname.setText(editedFolder.getNombre());

                        Button b = (Button) findViewById(R.id.create_folder_button);
                        b.setText("Editar");
                    }
                });
            }
        });
    }

    // Accion a realizar al pulsar el boton de crear tras establecer el nombre
    public void onConfirmCreateFolderButton(View v){
        // Obtencion del nombre
        EditText fname = findViewById(R.id.folderName);
        String folderName = fname.getText().toString();
        // Si el nombre no esta vacio
        if(folderName.length() > 0) {
            // Crea la carpeta
            if(editedFolder != null){
                editFolder(folderName);
            } else {
                createFolder(folderName);
            }
            finish();
        }
    }

    // Crea una nueva carpeta con nombre folderName
    public void createFolder(String folderName){
        Carpeta c = new Carpeta(folderName);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(CreateFolderActivity.this);
                // Insercion en BD
                long id = database.getCarpetaDao().insert(c);
                c.setIdDb(id);
            }
        });
    }

    // Edita una carpeta (le asigna un nuevo nombre)
    public void editFolder(String folderName){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(CreateFolderActivity.this);
                // Cambio de nombre y update en BD
                editedFolder.setNombre(folderName);
                database.getCarpetaDao().update(editedFolder);
            }
        });
    }
}