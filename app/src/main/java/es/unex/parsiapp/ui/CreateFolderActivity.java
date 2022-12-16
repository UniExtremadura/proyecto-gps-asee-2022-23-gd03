package es.unex.parsiapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.unex.parsiapp.MyApplication;
import es.unex.parsiapp.R;
import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.util.AppContainer;

public class CreateFolderActivity extends AppCompatActivity {

    private Carpeta editedFolder = null;
    private CreateFolderViewModel mViewModel;

    /* --- Metodos de Callback --- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_folder);

        /* Si createFolder es true, se creara una nueva carpeta. Si es false se editara una carpeta
            ya existente y se cargaran los datos de esta en la IU */
        boolean createFolder = true;
        createFolder = getIntent().getBooleanExtra("create", createFolder);

        AppContainer appContainer = ((MyApplication) this.getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.factory).get(CreateFolderViewModel.class);

        mViewModel.getCarpetaBeingEdited().observe(this, carpeta -> {
            editedFolder = carpeta;
            setUIForEditFolder();
        });

        // Si se esta editando una columna, se prepara la IU para cargar los datos
        if (!createFolder){
            long id_folder = 1;
            id_folder = getIntent().getLongExtra("idfolder", id_folder);
            mViewModel.setCarpetaBeingEdited(id_folder);
        }
    }

    // --- Otros métodos ---

    // Prepara la UI para editar una carpeta
    public void setUIForEditFolder(){
        EditText fname = (EditText) findViewById(R.id.folderName);
        fname.setText(editedFolder.getNombre());

        Button b = (Button) findViewById(R.id.create_folder_button);
        b.setText("Editar");
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
        mViewModel.createFolder(c);
    }

    // Edita una carpeta (le asigna un nuevo nombre)
    public void editFolder(String folderName){
        editedFolder.setNombre(folderName);
        mViewModel.editFolder(editedFolder);
    }
}