package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_shop_products_summary_repositories {
    private String category_id, shop_id;
    private get_shop_category_products_summary_api category_products_summary_api;
    private get_shop_all_products_summary_api all_products_summary_api;
    private MutableLiveData<get_shop_products_summary_response> data_category;
    private MutableLiveData<get_shop_products_summary_response> data_all;
    private static get_shop_products_summary_repositories get_shop_products_summary_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_shop_products_summary_repositories() {
        //this.shop_id = shop_id;
        data_all = new MutableLiveData<>();
        data_category = new MutableLiveData<>();
        category_products_summary_api = ApiUtilize.category_products_summary();
        all_products_summary_api = ApiUtilize.all_products_summary();

    }

    public synchronized static get_shop_products_summary_repositories getInstance() {
        if (get_shop_products_summary_repositories == null) {
            return new get_shop_products_summary_repositories();
        }
        return get_shop_products_summary_repositories;
    }


    public @NonNull
    MutableLiveData<get_shop_products_summary_response> getData_category(@NonNull String category_id, @NonNull String shop_id) {
        Call<get_shop_products_summary_response> call = category_products_summary_api.get(category_id, shop_id);
        call.enqueue(new Callback<get_shop_products_summary_response>() {
            @Override
            public void onResponse(Call<get_shop_products_summary_response> call, Response<get_shop_products_summary_response> response) {
                if (response.isSuccessful()) {
                    data_category.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_shop_products_summary_response> call, Throwable t) {

            }
        });
        return data_category;
    }

    public @NonNull
    MutableLiveData<get_shop_products_summary_response> getData_all(@NonNull String shop_id) {
        Call<get_shop_products_summary_response> call = all_products_summary_api.get(shop_id);
        call.enqueue(new Callback<get_shop_products_summary_response>() {
            @Override
            public void onResponse(Call<get_shop_products_summary_response> call, Response<get_shop_products_summary_response> response) {
                if (response.isSuccessful()) {
                    data_all.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_shop_products_summary_response> call, Throwable t) {

            }
        });
        return data_all;
    }
}

