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

public class LoginActivity extends AppCompatActivity {

    private String mensaje;
    private boolean logged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button bLogin = (Button) findViewById(R.id.login_confirm_button);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText us = (EditText) findViewById(R.id.username_login);
                String username = us.getText().toString();

                EditText ps = (EditText) findViewById(R.id.password_login);
                String password = ps.getText().toString();

                iniciarSesion(username, password);
            }
        });

        Button bRegister = (Button) findViewById(R.id.register_button);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void iniciarSesion(String username, String password){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(LoginActivity.this);

                // Obtencion Usuario
                Usuario u = database.getUsuarioDao().getUsuarioFromUsername(username);
                if(u != null){
                    if(u.getPassword().equals(password)){
                        mensaje = "Bienvenido, " + u.getUsername();
                        logged = true;
                    } else {
                        mensaje = "Contraseña errónea, no se ha podido iniciar sesión";
                        logged = false;
                    }
                } else {
                    mensaje = "Datos erróneos, no se ha podido iniciar sesión";
                    logged = false;
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                        if(logged){
                            Intent intent = new Intent(LoginActivity.this, MenuLateralActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}