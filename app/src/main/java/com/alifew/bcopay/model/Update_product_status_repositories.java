package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_product_status_repositories {
    Update_product_status_api update_status;
    MutableLiveData<Update_product_status_response> data;
    private String id, value;
    private static Update_product_status_repositories update_product_status_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public Update_product_status_repositories() {
        //this.id = id;
        //this.value = value;
        update_status = ApiUtilize.update_product_status();
        data = new MutableLiveData<>();
    }

    public synchronized static Update_product_status_repositories getInstance() {
        if (update_product_status_repositories == null) {
            return new Update_product_status_repositories();
        }
        return update_product_status_repositories;
    }

    public @NonNull
    MutableLiveData<Update_product_status_response> getData(@NonNull String id, @NonNull String value) {
        Call<Update_product_status_response> call = update_status.update_product_status(id, value);
        call.enqueue(new Callback<Update_product_status_response>() {
            @Override
            public void onResponse(Call<Update_product_status_response> call, Response<Update_product_status_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Update_product_status_response> call, Throwable t) {

            }
        });
        return data;
    }
}
