package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_count_for_type_repositories {
    private String id;
    MutableLiveData<get_count_for_type_response> data;
    private get_count_for_type_api getCountForTypeApi;
    private static get_count_for_type_repositories get_count_for_type_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_count_for_type_repositories() {
        // this.id = id;
        data = new MutableLiveData<>();
        getCountForTypeApi = ApiUtilize.get_count_for_type_response();
    }

    public synchronized static get_count_for_type_repositories getInstance() {
        if (get_count_for_type_repositories == null) {
            return new get_count_for_type_repositories();
        }
        return get_count_for_type_repositories;
    }

    public @NonNull
    MutableLiveData<get_count_for_type_response> getData(@NonNull String id) {
        Call<get_count_for_type_response> call = getCountForTypeApi.getcount(id);
        call.enqueue(new Callback<get_count_for_type_response>() {
            @Override
            public void onResponse(Call<get_count_for_type_response> call, Response<get_count_for_type_response> response) {
                if (response.isSuccessful()){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_count_for_type_response> call, Throwable t) {

            }
        });
        return data;
    }
}
