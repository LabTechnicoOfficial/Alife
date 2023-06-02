package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_shop_admin_repositories {
    add_shop_admin_api add_shop_admin;
    String name, phone, password, image, shop_id, access;
    MutableLiveData<add_shop_admin_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_shop_admin_repositories add_shop_admin_repositories;
    public add_shop_admin_repositories() {
        /*this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.shop_id = shop_id;
        this.access = access;*/
        data = new MutableLiveData<>();
        add_shop_admin = ApiUtilize.add_shop_admin_response();
    }
    public synchronized static add_shop_admin_repositories getInstance() {
        if (add_shop_admin_repositories == null) {
            return new add_shop_admin_repositories();
        }
        return add_shop_admin_repositories;
    }

    public @NonNull
    MutableLiveData<add_shop_admin_response> getData( @NonNull String name,@NonNull String phone,@NonNull String password,@NonNull String image,@NonNull String shop_id,@NonNull String access) {
        Call<add_shop_admin_response> call = add_shop_admin.add_shop_admin(name, phone, password, image, shop_id, access);
        call.enqueue(new Callback<add_shop_admin_response>() {
            @Override
            public void onResponse(Call<add_shop_admin_response> call, Response<add_shop_admin_response> response) {
                if (response.isSuccessful()){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_shop_admin_response> call, Throwable t) {

            }
        });
        return data;
    }
}
