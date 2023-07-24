package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_local_business_details_repositories {
    private String details, amount, price, id;
    private add_local_business_details_api add_local_business_details;
    private MutableLiveData<add_local_business_response> data;
    private static add_local_business_details_repositories add_local_business_details_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public add_local_business_details_repositories() {
        /*this.details = details;
        this.amount = amount;
        this.price = price;
        this.id = id;*/
        add_local_business_details = ApiUtilize.add_local_business_details();
        data = new MutableLiveData<>();
    }

    public synchronized static add_local_business_details_repositories getInstance() {
        if (add_local_business_details_repositories == null) {
            return new add_local_business_details_repositories();
        }
        return add_local_business_details_repositories;
    }

    public @NonNull MutableLiveData<add_local_business_response> getData(@NonNull String details,@NonNull String amount,@NonNull String price,@NonNull String id) {
        Call<add_local_business_response> call = add_local_business_details.add(details, amount, price, id);
        call.enqueue(new Callback<add_local_business_response>() {
            @Override
            public void onResponse(Call<add_local_business_response> call, Response<add_local_business_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_local_business_response> call, Throwable t) {
                add_local_business_response response = new add_local_business_response();
                response.setMessage("network error!!!");
                data.postValue(response);
            }
        });
        return data;
    }
}
