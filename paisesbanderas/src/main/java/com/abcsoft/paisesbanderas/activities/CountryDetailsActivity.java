package com.abcsoft.paisesbanderas.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.paisesbanderas.R;
import com.abcsoft.paisesbanderas.model.Country;
import com.abcsoft.paisesbanderas.model.Language;
import com.abcsoft.paisesbanderas.retrofit.ApiRest;
import com.squareup.picasso.Picasso;

import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryDetailsActivity extends AppCompatActivity {

    private TextView name;
    private TextView capital;
    private TextView region;
    private TextView area;
    private TextView languages;
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
        area = (TextView) findViewById(R.id.idCtryArea);
        languages = (TextView) findViewById(R.id.idCtryLanguages);
        bandera = (ImageView) findViewById(R.id.idCtryFlag);

        //Configuración de retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiRest = retrofit.create(ApiRest.class);

        //Recupero los datos enviados por el intent
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
            @RequiresApi(api = Build.VERSION_CODES.N)
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
                region.setText(country.getRegion() + ", " + country.getSubregion());
                area.setText(String.format("%,.2f", country.getArea()) + " Km\u00B2");
                languages.setText(country.getLanguages().stream().map(Language::getName).collect(Collectors.joining(", ")));
                Picasso.get().load("https://www.countryflags.io/" + country.getAlpha2Code() + "/flat/64.png").into(bandera);

            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {

            }
        });

    }

}
