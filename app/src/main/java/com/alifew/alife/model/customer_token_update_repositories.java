package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customer_token_update_repositories {
    private String customer_id;
    private String token;
    private customer_token_update_api token_update;
    private MutableLiveData<token_update_response> Data;
    private static customer_token_update_repositories customer_token_update_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public customer_token_update_repositories() {
        // this.customer_id = customer_id;
        // this.token = token;
        token_update = ApiUtilize.token_response();
        Data = new MutableLiveData<>();
    }

    public synchronized static customer_token_update_repositories getInstance() {
        if (customer_token_update_repositories == null) {
            return new customer_token_update_repositories();
        }
        return customer_token_update_repositories;
    }

    public @NonNull
    MutableLiveData<token_update_response> getData(@NonNull String customer_id,@NonNull String token) {
        Call<token_update_response> call = token_update.update_token(customer_id, token);
        call.enqueue(new Callback<token_update_response>() {
            @Override
            public void onResponse(Call<token_update_response> call, Response<token_update_response> response) {
                if (response.isSuccessful()){
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<token_update_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
