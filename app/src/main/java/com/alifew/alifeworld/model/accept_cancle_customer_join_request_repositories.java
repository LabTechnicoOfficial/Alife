package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class accept_cancle_customer_join_request_repositories {
    accept_customer_join_request_api accept_customer_join_request;
    cancle_customer_join_request_api Cancel_customer_join_request;
    MutableLiveData<accept_cancle_customer_join_request_response> data;
    private String shop_id, customer_id;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static accept_cancle_customer_join_request_repositories accept_cancle_customer_join_request_repositories;
    private accept_cancle_customer_join_request_repositories() {
        /*this.shop_id = shop_id;
        this.customer_id = customer_id;*/
        data = new MutableLiveData<>();
        accept_customer_join_request = ApiUtilize.accept_customer_join_request_response();
        Cancel_customer_join_request = ApiUtilize.Cancel_customer_join_request_response();
    }
    public synchronized static accept_cancle_customer_join_request_repositories getInstance() {
        if (accept_cancle_customer_join_request_repositories == null) {
            return new accept_cancle_customer_join_request_repositories();
        }
        return accept_cancle_customer_join_request_repositories;
    }
    public @NonNull
    MutableLiveData<accept_cancle_customer_join_request_response> getData1(@NonNull String shop_id,@NonNull String customer_id) {
        Call<accept_cancle_customer_join_request_response> call = accept_customer_join_request.accept_customer(shop_id, customer_id);
        call.enqueue(new Callback<accept_cancle_customer_join_request_response>() {
            @Override
            public void onResponse(Call<accept_cancle_customer_join_request_response> call, Response<accept_cancle_customer_join_request_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<accept_cancle_customer_join_request_response> call, Throwable t) {

            }
        });
        return data;
    }

    public @NonNull MutableLiveData<accept_cancle_customer_join_request_response> getData2(@NonNull String shop_id,@NonNull String customer_id) {
        Call<accept_cancle_customer_join_request_response> call = Cancel_customer_join_request.Cancel_customer(shop_id, customer_id);
        call.enqueue(new Callback<accept_cancle_customer_join_request_response>() {
            @Override
            public void onResponse(Call<accept_cancle_customer_join_request_response> call, Response<accept_cancle_customer_join_request_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<accept_cancle_customer_join_request_response> call, Throwable t) {

            }
        });
        return data;
    }
}
