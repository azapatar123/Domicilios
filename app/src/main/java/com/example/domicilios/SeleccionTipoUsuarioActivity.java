package com.example.domicilios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionTipoUsuarioActivity extends AppCompatActivity {

    Button cardCliente, cardMensajero, cardOperador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seleccion_tipo);

        cardCliente = findViewById(R.id.cardCliente);
        cardMensajero = findViewById(R.id.cardMensajero);
        cardOperador = findViewById(R.id.cardOperador);

        cardCliente.setOnClickListener(v -> irARegistro("cliente"));
        cardMensajero.setOnClickListener(v -> irARegistro("mensajero"));
        cardOperador.setOnClickListener(v -> irARegistro("operador"));
    }

    private void irARegistro(String perfil) {
        Intent intent;

        if (perfil.equals("mensajero")) {
            intent = new Intent(this, RegistroMensajeroActivity.class);
        } else {
            intent = new Intent(this, RegistroActivity.class);
        }

        intent.putExtra("perfil", perfil);
        startActivity(intent);
    }
}