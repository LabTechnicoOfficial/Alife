package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_shop_admin_information_repositories {
    get_shop_admin_information_api get_shop_admin_information;
    MutableLiveData<get_shop_admin_information_response> data;
    private String agent_id;
    private static get_shop_admin_information_repositories get_shop_admin_information_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_shop_admin_information_repositories() {
        // this.agent_id = agent_id;
        data = new MutableLiveData<>();
        get_shop_admin_information = ApiUtilize.get_shop_admin_information_response();
    }

    public synchronized static get_shop_admin_information_repositories getInstance() {
        if (get_shop_admin_information_repositories == null) {
            return new get_shop_admin_information_repositories();
        }
        return get_shop_admin_information_repositories;
    }

    public @NonNull
    MutableLiveData<get_shop_admin_information_response> getData(@NonNull String agent_id) {
        Call<get_shop_admin_information_response> call = get_shop_admin_information.get_shop_admin(agent_id);
        call.enqueue(new Callback<get_shop_admin_information_response>() {
            @Override
            public void onResponse(Call<get_shop_admin_information_response> call, Response<get_shop_admin_information_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_shop_admin_information_response> call, Throwable t) {

            }
        });
        return data;
    }
}
