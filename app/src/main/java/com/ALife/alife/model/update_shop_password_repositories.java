package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_shop_password_repositories {
    private String shop_id, password;
    private update_shop_password_api update_password;
    private MutableLiveData<update_password_response> Data;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    private static update_shop_password_repositories update_shop_password_repositories;

    public update_shop_password_repositories() {
        //this.shop_id = shop_id;
        //this.password = password;
        update_password = ApiUtilize.update_shop_password();
        Data = new MutableLiveData<>();
    }

    public synchronized static update_shop_password_repositories getInstance() {
        if (update_shop_password_repositories == null) {
            return new update_shop_password_repositories();
        }
        return update_shop_password_repositories;
    }

    public @NonNull MutableLiveData<update_password_response> getData(@NonNull  String shop_id,@NonNull String password) {
        Call<update_password_response> call = update_password.update_password(shop_id, password);
        call.enqueue(new Callback<update_password_response>() {
            @Override
            public void onResponse(Call<update_password_response> call, Response<update_password_response> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_password_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
