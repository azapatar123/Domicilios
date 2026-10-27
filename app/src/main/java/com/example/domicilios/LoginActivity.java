package com.example.domicilios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText txtCorreo, txtPassword;

    Button btnLogin;
    Button btnIrRegistro;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin = findViewById(R.id.btnLogin);

        btnIrRegistro =
                findViewById(R.id.btnIrRegistro);

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {

            String correo =
                    txtCorreo.getText().toString();

            String password =
                    txtPassword.getText().toString();

            auth.signInWithEmailAndPassword(
                    correo,
                    password
            ).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    Toast.makeText(
                            LoginActivity.this,
                            "Bienvenido",
                            Toast.LENGTH_SHORT
                    ).show();

                    Intent intent = new Intent(
                            LoginActivity.this,
                            MenuActivity.class
                    );

                    startActivity(intent);

                } else {

                    Toast.makeText(
                            LoginActivity.this,
                            "Credenciales incorrectas",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            });

        });

        btnIrRegistro.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    SeleccionTipoUsuarioActivity.class
            );

            startActivity(intent);

        });

    }
}