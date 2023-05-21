package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_version_repositories {
    private get_version_api get_version;
    private MutableLiveData<get_version_response> data;
    private static get_version_repositories get_version_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_version_repositories() {
        data = new MutableLiveData<>();
        get_version = ApiUtilize.get_version();
    }

    public synchronized static get_version_repositories getInstance() {
        if (get_version_repositories == null) {
            return new get_version_repositories();
        }
        return get_version_repositories;
    }

    public @NonNull
    MutableLiveData<get_version_response> getData() {

        Call<get_version_response> call = get_version.getproduct(1);
        call.enqueue(new Callback<get_version_response>() {
            @Override
            public void onResponse(Call<get_version_response> call, Response<get_version_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_version_response> call, Throwable t) {

            }
        });
        return data;
    }
}
