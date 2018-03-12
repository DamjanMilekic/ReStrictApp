package com.example.laptop.restrict.RetrofitAppSettings;

import com.example.laptop.restrict.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by durma on 14.2.18..
 */

public class Client {

    private static final String BASE_URL = "http://s.strictapp.com/";

   public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging);
        }
        if (retrofit ==null){

            retrofit = new Retrofit.Builder().
                  baseUrl(BASE_URL).
                  addConverterFactory(GsonConverterFactory.create())/*.client(builder.build())*/.build();

        }

        return retrofit;

    }


}
