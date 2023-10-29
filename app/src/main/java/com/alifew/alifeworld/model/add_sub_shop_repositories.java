package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_sub_shop_repositories {
    private add_sub_shop_api add_sub_shop_api;
    private String parent_id, child_id;
    MutableLiveData<add_sub_shop_response> data;
    private static add_sub_shop_repositories add_sub_shop_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    private add_sub_shop_repositories() {
       /* this.parent_id = parent_id;
        this.child_id = child_id;*/
        add_sub_shop_api = ApiUtilize.add_sub_shop_response();
        data = new MutableLiveData<>();
    }

    public synchronized static add_sub_shop_repositories getInstance() {
        if (add_sub_shop_repositories == null) {
            return new add_sub_shop_repositories();
        }
        return add_sub_shop_repositories;
    }

    public @NonNull
    MutableLiveData<add_sub_shop_response> getData(@NonNull String parent_id, @NonNull String child_id) {
        Call<add_sub_shop_response> call = add_sub_shop_api.add_sub_shop(parent_id, child_id);
        call.enqueue(new Callback<add_sub_shop_response>() {
            @Override
            public void onResponse(Call<add_sub_shop_response> call, Response<add_sub_shop_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_sub_shop_response> call, Throwable t) {
                add_sub_shop_response add_sub_sop_response = new add_sub_shop_response();
                add_sub_sop_response.setMessage(t.getMessage());
                data.postValue(add_sub_sop_response);
            }
        });
        return data;
    }
}
