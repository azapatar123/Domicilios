package com.example.domicilios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroActivity extends AppCompatActivity {

    EditText txtNombre, txtCorreo, txtPassword;
    EditText txtNumeroDocumento, txtTelefono, txtDireccion;

    Button btnRegistrar;

    FirebaseAuth auth;
    FirebaseFirestore db;

    String perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro);

        perfil = getIntent().getStringExtra("perfil");
        if (perfil == null) {
            perfil = "cliente";
        }

        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);
        txtNumeroDocumento = findViewById(R.id.txtNumeroDocumento);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegistrar.setOnClickListener(v -> {

            String nombre = txtNombre.getText().toString().trim();
            String correo = txtCorreo.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            String numeroDocumento = txtNumeroDocumento.getText().toString().trim();
            String telefono = txtTelefono.getText().toString().trim();
            String direccion = txtDireccion.getText().toString().trim();

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()
                    || numeroDocumento.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
                Toast.makeText(RegistroActivity.this,
                        "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(correo, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Usuario usuario = new Usuario(
                                    nombre, correo, perfil, "Activo",
                                    numeroDocumento, telefono, direccion,
                                    null, null
                            );

                            String uid = auth.getCurrentUser().getUid();

                            db.collection("usuarios")
                                    .document(uid)
                                    .set(usuario)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(RegistroActivity.this,
                                                "Usuario registrado", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(RegistroActivity.this,
                                                    "Error Firestore: " + e.getMessage(),
                                                    Toast.LENGTH_LONG).show());

                        } else {
                            String mensaje = task.getException() != null
                                    ? task.getException().getMessage()
                                    : "desconocido";

                            Toast.makeText(RegistroActivity.this,
                                    "Error Auth: " + mensaje,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}