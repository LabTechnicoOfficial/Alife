package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_type_count_repositories {
    String type, count, id;
    edit_type_count_api edit_type_count;
    MutableLiveData<edit_type_count_response> data;
    private static edit_type_count_repositories edit_type_count_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public edit_type_count_repositories() {
        /*this.type = type;
        this.count = count;
        this.id = id;*/
        data = new MutableLiveData<>();
        edit_type_count = ApiUtilize.edit_type_count_response();
    }

    public synchronized static edit_type_count_repositories getInstance() {
        if (edit_type_count_repositories == null) {
            return new edit_type_count_repositories();
        }
        return edit_type_count_repositories;
    }

    public @NonNull
    MutableLiveData<edit_type_count_response> getdata(@NonNull String type, @NonNull String count, @NonNull String id) {
        Call<edit_type_count_response> call = edit_type_count.edit_type_count(type, count, id);
        call.enqueue(new Callback<edit_type_count_response>() {
            @Override
            public void onResponse(Call<edit_type_count_response> call, Response<edit_type_count_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<edit_type_count_response> call, Throwable t) {
                edit_type_count_response response = new edit_type_count_response();
                response.setMessage(t.getMessage());
                data.postValue(response);
            }
        });

        return data;
    }
}
