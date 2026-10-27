package com.example.domicilios;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegistrarPedidoActivity extends AppCompatActivity {

    EditText txtFechaEntrega, txtNumeroPedido, txtDireccionEntrega,
            txtPrioridad, txtDocumentoCliente;
    Spinner spMensajero;
    Button btnEnviar;

    FirebaseAuth auth;
    FirebaseFirestore db;

    List<String> nombresMensajeros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrar_pedido);

        txtFechaEntrega = findViewById(R.id.txtFechaEntrega);
        txtNumeroPedido = findViewById(R.id.txtNumeroPedido);
        txtDireccionEntrega = findViewById(R.id.txtDireccionEntrega);
        txtPrioridad = findViewById(R.id.txtPrioridad);
        txtDocumentoCliente = findViewById(R.id.txtDocumentoCliente);
        spMensajero = findViewById(R.id.spMensajero);
        btnEnviar = findViewById(R.id.btnEnviar);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        txtFechaEntrega.setOnClickListener(v -> mostrarCalendario());

        cargarMensajeros();

        btnEnviar.setOnClickListener(v -> {

            String fechaEntrega = txtFechaEntrega.getText().toString().trim();
            String numeroPedido = txtNumeroPedido.getText().toString().trim();
            String direccionEntrega = txtDireccionEntrega.getText().toString().trim();
            String prioridad = txtPrioridad.getText().toString().trim();
            String documentoCliente = txtDocumentoCliente.getText().toString().trim();

            if (fechaEntrega.isEmpty() || numeroPedido.isEmpty() || direccionEntrega.isEmpty()
                    || prioridad.isEmpty() || documentoCliente.isEmpty()) {
                Toast.makeText(RegistrarPedidoActivity.this,
                        "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (spMensajero.getSelectedItem() == null) {
                Toast.makeText(RegistrarPedidoActivity.this,
                        "No hay mensajeros registrados", Toast.LENGTH_SHORT).show();
                return;
            }

            String mensajeroAsignado = spMensajero.getSelectedItem().toString();
            String creadoPor = auth.getCurrentUser().getUid();

            Pedido pedido = new Pedido(
                    fechaEntrega, numeroPedido, direccionEntrega, prioridad,
                    documentoCliente, mensajeroAsignado, "pendiente", creadoPor
            );

            db.collection("pedidos")
                    .add(pedido)
                    .addOnSuccessListener(docRef -> {
                        Toast.makeText(RegistrarPedidoActivity.this,
                                "Pedido registrado", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(RegistrarPedidoActivity.this,
                                    "Error Firestore: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show());
        });
    }

    private void mostrarCalendario() {
        Calendar hoy = Calendar.getInstance();

        DatePickerDialog dialogo = new DatePickerDialog(
                this,
                (view, anio, mes, dia) -> {
                    String fecha = String.format(Locale.getDefault(),
                            "%02d/%02d/%04d", dia, mes + 1, anio);
                    txtFechaEntrega.setText(fecha);
                },
                hoy.get(Calendar.YEAR),
                hoy.get(Calendar.MONTH),
                hoy.get(Calendar.DAY_OF_MONTH)
        );

        // No permite elegir fechas anteriores a hoy
        dialogo.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialogo.show();
    }

    private void cargarMensajeros() {
        db.collection("usuarios")
                .whereEqualTo("perfil", "mensajero")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    nombresMensajeros.clear();

                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        String nombre = doc.getString("nombre");
                        if (nombre != null) {
                            nombresMensajeros.add(nombre);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_spinner_item,
                            nombresMensajeros
                    );

                    adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                    );

                    spMensajero.setAdapter(adapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(RegistrarPedidoActivity.this,
                                "Error al cargar mensajeros: " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
    }
}