package es.unex.parsiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import es.unex.parsiapp.databinding.ActivityMenuLateralBinding;
import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.roomdb.ParsiDatabase;

public class MenuLateralActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuLateralBinding binding;

    ImageButton b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuLateralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenuLateral.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_columnas, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_lateral);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_lateral);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Accion al pulsar boton de "crear carpeta"
    public void onCreateFolderButton(View v){
        Intent intent = new Intent(MenuLateralActivity.this, CreateFolderActivity.class);
        intent.putExtra("create", true);
        // Se inicia la actividad CreateFolderActivity
        startActivity(intent);
    }

    /* Metodos añadidos*/
    // Accion al pulsar el boton de "crear columna"
    public void onCreateColumnButton(View v){
        Intent intent = new Intent(MenuLateralActivity.this, CreateColumnActivity.class);
        // Se agrega el extra "create". Si es true, la columna se crea. Si es false, la columna se edita.
        intent.putExtra("create", true);
        // Se inicia la actividad CreateColumnActivity
        startActivity(intent);
    }

    // Accion al pulsar el boton de "editar columna"
    public void onEditColumnButton(View v){
        long id_columna = (long) v.getTag(R.string.idEdit);

        Intent intent = new Intent(MenuLateralActivity.this, CreateColumnActivity.class);
        // Se agrega el extra "create". Si es true, la columna se crea. Si es false, la columna se edita.
        intent.putExtra("create", false);
        // Se agrega el extra "idcolumna" para saber que columna se esta editando
        intent.putExtra("idcolumna", id_columna);
        // Se inicia la actividad CreateColumnActivity
        startActivity(intent);
    }

    public void nightmode(View v) {

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch swi = findViewById(R.id.switchNightmode);
        if(swi.isChecked()) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    // Accion al pulsar el boton de "borrar" tanto en una carpeta como en una columna
    public void onDeleteButton(View v){
        String deletedElement = null;

        // Obtencion del nombre e ID de la carpeta/columna
        Button b = (Button) v;
        long id = (long) b.getTag(R.string.idDelete);

        switch(v.getId())
        {
            case R.id.deleteFolder:
                deletedElement = "Folder";
                break;
            case R.id.deleteColumn:
                deletedElement = "Column";
                break;
        }
        // Se pasan el ID y el elemento a borrar como Extras en el Intent
        Intent intent = new Intent(MenuLateralActivity.this, DeleteActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("deletedElement", deletedElement);

        // Se inicia la actividad DeleteActivity
        startActivity(intent);
    }

    // Accion al pulsar el boton de "editar carpeta"
    public void onEditFolderButton(View v){
        // Obtencion del nombre e ID de carpeta
        Button b = (Button) v;
        long idFolder = (long) b.getTag(R.string.idFolder);

        // Se pasan el nombre e ID de la carpeta como Extras en el Intent
        Intent intent = new Intent(MenuLateralActivity.this, CreateFolderActivity.class);
        intent.putExtra("idfolder", idFolder);
        intent.putExtra("create", false);
        // Se inicia la actividad CreateFolderActivity
        startActivity(intent);
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

    // Accion al pulsar el boton de "guardar post"
    public void addPostToCarpeta(View v){

        final long[] folder_id = {1};

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(MenuLateralActivity.this);
                List<Carpeta> folders = database.getCarpetaDao().getAll();
                String[] nameFolders = new String[folders.size()];

                for(int i = 0; i < folders.size(); i++) {
                    nameFolders[i] = folders.get(i).getNombre();
                }

                b = (ImageButton) v;
                AlertDialog.Builder popupFolders = new AlertDialog.Builder(MenuLateralActivity.this);

                if(folders.size()>0){
                    popupFolders.setTitle("Seleccione una carpeta").setItems(nameFolders, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String data = "Se ha guardado en la carpeta " + folders.get(which).getNombre();
                            Toast.makeText(MenuLateralActivity.this, data, Toast.LENGTH_SHORT).show();
                            folder_id[0] = folders.get(which).getIdDb();


                            // Obtencion del ID del post
                            ImageButton imgButton = (ImageButton) v;
                            String post_id = (String) imgButton.getTag(R.string.idSave);
                            // Insertar post
                            Post p = new Post(post_id, folder_id[0]);
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    database.getPostDao().insert(p);
                                }
                            });
                        }
                    });
                }else{
                    popupFolders.setTitle("Crea una nueva carpeta")
                            .setPositiveButton("CREAR CARPETA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MenuLateralActivity.this, CreateFolderActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                }


                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog alertDialog = popupFolders.create();
                        alertDialog.show();
                    }
                });
            }
        });
    }
}