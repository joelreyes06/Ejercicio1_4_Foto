package com.aplication.tarea4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aplication.tarea4.procesos.SQLiteConexion;
import com.aplication.tarea4.procesos.Transacciones;
import com.aplication.tarea4.tablas.Empleados;

import java.util.ArrayList;

public class ActivityListImag extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView listaLV;



    ArrayList<String> ArregloImagenes;
    ArrayList<Empleados> ListaImagenes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_imag);

        conexion  = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        listaLV = (ListView) findViewById(R.id.lista_guardados);
        ObtenerLista();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloImagenes);
        listaLV.setAdapter(adp);

        listaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarImagen(i);
            }
        });
    }

    private void ObtenerLista()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Empleados list_emple = null;
        ListaImagenes = new ArrayList<Empleados>();

        Cursor cursor = db.rawQuery(Transacciones.SELECT_ALL_TABLE_PICTURE,null);

        while(cursor.moveToNext())
        {
            list_emple = new Empleados();

            list_emple.setId(cursor.getInt(0));                 //ID
            list_emple.setNombres(cursor.getString(1));         //Nombre
            list_emple.setDescripcion(cursor.getString(2));     //Descripcion
            list_emple.setPathImage(cursor.getString(3));
            list_emple.setImage(cursor.getBlob(4));

            ListaImagenes.add(list_emple);
        }

        cursor.close();
        llenarlista();
    }
    private void mostrarImagen(int i){

        Empleados empleados = ListaImagenes.get(i);

        Bundle bundle = new Bundle();
        bundle.putSerializable("empleados", empleados);

        Intent intent = new Intent(getApplicationContext(), ActivityDetalles.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    private void llenarlista()
    {
        ArregloImagenes = new ArrayList<String>();

        for(int i=0; i<ListaImagenes.size(); i++)
        {
            ArregloImagenes.add(ListaImagenes.get(i).getId() +" | "+
                    ListaImagenes.get(i).getNombres() +" | "+
                    ListaImagenes.get(i).getDescripcion());
        }

    }
}