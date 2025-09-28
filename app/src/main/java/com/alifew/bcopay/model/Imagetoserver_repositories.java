package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Imagetoserver_repositories {
    Imagetoserver_api imagetoserver_api;
    private String id, image;
    MutableLiveData<Imagetoserver_response> data;
    private static Imagetoserver_repositories imagetoserver_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public Imagetoserver_repositories() {

        imagetoserver_api = ApiUtilize.imagetoserver_response();
        data = new MutableLiveData<>();
    }

    public synchronized static Imagetoserver_repositories getInstance() {
        if (imagetoserver_repositories == null) {
            return new Imagetoserver_repositories();
        }
        return imagetoserver_repositories;
    }

    public @NonNull
    MutableLiveData<Imagetoserver_response> getData(@NonNull String id, @NonNull String image) {
        Call<Imagetoserver_response> call = imagetoserver_api.imagetoserver(image, id);
        call.enqueue(new Callback<Imagetoserver_response>() {
            @Override
            public void onResponse(Call<Imagetoserver_response> call, Response<Imagetoserver_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Imagetoserver_response> call, Throwable t) {
                Imagetoserver_response response = new Imagetoserver_response();
                response.setMessage(t.getMessage());
                data.postValue(response);

            }
        });

        return data;
    }
}
