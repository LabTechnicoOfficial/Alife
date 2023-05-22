package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_customer_password_repositories {
    private String customer_id, password;
    private update_customer_password_api update_password;
    private MutableLiveData<update_password_response> Data;
    private static update_customer_password_repositories update_customer_password_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public update_customer_password_repositories() {
        // this.customer_id = customer_id;
        // this.password = password;
        update_password = ApiUtilize.update_customer_password();
        Data = new MutableLiveData<>();
    }

    public synchronized static update_customer_password_repositories getInstance() {
        if (update_customer_password_repositories == null) {
            return new update_customer_password_repositories();
        }
        return update_customer_password_repositories;
    }

    public @NonNull
    MutableLiveData<update_password_response> getData(@NonNull String customer_id, @NonNull String password) {
        Call<update_password_response> call = update_password.update_password(customer_id, password);
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
