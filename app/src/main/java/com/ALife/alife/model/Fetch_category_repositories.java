package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fetch_category_repositories {
    String id, search;
    int page, limit;
    Category_fetch_api category;
    Category_fetch_by_search_api category_search;
    MutableLiveData<List<Category_response>> data;
    private static Fetch_category_repositories fetch_category_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public Fetch_category_repositories() {
       /* this.id = id;
        this.page=page;
        this.limit=limit;*/
        data = new MutableLiveData<>();
        category = ApiUtilize.get_Category();
        category_search = ApiUtilize.category_fetch_by_search_response();
    }

    public synchronized static Fetch_category_repositories getInstance() {
        if (fetch_category_repositories == null) {
            return new Fetch_category_repositories();
        }
        return fetch_category_repositories;
    }


    public @NonNull
    MutableLiveData<List<Category_response>> getdata(@NonNull String id,@NonNull int page,@NonNull int limit) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<List<Category_response>> call = category.getdata(id, page, limit);
        call.enqueue(new Callback<List<Category_response>>() {
            @Override
            public void onResponse(Call<List<Category_response>> call, Response<List<Category_response>> response) {
                if (response.isSuccessful()) {
                    List<Category_response> showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<List<Category_response>> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
            }


        });
        return data;
    }

    public @NonNull MutableLiveData<List<Category_response>> getCategory(@NonNull String id,@NonNull String search) {
        Call<List<Category_response>> call = category_search.getdata(id, search);
        call.enqueue(new Callback<List<Category_response>>() {
            @Override
            public void onResponse(Call<List<Category_response>> call, Response<List<Category_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category_response>> call, Throwable t) {

            }
        });
        return data;
    }
}
