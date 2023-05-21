package com.ALife.alife.model;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_local_business_title_repositories {
    private String title, id;
    private add_local_business_title_api add_local_business_title;
    private MutableLiveData<add_local_business_response> data;
    private static add_local_business_title_repositories add_local_business_title_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public add_local_business_title_repositories() {
        //this.title = title;
        //this.id = id;
        add_local_business_title = ApiUtilize.add_local_business_title();
        data = new MutableLiveData<>();
    }

    public synchronized static add_local_business_title_repositories getInstance() {
        if (add_local_business_title_repositories == null) {
            return new add_local_business_title_repositories();
        }
        return add_local_business_title_repositories;
    }

    public @NonNull MutableLiveData<add_local_business_response> getData(@NonNull String title, @NonNull String id) {
        Call<add_local_business_response> call = add_local_business_title.add(title, id);
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
                Log.d("serverresponse",t.getMessage());
            }
        });
        return data;
    }
}
