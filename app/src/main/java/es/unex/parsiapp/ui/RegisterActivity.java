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

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel mViewModel;
    private Usuario user;
    private String username;
    private String password;

    // --- Métodos de Callback ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AppContainer appContainer = ((MyApplication) this.getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.Rfactory).get(RegisterViewModel.class);

        mViewModel.getUser().observe(this, usuario -> {
            user = usuario;
        });

        Button b = (Button) findViewById(R.id.register_confirm_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText us = (EditText) findViewById(R.id.username_register);
                username = us.getText().toString();

                EditText ps = (EditText) findViewById(R.id.password_register);
                password = ps.getText().toString();

                mViewModel.setUser(username);
                registro();
            }
        });
    }

    // --- Otros métodos ---

    // Registro del usuario
    public void registro() {

        String mensaje;
        boolean logged = false;
        if (username != null && password != null) {
            if (user != null) {
                mensaje = "Error, ya existe un usuario con ese nombre";
            } else {
                if (username.length() > 0 && password.length() > 0) {
                    Usuario newU = new Usuario(username, password);
                    mViewModel.createUser(newU);

                    mensaje = "¡Bienvenido a Parsi, " + newU.getUsername() + "!";
                    logged = true;
                } else {
                    mensaje = "¡No puedes dejar vacío ningún campo al registrarte!";
                }
            }
            Toast.makeText(RegisterActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            if (logged) {
                Intent intent = new Intent(RegisterActivity.this, MenuLateralActivity.class);
                startActivity(intent);
            }
        }
    }
}