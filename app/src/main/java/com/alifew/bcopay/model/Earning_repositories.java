package com.alifew.bcopay.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Earning_repositories {
    private static Earning_repositories earning_repositories;
    Earning_API earningApi;
    MutableLiveData<Earning_response> message;

    private Earning_repositories() {
        earningApi = ApiUtilize.earningApi();
    }

    public synchronized static Earning_repositories getInstance() {
        if (earning_repositories == null) {
            return new Earning_repositories();
        }
        return earning_repositories;
    }

    public @NonNull
    MutableLiveData<Earning_response> getData( @NonNull String userID, @NonNull String type) {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<Earning_response> call = earningApi.getEarningID(userID, type);

        call.enqueue(new Callback<Earning_response>() {
            @Override
            public void onResponse(Call<Earning_response> call, Response<Earning_response> response) {

                if (response.isSuccessful()) {
                    Earning_response earning_response = response.body();
                    message.postValue(earning_response);
                }

            }

            @Override
            public void onFailure(Call<Earning_response> call, Throwable t) {
                Earning_response response = new Earning_response();
                message.postValue(response);
            }
        });
        return message;
    }

}
