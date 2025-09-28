package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fetch_customer_join_request_repositories {
    fetch_customer_join_request_api fetch_customer_join_request;
    private String shop_id;
    MutableLiveData<List<Get_shop_customer_response>> data;
    private static fetch_customer_join_request_repositories fetch_customer_join_request_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public fetch_customer_join_request_repositories() {
        //this.shop_id = shop_id;
        fetch_customer_join_request = ApiUtilize.fetch_customer_join_request();
        data = new MutableLiveData<>();
    }

    public synchronized static fetch_customer_join_request_repositories getInstance() {
        if (fetch_customer_join_request_repositories == null) {
            return new fetch_customer_join_request_repositories();
        }
        return fetch_customer_join_request_repositories;
    }

    public @NonNull
    MutableLiveData<List<Get_shop_customer_response>> getData(@NonNull String shop_id) {
        Call<List<Get_shop_customer_response>> call = fetch_customer_join_request.getcustomer(shop_id);
        call.enqueue(new Callback<List<Get_shop_customer_response>>() {
            @Override
            public void onResponse(Call<List<Get_shop_customer_response>> call, Response<List<Get_shop_customer_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Get_shop_customer_response>> call, Throwable t) {

            }
        });
        return data;
    }
}
