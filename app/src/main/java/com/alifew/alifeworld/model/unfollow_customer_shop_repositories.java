package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class unfollow_customer_shop_repositories {
    unfollow_customer_shop_api unfollow_customer_shop;
    MutableLiveData<unfollow_customer_shop_response> data;
    String shop_id, customer_id;
    private static unfollow_customer_shop_repositories unfollow_customer_shop_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public unfollow_customer_shop_repositories() {
        //this.shop_id = shop_id;
        //this.customer_id = customer_id;
        unfollow_customer_shop = ApiUtilize.unfollow_customer_shop_response();
        data = new MutableLiveData<>();
    }

    public synchronized static unfollow_customer_shop_repositories getInstance() {
        if (unfollow_customer_shop_repositories == null) {
            return new unfollow_customer_shop_repositories();
        }
        return unfollow_customer_shop_repositories;
    }

    public @NonNull
    MutableLiveData<unfollow_customer_shop_response> getData(@NonNull String shop_id, @NonNull String customer_id) {
        Call<unfollow_customer_shop_response> call = unfollow_customer_shop.unfollow_shop(shop_id, customer_id);
        call.enqueue(new Callback<unfollow_customer_shop_response>() {
            @Override
            public void onResponse(Call<unfollow_customer_shop_response> call, Response<unfollow_customer_shop_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<unfollow_customer_shop_response> call, Throwable t) {

            }
        });
        return data;
    }
}
