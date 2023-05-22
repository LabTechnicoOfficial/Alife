package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class normal_sell_details_repositories {
    private String sell_id;
    private normal_sell_details_api normal_sell_details;
    private MutableLiveData<normal_sell_details_response> Data;
    private static normal_sell_details_repositories normal_sell_details_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public normal_sell_details_repositories() {
        //  this.sell_id = sell_id;
        normal_sell_details = ApiUtilize.normal_sell_details();
        Data = new MutableLiveData<>();
    }

    public synchronized static normal_sell_details_repositories getInstance() {
        if (normal_sell_details_repositories == null) {
            return new normal_sell_details_repositories();
        }
        return normal_sell_details_repositories;
    }

    public @NonNull
    MutableLiveData<normal_sell_details_response> getData(@NonNull String sell_id) {
        Call<normal_sell_details_response> call = normal_sell_details.get_normal_sell_details(sell_id);
        call.enqueue(new Callback<normal_sell_details_response>() {
            @Override
            public void onResponse(Call<normal_sell_details_response> call, Response<normal_sell_details_response> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<normal_sell_details_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
