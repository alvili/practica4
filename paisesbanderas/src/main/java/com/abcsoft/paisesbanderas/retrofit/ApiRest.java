package com.abcsoft.paisesbanderas.retrofit;

import com.abcsoft.paisesbanderas.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRest {

    @GET("all")
    public Call<List<Country>> getAll();

}
