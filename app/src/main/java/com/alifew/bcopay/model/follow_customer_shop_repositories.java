package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class follow_customer_shop_repositories {
    String shop_id, customer_id;
    follow_customer_shop_api follow_customer_shop_api;
    MutableLiveData<follow_customer_shop_response> data;
    private static follow_customer_shop_repositories follow_customer_shop_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public follow_customer_shop_repositories() {
        // this.shop_id = shop_id;
        //this.customer_id = customer_id;
        follow_customer_shop_api = ApiUtilize.follow_customer_shop_response();
        data = new MutableLiveData<>();
    }

    public synchronized static follow_customer_shop_repositories getInstance() {
        if (follow_customer_shop_repositories == null) {
            return new follow_customer_shop_repositories();
        }
        return follow_customer_shop_repositories;
    }

    public @NonNull
    MutableLiveData<follow_customer_shop_response> getData(@NonNull String shop_id, @NonNull String customer_id) {
        Call<follow_customer_shop_response> call = follow_customer_shop_api.follow_shop(shop_id, customer_id);
        call.enqueue(new Callback<follow_customer_shop_response>() {
            @Override
            public void onResponse(Call<follow_customer_shop_response> call, Response<follow_customer_shop_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<follow_customer_shop_response> call, Throwable t) {

            }
        });
        return data;
    }
}
