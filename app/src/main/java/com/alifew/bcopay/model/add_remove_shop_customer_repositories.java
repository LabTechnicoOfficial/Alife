package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_remove_shop_customer_repositories {
    add_shop_customer_api add_shop_customer;
    remove_shop_customer_api remove_shop_customer;
    add_shop_customer_manually_api add_shop_customer_manually;
    String shop_id, customer_id;
    MutableLiveData<add_remove_shop_customer_response> data;
    private static add_remove_shop_customer_repositories add_remove_shop_customer_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public add_remove_shop_customer_repositories() {
        /*this.shop_id = shop_id;
        this.customer_id = customer_id;*/
        add_shop_customer = ApiUtilize.add_shop_customer_response();
        remove_shop_customer = ApiUtilize.remove_shop_customer_response();
        add_shop_customer_manually = ApiUtilize.add_shop_customer_manually_response();
        data = new MutableLiveData<>();
    }

    public synchronized static add_remove_shop_customer_repositories getInstance() {
        if (add_remove_shop_customer_repositories == null) {
            return new add_remove_shop_customer_repositories();
        }
        return add_remove_shop_customer_repositories;
    }

    public @NonNull
    MutableLiveData<add_remove_shop_customer_response> getDataAdd(@NonNull String shop_id, @NonNull String customer_id) {
        Call<add_remove_shop_customer_response> call = add_shop_customer.add_customer(shop_id, customer_id);
        call.enqueue(new Callback<add_remove_shop_customer_response>() {
            @Override
            public void onResponse(Call<add_remove_shop_customer_response> call, Response<add_remove_shop_customer_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_remove_shop_customer_response> call, Throwable t) {

            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<add_remove_shop_customer_response> getData_Manually(@NonNull String shop_id, @NonNull String customer_id) {
        Call<add_remove_shop_customer_response> call = add_shop_customer_manually.add_customer(shop_id, customer_id);
        call.enqueue(new Callback<add_remove_shop_customer_response>() {
            @Override
            public void onResponse(Call<add_remove_shop_customer_response> call, Response<add_remove_shop_customer_response> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_remove_shop_customer_response> call, Throwable t) {

            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<add_remove_shop_customer_response> getDataRemove(@NonNull String shop_id, @NonNull String customer_id) {
        Call<add_remove_shop_customer_response> call = remove_shop_customer.remove_customer(shop_id, customer_id);
        call.enqueue(new Callback<add_remove_shop_customer_response>() {
            @Override
            public void onResponse(Call<add_remove_shop_customer_response> call, Response<add_remove_shop_customer_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_remove_shop_customer_response> call, Throwable t) {

            }
        });
        return data;
    }
}
