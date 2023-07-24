package com.alifew.alife.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_client {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {

        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        /*Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http:*******")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());*/
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
