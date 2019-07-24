package com.abcsoft.accesocontactos;

import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewContactos;
    Button btnBuscarContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBuscarContactos = (Button) findViewById(R.id.idBtnContactos);
        textViewContactos = (TextView) findViewById(R.id.idTextView);

        textViewContactos.setText(String.valueOf(ContactsContract.Contacts.CONTENT_URI));
        obtenerDatos();

        btnBuscarContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatos();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void obtenerDatos(){

        //Fabrico un array con los nombres de las columnas que quiero acceder con la query
        //ContactsContract.Data es la tabla interna donde se guarda la información de contactos
        //Especificamos las columnas de la proyección
        //Ver la documentación si necesitamos más
        String[] columnas = new String[] {
                ContactsContract.Data._ID, //Constante que almacena el nombre de la columna ID
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE

        };
        Log.d("**",  String.join(" ",columnas));

        //Definimos el filtro de selección
        //Quiero que algo sea igual a otra cosa y que, además, el telefono no sea nulo
        //MIMETYPE -> tipo de conteido
        String selectionClause =
                ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE +"' AND " +
                ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";

        String orden = ContactsContract.Data.DISPLAY_NAME + " ASC";

        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,  //URI de contenido para los contactos
                columnas,                               //Array con las columnas que queremos obtener
                selectionClause,                        //La clausula del filtro
                null,                       //No hay parametros
                orden                                   //Criterio de ordenación
        );


        //Recorremos el cursor
        while (cursor.moveToNext()){
            textViewContactos.append("Identificador: " + cursor.getString(0) + "\n");
            textViewContactos.append("Nombre: " + cursor.getString(1) + "\n");
            textViewContactos.append("Teléfono: " + cursor.getString(2) + "\n");
            textViewContactos.append("Tipo Teléfono: " + cursor.getString(3) + "\n");
        }

    }


}
