package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fetch_shop_repositories {
    fetch_shop_api fetch_shop;
    MutableLiveData<List<fetch_shop_response>> data;
    private static fetch_shop_repositories fetch_shop_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public fetch_shop_repositories() {
        fetch_shop=ApiUtilize.fetch_shop_response();
        data=new MutableLiveData<>();
    }
    public synchronized static fetch_shop_repositories getInstance() {
        if (fetch_shop_repositories == null) {
            return new fetch_shop_repositories();
        }
        return fetch_shop_repositories;
    }
    public @NonNull
    MutableLiveData<List<fetch_shop_response>> getData()
    {

        Call<List<fetch_shop_response>> call=fetch_shop.fetch_shop("xxx");
        call.enqueue(new Callback<List<fetch_shop_response>>() {
            @Override
            public void onResponse(Call<List<fetch_shop_response>> call, Response<List<fetch_shop_response>> response) {
                if (response.isSuccessful()){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<fetch_shop_response>> call, Throwable t) {

            }
        });
        return  data;
    }
}
