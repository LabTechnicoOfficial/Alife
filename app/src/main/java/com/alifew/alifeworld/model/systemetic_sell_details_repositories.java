package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class systemetic_sell_details_repositories {
    private String sell_id;
    private systemetic_sell_details_api sell_details;
    private MutableLiveData<List<systemetic_sell_details_response>> Data;
    private static systemetic_sell_details_repositories systemetic_sell_details_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public systemetic_sell_details_repositories() {
        //this.sell_id = sell_id;
        sell_details = ApiUtilize.systemetic_sell_details();
        Data = new MutableLiveData<>();
    }

    public synchronized static systemetic_sell_details_repositories getInstance() {
        if (systemetic_sell_details_repositories == null) {
            return new systemetic_sell_details_repositories();
        }
        return systemetic_sell_details_repositories;
    }

    public @NonNull
    MutableLiveData<List<systemetic_sell_details_response>> getData(@NonNull String sell_id) {
        Call<List<systemetic_sell_details_response>> call = sell_details.get_sell_productlist(sell_id);
        call.enqueue(new Callback<List<systemetic_sell_details_response>>() {
            @Override
            public void onResponse(Call<List<systemetic_sell_details_response>> call, Response<List<systemetic_sell_details_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<systemetic_sell_details_response>> call, Throwable t) {

            }
        });
        return Data;
    }
}
