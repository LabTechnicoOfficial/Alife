package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class phone_verification_customer_repositories {
    private String phone;
    private phone_verification_customer_api verification;
    private MutableLiveData<phone_verification_response> data;
    private static phone_verification_customer_repositories phone_verification_customer_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public phone_verification_customer_repositories() {
        // this.phone = phone;
        verification = ApiUtilize.verification_customer();
        data = new MutableLiveData<>();
    }

    public synchronized static phone_verification_customer_repositories getInstance() {
        if (phone_verification_customer_repositories == null) {
            return new phone_verification_customer_repositories();
        }
        return phone_verification_customer_repositories;
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
