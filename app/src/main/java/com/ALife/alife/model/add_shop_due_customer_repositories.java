package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_shop_due_customer_repositories {
    private String shop_id,customer_id;
    private MutableLiveData<add_shop_due_customer_response> Data;
    private add_shop_due_customer_api add_shop_due_cutomer;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    private static add_shop_due_customer_repositories add_shop_due_customer_repositories;
    public add_shop_due_customer_repositories() {
        /*this.shop_id = shop_id;
        this.customer_id = customer_id;*/
        add_shop_due_cutomer= ApiUtilize.add_shop_due_customer();
        Data=new MutableLiveData<>();
    }
    public synchronized static add_shop_due_customer_repositories getInstance() {
        if (add_shop_due_customer_repositories == null) {
            return new add_shop_due_customer_repositories();
        }
        return add_shop_due_customer_repositories;
    }
    public @NonNull
    MutableLiveData<add_shop_due_customer_response> getData(@NonNull String shop_id,@NonNull String customer_id,@NonNull String phone)
    {
        Call<add_shop_due_customer_response> call=add_shop_due_cutomer.add_shop_due_customer(shop_id,customer_id,phone);
        call.enqueue(new Callback<add_shop_due_customer_response>() {
            @Override
            public void onResponse(Call<add_shop_due_customer_response> call, Response<add_shop_due_customer_response> response) {
                if (response.isSuccessful()){
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_shop_due_customer_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
