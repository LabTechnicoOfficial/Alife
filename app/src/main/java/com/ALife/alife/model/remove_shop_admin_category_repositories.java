package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class remove_shop_admin_category_repositories {
    remove_shop_admin_category_api remove_shop_admin_category;
    MutableLiveData<remove_shop_admin_category_response> data;
    private String agent_id, category_id;
    private static remove_shop_admin_category_repositories remove_shop_admin_category_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public remove_shop_admin_category_repositories() {
        // this.agent_id = agent_id;
        //this.category_id = category_id;
        remove_shop_admin_category = ApiUtilize.remove_shop_admin_category_response();
        data = new MutableLiveData<>();
    }

    public synchronized static remove_shop_admin_category_repositories getInstance() {
        if (remove_shop_admin_category_repositories == null) {
            return new remove_shop_admin_category_repositories();
        }
        return remove_shop_admin_category_repositories;
    }

    public @NonNull MutableLiveData<remove_shop_admin_category_response> getData(@NonNull String agent_id, @NonNull String category_id) {
        Call<remove_shop_admin_category_response> call = remove_shop_admin_category.remove_category(agent_id, category_id);
        call.enqueue(new Callback<remove_shop_admin_category_response>() {
            @Override
            public void onResponse(Call<remove_shop_admin_category_response> call, Response<remove_shop_admin_category_response> response) {
                if (response.isSuccessful()){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<remove_shop_admin_category_response> call, Throwable t) {

            }
        });
        return data;
    }
}
