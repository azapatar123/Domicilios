package com.example.domicilios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuActivity extends AppCompatActivity {

    Button btnRegistrar, btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnConsultar = findViewById(R.id.btnConsultar);

        btnRegistrar.setVisibility(View.GONE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            FirebaseFirestore.getInstance()
                    .collection("usuarios")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(doc -> {
                        String perfil = doc.getString("perfil");
                        if (perfil != null && perfil.equalsIgnoreCase("operador")) {
                            btnRegistrar.setVisibility(View.VISIBLE);
                        }
                    });
        }

        btnRegistrar.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, RegistrarPedidoActivity.class)));

        btnConsultar.setOnClickListener(v ->
                Toast.makeText(MenuActivity.this,
                        "Próximamente", Toast.LENGTH_SHORT).show());
    }
}