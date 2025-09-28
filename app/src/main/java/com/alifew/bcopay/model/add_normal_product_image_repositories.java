package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_normal_product_image_repositories {
    private String id,image;
    private add_normal_product_image_api add_image;
    private MutableLiveData<add_normal_product_image_response> Data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_normal_product_image_repositories add_normal_product_image_repositories;
    public add_normal_product_image_repositories() {
       // this.id = id;
        //this.image = image;
        add_image= ApiUtilize.add_normal_product_image();
        Data=new MutableLiveData<>();
    }
    public synchronized static add_normal_product_image_repositories getInstance() {
        if (add_normal_product_image_repositories == null) {
            return new add_normal_product_image_repositories();
        }
        return add_normal_product_image_repositories;
    }
    public @NonNull
    MutableLiveData<add_normal_product_image_response> getData(@NonNull String id,@NonNull String image)
    {
        Call<add_normal_product_image_response> call=add_image.add_normal_image(id,image);
        call.enqueue(new Callback<add_normal_product_image_response>() {
            @Override
            public void onResponse(Call<add_normal_product_image_response> call, Response<add_normal_product_image_response> response) {
                if (response.isSuccessful()){
                Data.postValue(response.body());}
            }

            @Override
            public void onFailure(Call<add_normal_product_image_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
