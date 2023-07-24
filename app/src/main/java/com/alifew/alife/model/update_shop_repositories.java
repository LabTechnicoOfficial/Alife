package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_shop_repositories {
    update_shop_api1 update_shop1;
    update_shop_api2 update_shop2;
    MutableLiveData<update_shop_response> Data;
    private String id, name, owner, location, image;
    private static update_shop_repositories update_shop_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public update_shop_repositories() {
       /* this.id = id;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.image = image;*/
        Data = new MutableLiveData<>();
        update_shop1 = ApiUtilize.update_shop_response1();
        update_shop2 = ApiUtilize.update_shop_response2();
    }

    public synchronized static update_shop_repositories getInstance() {
        if (update_shop_repositories == null) {
            return new update_shop_repositories();
        }
        return update_shop_repositories;
    }

    public @NonNull  MutableLiveData<update_shop_response> getData1(@NonNull  String id,@NonNull String name,@NonNull String owner,@NonNull String location,@NonNull String image) {

        Call<update_shop_response> call = update_shop1.update_shop(id, name, owner, location, image);
        call.enqueue(new Callback<update_shop_response>() {
            @Override
            public void onResponse(Call<update_shop_response> call, Response<update_shop_response> response) {
                if (response.isSuccessful()){
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_shop_response> call, Throwable t) {
                update_shop_response response = new update_shop_response();
                response.setMessage("fail to edit");
                Data.postValue(response);
            }
        });
        return Data;
    }

    public @NonNull  MutableLiveData<update_shop_response> getData2(@NonNull String id,@NonNull String name,@NonNull String owner,@NonNull String location) {

        Call<update_shop_response> call = update_shop2.update_shop(id, name, owner, location);
        call.enqueue(new Callback<update_shop_response>() {
            @Override
            public void onResponse(Call<update_shop_response> call, Response<update_shop_response> response) {
                if (response.isSuccessful()){
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_shop_response> call, Throwable t) {
                update_shop_response response = new update_shop_response();
                response.setMessage(t.getMessage());
                Data.postValue(response);
            }
        });
        return Data;
    }
}
