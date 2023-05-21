package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_all_product_discount_repositories {
    private String shop_id;
    get_all_product_discount_api get_all_product_discount;
    MutableLiveData<get_all_product_discount_response> data;
    private static get_all_product_discount_repositories get_all_product_discount_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_all_product_discount_repositories() {
        // this.shop_id = shop_id;
        data = new MutableLiveData<>();
        get_all_product_discount = ApiUtilize.get_all_product_discount_response();
    }

    public synchronized static get_all_product_discount_repositories getInstance() {
        if (get_all_product_discount_repositories == null) {
            return new get_all_product_discount_repositories();
        }
        return get_all_product_discount_repositories;
    }

    public @NonNull
    MutableLiveData<get_all_product_discount_response> getData(@NonNull String shop_id) {
        Call<get_all_product_discount_response> call = get_all_product_discount.getallproduct(shop_id);
        call.enqueue(new Callback<get_all_product_discount_response>() {
            @Override
            public void onResponse(Call<get_all_product_discount_response> call, Response<get_all_product_discount_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_all_product_discount_response> call, Throwable t) {

            }
        });
        return data;
    }
}