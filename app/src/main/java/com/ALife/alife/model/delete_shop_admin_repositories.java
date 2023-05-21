package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class delete_shop_admin_repositories {
    private String agent_id;
    delete_shop_admin_api delete_shop_admin;
    MutableLiveData<delete_shop_admin_response> data;
    private static delete_shop_admin_repositories delete_shop_admin_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public delete_shop_admin_repositories() {
        //this.agent_id = agent_id;
        delete_shop_admin = ApiUtilize.delete_shop_admin_response();
        data = new MutableLiveData<>();
    }

    public synchronized static delete_shop_admin_repositories getInstance() {
        if (delete_shop_admin_repositories == null) {
            return new delete_shop_admin_repositories();
        }
        return delete_shop_admin_repositories;
    }

    public @NonNull
    MutableLiveData<delete_shop_admin_response> getData(@NonNull String agent_id) {
        Call<delete_shop_admin_response> call = delete_shop_admin.getdata(agent_id);
        call.enqueue(new Callback<delete_shop_admin_response>() {
            @Override
            public void onResponse(Call<delete_shop_admin_response> call, Response<delete_shop_admin_response> response) {
                if (response.isSuccessful()){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<delete_shop_admin_response> call, Throwable t) {

            }
        });
        return data;
    }
}
