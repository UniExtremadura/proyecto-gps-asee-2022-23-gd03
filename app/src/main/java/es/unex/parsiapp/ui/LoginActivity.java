package es.unex.parsiapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.unex.parsiapp.MyApplication;
import es.unex.parsiapp.R;
import es.unex.parsiapp.model.Usuario;
import es.unex.parsiapp.util.AppContainer;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mViewModel;
    private Usuario user;
    private String username;
    private String password;

    // --- Métodos de Callback ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppContainer appContainer = ((MyApplication) this.getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.factory).get(LoginViewModel.class);

        mViewModel.getUser().observe(this, usuario -> {
            user = usuario;
            iniciarSesion();
        });

        Button bLogin = (Button) findViewById(R.id.login_confirm_button);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText us = (EditText) findViewById(R.id.username_login);
                username = us.getText().toString();

                EditText ps = (EditText) findViewById(R.id.password_login);
                password = ps.getText().toString();

                mViewModel.setUser(username);
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

    // --- Otros métodos ---

    // Inicio de sesión
    public void iniciarSesion(){
        String mensaje;
        boolean logged = false;
        if(user != null){
            if(user.getPassword().equals(password)){
                mensaje = "Bienvenido, " + user.getUsername();
                logged = true;
            } else {
                mensaje = "Contraseña errónea, no se ha podido iniciar sesión";
            }
        } else {
            mensaje = "Datos erróneos, no se ha podido iniciar sesión";
        }
        Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
        if(logged){
            Intent intent = new Intent(LoginActivity.this, MenuLateralActivity.class);
            startActivity(intent);
        }
    }
}