package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class phone_verification_shop_repositories {
    private String phone;
    private phone_verification_shop_api verification;
    private MutableLiveData<phone_verification_response> data;
    private static phone_verification_shop_repositories phone_verification_shop_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public phone_verification_shop_repositories() {
        //this.phone = phone;
        verification = ApiUtilize.verification_shop();
        data = new MutableLiveData<>();
    }

    public synchronized static phone_verification_shop_repositories getInstance() {
        if (phone_verification_shop_repositories == null) {
            return new phone_verification_shop_repositories();
        }
        return phone_verification_shop_repositories;
    }

    public @NonNull
    MutableLiveData<phone_verification_response> getData(@NonNull String phone) {
        Call<phone_verification_response> call = verification.verification(phone);
        call.enqueue(new Callback<phone_verification_response>() {
            @Override
            public void onResponse(Call<phone_verification_response> call, Response<phone_verification_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<phone_verification_response> call, Throwable t) {

            }
        });
        return data;
    }
}
