package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_manager_assistantList_repositories {
    get_manager_assistantList_api get_manager_assistantList;
    private String manager_id;
    MutableLiveData<List<fetch_shop_admin_response>> data;
    private static get_manager_assistantList_repositories get_manager_assistantList_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_manager_assistantList_repositories() {
        //this.manager_id = manager_id;
        data = new MutableLiveData<>();
        get_manager_assistantList = ApiUtilize.get_manager_assistantList_response();
    }

    public synchronized static get_manager_assistantList_repositories getInstance() {
        if (get_manager_assistantList_repositories == null) {
            return new get_manager_assistantList_repositories();
        }
        return get_manager_assistantList_repositories;
    }

    public @NonNull
    MutableLiveData<List<fetch_shop_admin_response>> getData(@NonNull String manager_id) {
        Call<List<fetch_shop_admin_response>> call = get_manager_assistantList.fetch_manager_assistant(manager_id);
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
