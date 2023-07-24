package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customer_profile_repositories {
    String id;
    customer_profile_api customer_profile;
    MutableLiveData<customer_profile_response> data;
    public static customer_profile_repositories customer_profile_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public customer_profile_repositories() {
        //this.id = id;
        customer_profile = ApiUtilize.customer_profile_response();
        data = new MutableLiveData<>();
    }

    public synchronized static customer_profile_repositories getInstance() {
        if (customer_profile_repositories == null) {
            return new customer_profile_repositories();
        }
        return customer_profile_repositories;
    }

    public @NonNull
    MutableLiveData<customer_profile_response> getdata(@NonNull String id) {

        Call<customer_profile_response> call = customer_profile.getdata(id);
        call.enqueue(new Callback<customer_profile_response>() {
            @Override
            public void onResponse(Call<customer_profile_response> call, Response<customer_profile_response> response) {
                if (response.isSuccessful()) {
                    customer_profile_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<customer_profile_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
            }


        });
        return data;
    }
}
