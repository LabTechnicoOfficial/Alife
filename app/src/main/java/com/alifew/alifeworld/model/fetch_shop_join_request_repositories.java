package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fetch_shop_join_request_repositories {
    private String customer_id;
    private fetch_shop_join_request_api fetch_shop_join_request;
    MutableLiveData<List<fetch_shop_response>> data;
    private static fetch_shop_join_request_repositories fetch_shop_join_request_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public fetch_shop_join_request_repositories() {
        // this.customer_id = customer_id;
        data = new MutableLiveData<>();
        fetch_shop_join_request = ApiUtilize.fetch_shop_join_request();
    }

    public synchronized static fetch_shop_join_request_repositories getInstance() {
        if (fetch_shop_join_request_repositories == null) {
            return new fetch_shop_join_request_repositories();
        }
        return fetch_shop_join_request_repositories;
    }

    public @NonNull
    MutableLiveData<List<fetch_shop_response>> getData(@NonNull String customer_id) {
        Call<List<fetch_shop_response>> call = fetch_shop_join_request.getShop(customer_id);
        call.enqueue(new Callback<List<fetch_shop_response>>() {
            @Override
            public void onResponse(Call<List<fetch_shop_response>> call, Response<List<fetch_shop_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<fetch_shop_response>> call, Throwable t) {

            }
        });
        return data;
    }
}
