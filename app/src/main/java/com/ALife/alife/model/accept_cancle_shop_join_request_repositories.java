package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class accept_cancle_shop_join_request_repositories {
    accept_shop_join_request_api accept_shop_join_request;
    cancle_shop_join_request_api Cancel_shop_join_request;
    private String shop_id,customer_id;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    MutableLiveData<accept_cancle_shop_join_request_response> data;
private static accept_cancle_shop_join_request_repositories accept_cancle_shop_join_request_repositories;
    public accept_cancle_shop_join_request_repositories() {
        /*this.shop_id = shop_id;
        this.customer_id = customer_id;*/
        data=new MutableLiveData<>();
        accept_shop_join_request= ApiUtilize.accept_shop_join_request_response();
        Cancel_shop_join_request=ApiUtilize.Cancel_shop_join_request_response();
    }
    public synchronized static accept_cancle_shop_join_request_repositories getInstance() {
        if (accept_cancle_shop_join_request_repositories == null) {
            return new accept_cancle_shop_join_request_repositories();
        }
        return accept_cancle_shop_join_request_repositories;
    }
    public @NonNull MutableLiveData<accept_cancle_shop_join_request_response> getData1(@NonNull String customer_id,@NonNull String shop_id)
    {
        Call<accept_cancle_shop_join_request_response> call=accept_shop_join_request.accept_shop(customer_id,shop_id);
        call.enqueue(new Callback<accept_cancle_shop_join_request_response>() {
            @Override
            public void onResponse(Call<accept_cancle_shop_join_request_response> call, Response<accept_cancle_shop_join_request_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<accept_cancle_shop_join_request_response> call, Throwable t) {

            }
        });
        return data;
    }
    public @NonNull MutableLiveData<accept_cancle_shop_join_request_response> getData2(@NonNull String customer_id,@NonNull String shop_id)
    {
        Call<accept_cancle_shop_join_request_response> call=Cancel_shop_join_request.Cancel_shop(customer_id,shop_id);
        call.enqueue(new Callback<accept_cancle_shop_join_request_response>() {
            @Override
            public void onResponse(Call<accept_cancle_shop_join_request_response> call, Response<accept_cancle_shop_join_request_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<accept_cancle_shop_join_request_response> call, Throwable t) {

            }
        });
        return data;
    }
}
