package com.alifew.bcopay.model;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_details_repositories {

    Customer_details_api customer_details;
    MutableLiveData<Customer_response> data;
    private static Customer_details_repositories customer_details_repositories;
    MutableLiveData<CommonResponse> commonResponse;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public Customer_details_repositories() {
        //this.id = id;
        customer_details = ApiUtilize.get_Customer();
        commonResponse = new MutableLiveData<>();
    }

    public synchronized static Customer_details_repositories getInstance() {
        if (customer_details_repositories == null) {
            return new Customer_details_repositories();
        }
        return customer_details_repositories;
    }

    public @NonNull
    MutableLiveData<Customer_response> getdata(@NonNull String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<Customer_response> call = customer_details.getdata(id);
        call.enqueue(new Callback<Customer_response>() {
            @Override
            public void onResponse(Call<Customer_response> call, Response<Customer_response> response) {
                if (response.isSuccessful()) {
                    Log.d("dataxx", "success: " + response.body().customerName);
                    data.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<Customer_response> call, Throwable t) {

                Log.d("dataxx", "onFailure: " + t.getMessage());
            }


        });
        return data;
    }

    public MutableLiveData<CommonResponse> addReferCode(String userID, String referCode) {
        Call<CommonResponse> call = customer_details.addReferCode(userID, referCode);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    commonResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });

        return commonResponse;
    }
}
