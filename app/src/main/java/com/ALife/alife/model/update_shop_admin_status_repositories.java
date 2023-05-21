package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_shop_admin_status_repositories {
    private String agent_id, value;
    MutableLiveData<update_shop_admin_status_response> data;
    update_shop_admin_status_api update_shop_admin_status;
    private static update_shop_admin_status_repositories update_shop_admin_status_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public update_shop_admin_status_repositories() {
        //this.agent_id = agent_id;
        // this.value = value;
        data = new MutableLiveData<>();
        update_shop_admin_status = ApiUtilize.update_shop_admin_status_response();
    }

    public synchronized static update_shop_admin_status_repositories getInstance() {
        if (update_shop_admin_status_repositories == null) {
            return new update_shop_admin_status_repositories();
        }
        return update_shop_admin_status_repositories;
    }

    public @NonNull
    MutableLiveData<update_shop_admin_status_response> getData(@NonNull String agent_id, @NonNull String value) {
        Call<update_shop_admin_status_response> call = update_shop_admin_status.update_shop_admin_status(agent_id, value);
        call.enqueue(new Callback<update_shop_admin_status_response>() {
            @Override
            public void onResponse(Call<update_shop_admin_status_response> call, Response<update_shop_admin_status_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_shop_admin_status_response> call, Throwable t) {

            }
        });
        return data;
    }
}
