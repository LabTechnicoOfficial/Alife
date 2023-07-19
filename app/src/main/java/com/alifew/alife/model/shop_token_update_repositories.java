package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_token_update_repositories {
    private String shop_id, token;
    private shop_token_update_api token_update;
    private MutableLiveData<token_update_response> Data;
    private static shop_token_update_repositories shop_token_update_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public shop_token_update_repositories() {
        //  this.shop_id = shop_id;
        //this.token = token;
        token_update = ApiUtilize.token_update_response();
        Data = new MutableLiveData<>();
    }

    public synchronized static shop_token_update_repositories getInstance() {
        if (shop_token_update_repositories == null) {
            return new shop_token_update_repositories();
        }
        return shop_token_update_repositories;
    }

    public @NonNull
    MutableLiveData<token_update_response> getData(@NonNull String shop_id, @NonNull String token) {
        Call<token_update_response> call = token_update.update_token(shop_id, token);
        call.enqueue(new Callback<token_update_response>() {
            @Override
            public void onResponse(Call<token_update_response> call, Response<token_update_response> response) {
                if (response.isSuccessful()) {
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
