package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_manager_assistant_repositories {
    private String manager_id,assistant_id;
    MutableLiveData<add_manager_assistant_response> data;
    add_manager_assistant_api add_manager_assistant;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_manager_assistant_repositories add_manager_assistant_repositories;
    public add_manager_assistant_repositories() {
        /*this.manager_id = manager_id;
        this.assistant_id = assistant_id;*/
        add_manager_assistant= ApiUtilize.add_manager_assistant_response();
        data=new MutableLiveData<>();

    }
    public synchronized static add_manager_assistant_repositories getInstance() {
        if (add_manager_assistant_repositories == null) {
            return new add_manager_assistant_repositories();
        }
        return add_manager_assistant_repositories;
    }
    public @NonNull
    MutableLiveData<add_manager_assistant_response> getData(@NonNull String manager_id,@NonNull String assistant_id)
    {
        Call<add_manager_assistant_response> call=add_manager_assistant.add_manager_assistant(manager_id,assistant_id);
        call.enqueue(new Callback<add_manager_assistant_response>() {
            @Override
            public void onResponse(Call<add_manager_assistant_response> call, Response<add_manager_assistant_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_manager_assistant_response> call, Throwable t) {

            }
        });
        return data;
    }
}
