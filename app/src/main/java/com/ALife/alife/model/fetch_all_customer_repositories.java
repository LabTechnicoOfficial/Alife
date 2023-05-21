package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fetch_all_customer_repositories {
    fetch_all_customer_api fetch_all_customer;
    MutableLiveData<List<get_shop_customer_response>> data;
    private static fetch_all_customer_repositories fetch_all_customer_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public fetch_all_customer_repositories() {
        fetch_all_customer=ApiUtilize.fetch_all_customer_response();
        data=new MutableLiveData<>();
    }
    public synchronized static fetch_all_customer_repositories getInstance() {
        if (fetch_all_customer_repositories == null) {
            return new fetch_all_customer_repositories();
        }
        return fetch_all_customer_repositories;
    }
    public @NonNull
    MutableLiveData<List<get_shop_customer_response>> getData()
    {

        Call<List<get_shop_customer_response>> call=fetch_all_customer.fetch_customer("xyz");
        call.enqueue(new Callback<List<get_shop_customer_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_customer_response>> call, Response<List<get_shop_customer_response>> response) {
                if (response.isSuccessful()){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_customer_response>> call, Throwable t) {

            }
        });
        return  data;

    }

}
