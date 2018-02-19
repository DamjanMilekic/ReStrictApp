package com.example.laptop.restrict.RetrofitAppSettings;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by durma on 14.2.18..
 */

public class ApiClientAppSettings {

    private static final String BASE_URL = "https://s.strictapp.com/api/";

    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){

        if (retrofit ==null){

            retrofit = new Retrofit.Builder().
                  baseUrl(BASE_URL).
                  addConverterFactory(GsonConverterFactory.create()).
                  build();

        }

        return retrofit;

    }

}
