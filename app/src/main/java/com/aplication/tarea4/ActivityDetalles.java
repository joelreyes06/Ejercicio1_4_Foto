package com.aplication.tarea4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.aplication.tarea4.tablas.Empleados;

import java.io.ByteArrayInputStream;

public class ActivityDetalles extends AppCompatActivity {

    EditText MostrarNombre, MostrarDescripcion;
    ImageView MostrarImagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);


        MostrarNombre = (EditText) findViewById(R.id.txt_nom);
        MostrarImagen = (ImageView) findViewById(R.id.fotografiaLista);

        MostrarDescripcion = (EditText) findViewById(R.id.txtnota);
        Bundle objetEnvia = getIntent().getExtras();


        Empleados imagen = null;
        if(objetEnvia != null){
            imagen = (Empleados) objetEnvia.getSerializable("empleados");

            MostrarNombre.setText(imagen.getNombres());
            MostrarDescripcion.setText(imagen.getDescripcion());

            mostrarImagen(imagen.getImage());
            Bitmap image = BitmapFactory.decodeFile(imagen.getPathImage());

            MostrarImagen.setImageBitmap(image);
        }

    }
    private void mostrarImagen(byte[] image) {
        Bitmap bitmap = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(image);
        bitmap = BitmapFactory.decodeStream(bais);
        MostrarImagen.setImageBitmap(bitmap);
    }

}