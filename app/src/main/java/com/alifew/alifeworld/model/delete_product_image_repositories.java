package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class delete_product_image_repositories {
    delete_product_image_api delete_product_image_api;
    private String id;
    MutableLiveData<delete_product_image_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static delete_product_image_repositories delete_product_image_repositories;
    public delete_product_image_repositories() {
        //this.id = id;
        delete_product_image_api= ApiUtilize.delete_product_image_response();
        data=new MutableLiveData<>();
    }
    public synchronized static delete_product_image_repositories getInstance() {
        if (delete_product_image_repositories == null) {
            return new delete_product_image_repositories();
        }
        return delete_product_image_repositories;
    }
    public @NonNull
    MutableLiveData<delete_product_image_response> getData(@NonNull String id)
    {
        Call<delete_product_image_response> call=delete_product_image_api.getdata(id);
        call.enqueue(new Callback<delete_product_image_response>() {
            @Override
            public void onResponse(Call<delete_product_image_response> call, Response<delete_product_image_response> response) {
                if (response.isSuccessful()){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<delete_product_image_response> call, Throwable t) {
                delete_product_image_response response=new delete_product_image_response();
                response.setMessage(t.getMessage());
                data.postValue(response);

            }
        });
        return  data;
    }
}
