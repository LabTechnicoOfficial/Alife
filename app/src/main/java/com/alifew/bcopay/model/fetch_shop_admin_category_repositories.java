package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fetch_shop_admin_category_repositories {
    fetch_shop_admin_category_api fetch_shop_admin_category;
    fetch_shop_admin_category_by_search_api fetch_shop_admin_category_by_search;
    private String agent_id, search;
    private int page, limit;
    MutableLiveData<List<Category_response>> data;
    private static fetch_shop_admin_category_repositories fetch_shop_admin_category_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public fetch_shop_admin_category_repositories() {
        /*this.agent_id = agent_id;
        this.page = page;
        this.limit = limit;*/
        fetch_shop_admin_category = ApiUtilize.fetch_shop_admin_category_response();
        fetch_shop_admin_category_by_search = ApiUtilize.fetch_shop_admin_category_by_search_response();

        data = new MutableLiveData<>();
    }

    public synchronized static fetch_shop_admin_category_repositories getInstance() {
        if (fetch_shop_admin_category_repositories == null) {
            return new fetch_shop_admin_category_repositories();
        }
        return fetch_shop_admin_category_repositories;
    }


    public @NonNull
    MutableLiveData<List<Category_response>> getData(@NonNull String agent_id, @NonNull int page, @NonNull int limit) {
        Call<List<Category_response>> call = fetch_shop_admin_category.getcategory(agent_id, page, limit);
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

    public @NonNull
    MutableLiveData<List<Category_response>> getSearchData(@NonNull String agent_id, @NonNull String search) {
        Call<List<Category_response>> call = fetch_shop_admin_category_by_search.getcategory(agent_id, search);
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
