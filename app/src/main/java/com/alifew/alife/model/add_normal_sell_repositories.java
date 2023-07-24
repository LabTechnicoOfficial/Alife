package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_normal_sell_repositories {
    private String sell_id,description;
    private add_normal_sell_api normal_sell;
    private MutableLiveData<add_normal_sell_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_normal_sell_repositories add_normal_sell_repositories;
    public add_normal_sell_repositories() {
        //this.sell_id = sell_id;
        //this.description = description;
        normal_sell= ApiUtilize.add_normal_sell_response();
        data=new MutableLiveData<>();
    }
    public synchronized static add_normal_sell_repositories getInstance() {
        if (add_normal_sell_repositories == null) {
            return new add_normal_sell_repositories();
        }
        return add_normal_sell_repositories;
    }
    public @NonNull
    MutableLiveData<add_normal_sell_response> getData(@NonNull String sell_id,@NonNull String description)
    {
        Call<add_normal_sell_response> call=normal_sell.add_normal_sell(sell_id,description);
        call.enqueue(new Callback<add_normal_sell_response>() {
            @Override
            public void onResponse(Call<add_normal_sell_response> call, Response<add_normal_sell_response> response) {
                if (response.isSuccessful()){
                data.postValue(response.body());}
            }

            @Override
            public void onFailure(Call<add_normal_sell_response> call, Throwable t) {

            }
        });
        return data;
    }
}
