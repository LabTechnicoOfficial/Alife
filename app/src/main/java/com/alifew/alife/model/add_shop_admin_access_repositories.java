package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_shop_admin_access_repositories {
    add_shop_admin_access_api add_shop_admin_access;
    String agent_id, category_id;
    MutableLiveData<add_shop_admin_access_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_shop_admin_access_repositories add_shop_admin_access_repositories;
    public add_shop_admin_access_repositories() {
        /*this.agent_id = agent_id;
        this.category_id = category_id;*/
        add_shop_admin_access = ApiUtilize.add_shop_admin_access_response();
        data = new MutableLiveData<>();
    }
    public synchronized static add_shop_admin_access_repositories getInstance() {
        if (add_shop_admin_access_repositories == null) {
            return new add_shop_admin_access_repositories();
        }
        return add_shop_admin_access_repositories;
    }
    public @NonNull
    MutableLiveData<add_shop_admin_access_response> getData(@NonNull String agent_id,@NonNull String category_id) {
        Call<add_shop_admin_access_response> call = add_shop_admin_access.add_admin_access(agent_id, category_id);
        call.enqueue(new Callback<add_shop_admin_access_response>() {
            @Override
            public void onResponse(Call<add_shop_admin_access_response> call, Response<add_shop_admin_access_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_shop_admin_access_response> call, Throwable t) {

            }
        });
        return data;
    }
}
