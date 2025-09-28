package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_add_repositories {
    String id;
    String name;
    String logo;
    String unit;
    Category_add_api category_add;
    MutableLiveData<Category_add_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    private static Category_add_repositories category_add_repositories;

    public Category_add_repositories() {
        /*this.id = id;
        this.name=name;
        this.unit=unit;
        this.logo=logo;*/
        data = new MutableLiveData<>();
        category_add = ApiUtilize.Category_add_response();
    }

    public synchronized static Category_add_repositories getInstance() {
        if (category_add_repositories == null) {
            return new Category_add_repositories();
        }
        return category_add_repositories;
    }

    public @NonNull
    MutableLiveData<Category_add_response> getdata(@NonNull String logo,@NonNull String name,@NonNull String unit,@NonNull String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<Category_add_response> call = category_add.add_category(logo, name, unit, id);
        call.enqueue(new Callback<Category_add_response>() {
            @Override
            public void onResponse(Call<Category_add_response> call, Response<Category_add_response> response) {
                if (response.isSuccessful()) {
                    Category_add_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<Category_add_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
                Category_add_response showresponse = new Category_add_response();
                showresponse.setMessage("Failed to add");
                data.postValue(showresponse);
            }


        });
        return data;
    }
}
