package com.example.pm01uth2p2025;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm01uth2p2025.Configuracion.Personas;
import com.example.pm01uth2p2025.Configuracion.SQLiteConexion;
import com.example.pm01uth2p2025.Configuracion.Transacciones;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listapersonas;
    ArrayList<Personas> lista;
    ArrayList<String> ArregloList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista);

        listapersonas = (ListView) findViewById(R.id.listpersonas);
        conexion = new SQLiteConexion(this, Transacciones.NameDB, null, 1);

        ObtenerDatos();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloList);
        listapersonas.setAdapter(adp);

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
        ArregloList = new ArrayList<String>();

        for(int i = 0; i< lista.size(); i++)
        {
            ArregloList.add(lista.get(i).getNombres() + " " +
                            lista.get(i).getApellidos() + " " +
                            lista.get(i).getCorreo());
        }

    }
}