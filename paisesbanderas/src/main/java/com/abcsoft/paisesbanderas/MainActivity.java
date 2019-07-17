package com.abcsoft.paisesbanderas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.abcsoft.paisesbanderas.adapters.CountriesAdapters;
import com.abcsoft.paisesbanderas.model.Country;
import com.abcsoft.paisesbanderas.retrofit.ApiRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response; //Modeliza la respuesta http
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.idListView);

        //Configuración de retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRest apiRest = retrofit.create(ApiRest.class);

        Call<List<Country>> call = apiRest.getAll();

        //En otros lenguajes, este objeto Call seria una Promise de que habrá datos

        //Esto se ejecuta cuando llega la respuesta
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (!response.isSuccessful()){
                    Log.d("**","code: " + response.code());
                    return;
                }

                //recupero los datos
                List<Country> countries = response.body();

                //Instancio adaptador
                CountriesAdapters countriesAdapter = new CountriesAdapters(MainActivity.this, countries);

                //asigno el adaptador al ListView
                listView.setAdapter(countriesAdapter);

            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });

    }
}