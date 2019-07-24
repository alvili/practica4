package com.abcsoft.paisesbanderas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.paisesbanderas.R;
import com.abcsoft.paisesbanderas.model.Country;
import com.abcsoft.paisesbanderas.retrofit.ApiRest;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryDetailsActivity extends AppCompatActivity {

    private TextView name;
    private TextView capital;
    private TextView region;
    private ImageView bandera;

    private ApiRest apiRest;
    private Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        name = (TextView) findViewById(R.id.idCtryName);
        capital = (TextView) findViewById(R.id.idCtryCapital);
        region = (TextView) findViewById(R.id.idCtryRegion);
        bandera = (ImageView) findViewById(R.id.idCtryFlag);

        //Configuración de retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiRest = retrofit.create(ApiRest.class);

        //Recogemos los datos enviados por el intent
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        //Solo si bundle no es null
        if(b != null){
            getCountry(b.getString("countryCode"));
        }
    }


    void getCountry(String countryCode){

        Call<Country> call = apiRest.getContry(countryCode);

        //Esto se ejecuta cuando llega la respuesta
        call.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                if (!response.isSuccessful()){
                    Log.d("**","code: " + response.code());
                    return;
                }

                //recupero los datos
                country = response.body();

                //Relleno los campos
                name.setText(country.getName());
                capital.setText(country.getCapital());
                region.setText(country.getRegion());
                Picasso.get().load("https://www.countryflags.io/" + country.getAlpha2Code() + "/flat/64.png").into(bandera);


            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {

            }
        });

    }

}
