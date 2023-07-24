package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_details_repositories {
    String id;
    Customer_details_api customer_details;
    MutableLiveData<Customer_response> data;
    private static Customer_details_repositories customer_details_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public Customer_details_repositories() {
        //this.id = id;
        customer_details = ApiUtilize.get_Customer();
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
                    Customer_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<Customer_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
            }


        });
        return data;
    }
}
