package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class delete_type_count_repositories {
    String id;

    delete_type_count_api delete_type_count;
    MutableLiveData<delete_type_count_response> data;
    private static delete_type_count_repositories delete_type_count_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public delete_type_count_repositories() {
        // this.id = id;

        data = new MutableLiveData<>();
        delete_type_count = ApiUtilize.delete_type_count_response();

    }

    public synchronized static delete_type_count_repositories getInstance() {
        if (delete_type_count_repositories == null) {
            return new delete_type_count_repositories();
        }
        return delete_type_count_repositories;
    }

    public @NonNull
    MutableLiveData<delete_type_count_response> getdata(@NonNull String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<delete_type_count_response> call = delete_type_count.getdata(id);
        call.enqueue(new Callback<delete_type_count_response>() {
            @Override
            public void onResponse(Call<delete_type_count_response> call, Response<delete_type_count_response> response) {
                if (response.isSuccessful()) {
                    delete_type_count_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<delete_type_count_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
                delete_type_count_response showresponse = new delete_type_count_response();
                showresponse.setMessage("Failed to delete");
                data.postValue(showresponse);
            }


        });
        return data;
    }
}
