package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fetch_shop_adminList_repositories {
    fetch_shop_adminList_api fetch_shop_admin;
    MutableLiveData<List<fetch_shop_admin_response>> data;
    String shop_id;
    private static fetch_shop_adminList_repositories fetch_shop_adminList_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public fetch_shop_adminList_repositories() {
        // this.shop_id = shop_id;
        data = new MutableLiveData<>();
        fetch_shop_admin = ApiUtilize.fetch_shop_admin_response();
    }

    public synchronized static fetch_shop_adminList_repositories getInstance() {
        if (fetch_shop_adminList_repositories == null) {
            return new fetch_shop_adminList_repositories();
        }
        return fetch_shop_adminList_repositories;
    }

    public @NonNull
    MutableLiveData<List<fetch_shop_admin_response>> getData(@NonNull String shop_id) {
        Call<List<fetch_shop_admin_response>> call = fetch_shop_admin.fetch_shop_admin(shop_id);
        call.enqueue(new Callback<List<fetch_shop_admin_response>>() {
            @Override
            public void onResponse(Call<List<fetch_shop_admin_response>> call, Response<List<fetch_shop_admin_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<fetch_shop_admin_response>> call, Throwable t) {

            }
        });
        return data;
    }
}
