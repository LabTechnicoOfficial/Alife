package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_business_summary_image_repositories {
    private String summary_id,image;
    private add_business_summary_image_api add_summary_image;
    private MutableLiveData<add_business_summary_image_response> Data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_business_summary_image_repositories add_business_summary_image_repositories;
    public add_business_summary_image_repositories() {
        //this.summary_id = summary_id;
        //this.image = image;
        add_summary_image=ApiUtilize.add_summary_image();
        Data=new MutableLiveData<>();
    }
    public synchronized static add_business_summary_image_repositories getInstance() {
        if (add_business_summary_image_repositories == null) {
            return new add_business_summary_image_repositories();
        }
        return add_business_summary_image_repositories;
    }
    public @NonNull MutableLiveData<add_business_summary_image_response> getData(@NonNull String summary_id,@NonNull String image)
    {
        Call<add_business_summary_image_response> call=add_summary_image.add_summary_image(summary_id,image);
        call.enqueue(new Callback<add_business_summary_image_response>() {
            @Override
            public void onResponse(Call<add_business_summary_image_response> call, Response<add_business_summary_image_response> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_business_summary_image_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
