package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_product_type_repositories {
    String id;
    get_product_type_api get_product_type;
    MutableLiveData<List<get_product_type_response>> data;
    private static get_product_type_repositories get_product_type_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_product_type_repositories() {
        //this.id = id;
        get_product_type = ApiUtilize.get_product_type_response();
        data = new MutableLiveData<>();
    }

    public synchronized static get_product_type_repositories getInstance() {
        if (get_product_type_repositories == null) {
            return new get_product_type_repositories();
        }
        return get_product_type_repositories;
    }

    public @NonNull
    MutableLiveData<List<get_product_type_response>> getdata(@NonNull String id) {

        Call<List<get_product_type_response>> call = get_product_type.getproduct_type(id);
        call.enqueue(new Callback<List<get_product_type_response>>() {
            @Override
            public void onResponse(Call<List<get_product_type_response>> call, Response<List<get_product_type_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_product_type_response>> call, Throwable t) {
            }
        });
        return data;
    }
}
