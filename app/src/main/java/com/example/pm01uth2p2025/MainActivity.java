package com.example.pm01uth2p2025;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm01uth2p2025.Configuracion.SQLiteConexion;
import com.example.pm01uth2p2025.Configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    EditText nombres, apellidos, correo, telefono;
    Button btnaceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nombres = (EditText) findViewById(R.id.nombres);
        apellidos = (EditText) findViewById(R.id.apellidos);
        correo = (EditText) findViewById(R.id.correo);
        telefono = (EditText) findViewById(R.id.telefono);
        btnaceptar = (Button) findViewById(R.id.button);

        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarPersona();
            }
        });
    }

    private void agregarPersona()
    {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDB, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Transacciones.nombres, nombres.getText().toString());
        values.put(Transacciones.apellidos, apellidos.getText().toString());
        values.put(Transacciones.correo, correo.getText().toString());
        values.put(Transacciones.telefono, telefono.getText().toString());

        Long resultado = db.insert(Transacciones.tablaPersonas, Transacciones.id, values);

        Toast.makeText(getApplicationContext(),
                "Persona ingresada con exito " + resultado.toString(),
                Toast.LENGTH_LONG).show();

        db.close();

    }


}