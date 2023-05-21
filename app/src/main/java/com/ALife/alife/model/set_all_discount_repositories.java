package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class set_all_discount_repositories {
    private String shop_id, discount;
    private MutableLiveData<set_all_discount_response> data;
    private set_all_discount_api set_all_discount;
    private static set_all_discount_repositories set_all_discount_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public set_all_discount_repositories() {
        //this.shop_id = shop_id;
        //this.discount = discount;
        data = new MutableLiveData<>();
        set_all_discount = ApiUtilize.set_all_discount_response();
    }

    public synchronized static set_all_discount_repositories getInstance() {
        if (set_all_discount_repositories == null) {
            return new set_all_discount_repositories();
        }
        return set_all_discount_repositories;
    }

    public @NonNull
    MutableLiveData<set_all_discount_response> getData(@NonNull String shop_id, @NonNull String discount) {
        Call<set_all_discount_response> call = set_all_discount.set_all_discount(shop_id, discount);
        call.enqueue(new Callback<set_all_discount_response>() {
            @Override
            public void onResponse(Call<set_all_discount_response> call, Response<set_all_discount_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<set_all_discount_response> call, Throwable t) {

            }
        });
        return data;
    }
}
