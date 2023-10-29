package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_admin_login_repositories {
    shop_admin_login_api shop_admin_login;
    MutableLiveData<shop_admin_login_response> data;
    private String phone, password, shop_id;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    private static shop_admin_login_repositories shop_admin_login_repositories;

    private shop_admin_login_repositories() {
        //this.phone = phone;
        //this.password = password;
        //this.shop_id=shop_id;
        shop_admin_login = ApiUtilize.shop_admin_login_response();
        data = new MutableLiveData<>();
    }

    public synchronized static shop_admin_login_repositories getInstance() {
        if (shop_admin_login_repositories == null) {
            return new shop_admin_login_repositories();
        }
        return shop_admin_login_repositories;
    }

    public @NonNull
    MutableLiveData<shop_admin_login_response> getData(@NonNull String phone, @NonNull String password, @NonNull String shop_id) {
        Call<shop_admin_login_response> call = shop_admin_login.getShopadminLogin(phone, password, shop_id);
        call.enqueue(new Callback<shop_admin_login_response>() {
            @Override
            public void onResponse(Call<shop_admin_login_response> call, Response<shop_admin_login_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<shop_admin_login_response> call, Throwable t) {

            }
        });
        return data;
    }
}
