package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class remove_manager_assistant_repositories {
    private String manager_id, assistant_id;
    MutableLiveData<remove_manager_assistant_response> data;
    remove_manager_assistant_api remove_manager_assistant;
    private static remove_manager_assistant_repositories remove_manager_assistant_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public remove_manager_assistant_repositories() {
        // this.manager_id = manager_id;
        // this.assistant_id = assistant_id;
        remove_manager_assistant = ApiUtilize.remove_manager_assistant_response();
        data = new MutableLiveData<>();
    }

    public synchronized static remove_manager_assistant_repositories getInstance() {
        if (remove_manager_assistant_repositories == null) {
            return new remove_manager_assistant_repositories();
        }
        return remove_manager_assistant_repositories;
    }

    public @NonNull
    MutableLiveData<remove_manager_assistant_response> getData(@NonNull String manager_id, @NonNull String assistant_id) {
        Call<remove_manager_assistant_response> call = remove_manager_assistant.remove_assistant(manager_id, assistant_id);
        call.enqueue(new Callback<remove_manager_assistant_response>() {
            @Override
            public void onResponse(Call<remove_manager_assistant_response> call, Response<remove_manager_assistant_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<remove_manager_assistant_response> call, Throwable t) {

            }
        });
        return data;
    }
}
