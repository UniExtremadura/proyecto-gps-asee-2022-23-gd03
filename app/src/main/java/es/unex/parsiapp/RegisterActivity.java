package es.unex.parsiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.parsiapp.model.Usuario;
import es.unex.parsiapp.roomdb.ParsiDatabase;

public class RegisterActivity extends AppCompatActivity {

    private String mensaje;
    private boolean logged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button b = (Button) findViewById(R.id.register_confirm_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText us = (EditText) findViewById(R.id.username_register);
                String username = us.getText().toString();

                EditText ps = (EditText) findViewById(R.id.password_register);
                String password = ps.getText().toString();

                registro(username, password);
            }
        });
    }

    public void registro(String username, String password){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(RegisterActivity.this);

                // Obtencion Usuario
                Usuario u = database.getUsuarioDao().getUsuarioFromUsername(username);
                if(u != null){
                    mensaje = "Error, ya existe un usuario con ese nombre";
                    logged = false;
                } else {
                    if(username.length() > 0 && password.length() > 0){
                        Usuario newU = new Usuario(username, password);
                        database.getUsuarioDao().insert(newU);

                        mensaje = "¡Bienvenido a Parsi, " + newU.getUsername() + "!";
                        logged = true;

                    } else {
                        mensaje = "¡No puedes dejar vacío ningún campo al registrarte!";
                        logged = false;
                    }
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                        if(logged){
                            Intent intent = new Intent(RegisterActivity.this, MenuLateralActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}