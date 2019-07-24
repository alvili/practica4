package com.abcsoft.accesoacamara;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {

    private Button btnAbrirCamara;
    private Button btnGuardarFoto;
    private ImageView imageView;

    private Bitmap imagenActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAbrirCamara =  findViewById(R.id.IdBtnCamara);
        btnGuardarFoto = findViewById(R.id.IdBtnGuardar);
        imageView = findViewById(R.id.IdImageView);

        btnAbrirCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        btnGuardarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarFoto();
            }
        });

    }

    //Detecta cuando se toma una foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Verificamos que venimos de la camara y que todo ha ido bien
        if(requestCode == 1 && resultCode == RESULT_OK){  // -> MainActivity.RESULT_OK //Constante estatica de la clase Activity

            //Recuperamos la foto
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data"); //Hay que saber que la clave del bundle para la foto es "data"

            imageView.setImageBitmap(imageBitmap);

            //Possiblemente guarde el bitmap en el sistema de archivos
            //interesara guardar el bitmap en la variable de instancia de esta activity
            imagenActual = imageBitmap;
        }
    }



    private void abrirCamara(){

        //Usamos un Intent ya definido en el sistema
        Intent hacerFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Comprobamos que la cámara se puede abrir
        if(hacerFotoIntent.resolveActivity(getPackageManager()) != null){

            //Abrir la cámara
            startActivityForResult(hacerFotoIntent, 1);
            //El segundo parametro sirve de referencia para saber de donde volvemos
        }
    }

    private File createImageFile() throws IOException {
        //throws IOException -> Operaciones de entrada/salida son muy delicadas y propensas a errores

        String strName = "name" + ((int)(Math.random()*10000));

        //El constructor de file necessita saber
        // 1) el directorio de nuestra app
        // 2) el nombre del archivo
        File file = new File(this.getFilesDir(),strName);

        return file;
    }

    private void guardarFoto(){

        try {
            File file = createImageFile();
            Log.d("**","file: " + file.getAbsolutePath());

            //Un FileOutputStream es un OutputStream especializado en archivos
            OutputStream out = new FileOutputStream(file);

            //Enviamos la imagen actual a través del stream
            imagenActual.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush(); //ejecutamos
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
