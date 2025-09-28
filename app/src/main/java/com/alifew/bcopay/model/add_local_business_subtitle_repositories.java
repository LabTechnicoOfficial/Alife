package com.alifew.bcopay.model;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_local_business_subtitle_repositories {
    private String subtitle, id;
    private add_local_business_subtitle_api add_local_business_subtitle;
    private MutableLiveData<add_local_business_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_local_business_subtitle_repositories add_local_business_subtitle_repositories;
    public add_local_business_subtitle_repositories() {
        //this.subtitle = subtitle;
        //this.id = id;
        add_local_business_subtitle = ApiUtilize.add_local_business_subtitle();
        data = new MutableLiveData<>();
    }
    public synchronized static add_local_business_subtitle_repositories getInstance() {
        if (add_local_business_subtitle_repositories == null) {
            return new add_local_business_subtitle_repositories();
        }
        return add_local_business_subtitle_repositories;
    }
    public @NonNull MutableLiveData<add_local_business_response> getData(@NonNull String subtitle,@NonNull String id)
    {
        Call<add_local_business_response> call=add_local_business_subtitle.add(subtitle,id);
        call.enqueue(new Callback<add_local_business_response>() {

            @Override
            public void onResponse(Call<add_local_business_response> call, Response<add_local_business_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                    Log.d("mesba",id);
                }
            }

            @Override
            public void onFailure(Call<add_local_business_response> call, Throwable t) {
                add_local_business_response response=new add_local_business_response();
                response.setMessage("Something Error");
                data.postValue(response);
                Log.d("mesba",t.getMessage());
            }
        });
        return data;
    }

}
