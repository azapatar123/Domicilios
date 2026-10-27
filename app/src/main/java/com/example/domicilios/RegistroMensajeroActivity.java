package com.example.domicilios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroMensajeroActivity extends AppCompatActivity {

    EditText txtNumeroDocumento, txtNombre, txtCorreo, txtPassword, txtPlaca;
    Spinner spTipoVehiculo;
    Button btnEnviar;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_mensajero);

        txtNumeroDocumento = findViewById(R.id.txtNumeroDocumento);
        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);
        spTipoVehiculo = findViewById(R.id.spTipoVehiculo);
        txtPlaca = findViewById(R.id.txtPlaca);
        btnEnviar = findViewById(R.id.btnEnviar);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.tipos_vehiculo,
                        android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spTipoVehiculo.setAdapter(adapter);

        btnEnviar.setOnClickListener(v -> {

            String numeroDocumento = txtNumeroDocumento.getText().toString().trim();
            String nombre = txtNombre.getText().toString().trim();
            String correo = txtCorreo.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            String tipoVehiculo = spTipoVehiculo.getSelectedItem().toString();
            String placa = txtPlaca.getText().toString().trim();

            if (numeroDocumento.isEmpty() || nombre.isEmpty() || correo.isEmpty()
                    || password.isEmpty() || placa.isEmpty()) {
                Toast.makeText(RegistroMensajeroActivity.this,
                        "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(correo, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Usuario usuario = new Usuario(
                                    nombre, correo, "mensajero", "Activo",
                                    numeroDocumento, null, null,
                                    tipoVehiculo, placa
                            );

                            String uid = auth.getCurrentUser().getUid();

                            db.collection("usuarios")
                                    .document(uid)
                                    .set(usuario)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(RegistroMensajeroActivity.this,
                                                "Mensajero registrado", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegistroMensajeroActivity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(RegistroMensajeroActivity.this,
                                                    "Error Firestore: " + e.getMessage(),
                                                    Toast.LENGTH_LONG).show());

                        } else {
                            String mensaje = task.getException() != null
                                    ? task.getException().getMessage()
                                    : "desconocido";

                            Toast.makeText(RegistroMensajeroActivity.this,
                                    "Error Auth: " + mensaje,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}