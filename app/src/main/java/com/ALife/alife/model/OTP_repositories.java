package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTP_repositories {

    String to, text;

    MutableLiveData<OTP_response> status;
    OTP_api otpapi;
    private static OTP_repositories otp_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public OTP_repositories() {
        //this.to = to;
        //this.text = text;
        otpapi = ApiUtilize.otp_response();
        status = new MutableLiveData<>();
    }

    public synchronized static OTP_repositories getInstance() {
        if (otp_repositories == null) {
            return new OTP_repositories();
        }
        return otp_repositories;
    }

    public @NonNull
    MutableLiveData<OTP_response> getStatus(@NonNull String to, @NonNull String text) {
        Call<OTP_response> call = otpapi.otpresponse(to, text);
        call.enqueue(new Callback<OTP_response>() {
            @Override
            public void onResponse(Call<OTP_response> call, Response<OTP_response> response) {
                if (response.isSuccessful()) {
                    status.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OTP_response> call, Throwable t) {
                OTP_response response = new OTP_response();
                response.setStatus(t.getMessage());

                status.postValue(response);
            }
        });
        return status;
    }
}
