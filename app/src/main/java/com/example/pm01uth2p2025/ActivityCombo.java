package com.example.pm01uth2p2025;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm01uth2p2025.Configuracion.Personas;
import com.example.pm01uth2p2025.Configuracion.SQLiteConexion;
import com.example.pm01uth2p2025.Configuracion.Transacciones;

import java.util.ArrayList;

public class ActivityCombo extends AppCompatActivity {

    SQLiteConexion conexion;
    Spinner combopersonas;

    EditText nombres, apellidos, correo;
    ArrayList<Personas> lista;

    ArrayList<String> ArregloLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_combo);

        combopersonas = (Spinner) findViewById(R.id.spinner);

        nombres = (EditText) findViewById(R.id.cbnombre);
        apellidos = (EditText) findViewById(R.id.cbapellido);
        correo = (EditText) findViewById(R.id.cbcorreo);

        conexion = new SQLiteConexion(this, Transacciones.NameDB, null, 1);

        ObtenerDatos();

        ArrayAdapter<CharSequence> adp = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                ArregloLista
                );

        combopersonas.setAdapter(adp);


        combopersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try
                {
                    nombres.setText(lista.get(i).getNombres());
                    apellidos.setText(lista.get(i).getApellidos());
                    correo.setText(lista.get(i).getCorreo());
                }
                catch (Exception ex)
                {
                    Log.d("Error : ", ex.toString() );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void ObtenerDatos()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        lista = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery(Transacciones.SelectPersonas, null);

        while(cursor.moveToNext())
        {
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setApellidos(cursor.getString(2));
            person.setCorreo(cursor.getString(3));
            person.setTelefono(cursor.getString(4));

            lista.add(person);
        }

        cursor.close();

        FillData();
    }

    private void FillData()
    {
        ArregloLista = new ArrayList<String>();

        for(int i = 0; i< lista.size(); i++)
        {
            ArregloLista.add(lista.get(i).getNombres() + " " +
                    lista.get(i).getApellidos() + " " +
                    lista.get(i).getCorreo());
        }
    }
}