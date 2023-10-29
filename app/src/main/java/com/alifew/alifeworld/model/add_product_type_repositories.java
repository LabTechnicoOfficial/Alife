package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_product_type_repositories {
    String type, count, id;
    add_product_type_api add_product_type;
    MutableLiveData<add_product_type_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    private static add_product_type_repositories add_product_type_repositories;

    public add_product_type_repositories() {
        /*this.type = type;
        this.count = count;
        this.id = id;*/
        data = new MutableLiveData<>();
        add_product_type = ApiUtilize.add_product_type_response();
    }

    public synchronized static add_product_type_repositories getInstance() {
        if (add_product_type_repositories == null) {
            return new add_product_type_repositories();
        }
        return add_product_type_repositories;
    }

    public @NonNull
    MutableLiveData<add_product_type_response> getdata(@NonNull String type, @NonNull String count, @NonNull String id) {
        Call<add_product_type_response> call = add_product_type.add_product_type(type, count, id);
        call.enqueue(new Callback<add_product_type_response>() {
            @Override
            public void onResponse(Call<add_product_type_response> call, Response<add_product_type_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_product_type_response> call, Throwable t) {
                add_product_type_response response = new add_product_type_response();
                response.setMessage(t.getMessage());
                data.postValue(response);
            }
        });

        return data;
    }
}
