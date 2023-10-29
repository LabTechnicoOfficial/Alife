package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_customer_repositories {
    update_customer_api1 update_customer1;
    update_customer_api2 update_customer2;
    MutableLiveData<update_customer_response> Data;
    private String id, name, location, image;
    private static update_customer_repositories update_customer_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public update_customer_repositories() {
       /* this.id = id;
        this.name = name;
        this.location = location;
        this.image = image;*/
        Data = new MutableLiveData<>();
        update_customer1 = ApiUtilize.update_customer_response1();
        update_customer2 = ApiUtilize.update_customer_response2();
    }

    public synchronized static update_customer_repositories getInstance() {
        if (update_customer_repositories == null) {
            return new update_customer_repositories();
        }
        return update_customer_repositories;
    }

    public @NonNull
    MutableLiveData<update_customer_response> getData1(@NonNull String id, @NonNull String name, @NonNull String location, @NonNull String image) {

        Call<update_customer_response> call = update_customer1.update_customer(id, name, location, image);
        call.enqueue(new Callback<update_customer_response>() {
            @Override
            public void onResponse(Call<update_customer_response> call, Response<update_customer_response> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_customer_response> call, Throwable t) {
                update_customer_response response = new update_customer_response();
                response.setMessage("fail to edit");
                Data.postValue(response);
            }
        });
        return Data;
    }

    public @NonNull
    MutableLiveData<update_customer_response> getData2(@NonNull String id, @NonNull String name, @NonNull String location) {

        Call<update_customer_response> call = update_customer2.update_customer(id, name, location);
        call.enqueue(new Callback<update_customer_response>() {
            @Override
            public void onResponse(Call<update_customer_response> call, Response<update_customer_response> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_customer_response> call, Throwable t) {
                update_customer_response response = new update_customer_response();
                response.setMessage(t.getMessage());
                Data.postValue(response);
            }
        });
        return Data;
    }
}
