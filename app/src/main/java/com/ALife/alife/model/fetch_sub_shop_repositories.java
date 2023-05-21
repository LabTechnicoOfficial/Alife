package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fetch_sub_shop_repositories {
    fetch_sub_shop_api fetch_sub_shop;
    private String shop_id;
    MutableLiveData<List<fetch_sub_shop_response>> data;
    private static fetch_sub_shop_repositories fetch_sub_shop_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public fetch_sub_shop_repositories() {
        //this.shop_id = shop_id;
        fetch_sub_shop = ApiUtilize.fetch_sub_shop_response();
        data = new MutableLiveData<>();
    }

    public synchronized static fetch_sub_shop_repositories getInstance() {
        if (fetch_sub_shop_repositories == null) {
            return new fetch_sub_shop_repositories();
        }
        return fetch_sub_shop_repositories;
    }

    public @NonNull
    MutableLiveData<List<fetch_sub_shop_response>> getData(@NonNull String shop_id) {
        Call<List<fetch_sub_shop_response>> call = fetch_sub_shop.fetch_sub_shop(shop_id);
        call.enqueue(new Callback<List<fetch_sub_shop_response>>() {
            @Override
            public void onResponse(Call<List<fetch_sub_shop_response>> call, Response<List<fetch_sub_shop_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<fetch_sub_shop_response>> call, Throwable t) {

            }
        });
        return data;

    }
}
