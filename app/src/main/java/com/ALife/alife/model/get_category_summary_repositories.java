package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_category_summary_repositories {
    private String shop_id, agent_id;
    private get_shop_category_summary_api shop_category_summary_api;
    private fetch_shop_admin_category_summary_api fetch_shop_admin_category_summary;
    private MutableLiveData<get_shop_products_summary_response> summary_shop, summary_agent;
    private static get_category_summary_repositories get_category_summary_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_category_summary_repositories() {
        // this.shop_id = shop_id;
        shop_category_summary_api = ApiUtilize.shop_category_summary_response();
        summary_shop = new MutableLiveData<>();
        fetch_shop_admin_category_summary = ApiUtilize.fetch_shop_admin_category_summary_response();
        summary_agent = new MutableLiveData<>();
    }

    public synchronized static get_category_summary_repositories getInstance() {
        if (get_category_summary_repositories == null) {
            return new get_category_summary_repositories();
        }
        return get_category_summary_repositories;
    }

    @NonNull
    public MutableLiveData<get_shop_products_summary_response> getSummary_shop(@NonNull String shop_id) {
        Call<get_shop_products_summary_response> call = shop_category_summary_api.get(shop_id);
        call.enqueue(new Callback<get_shop_products_summary_response>() {
            @Override
            public void onResponse(Call<get_shop_products_summary_response> call, Response<get_shop_products_summary_response> response) {
                if (response.isSuccessful()) {
                    summary_shop.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_shop_products_summary_response> call, Throwable t) {

            }
        });
        return summary_shop;
    }

    @NonNull
    public MutableLiveData<get_shop_products_summary_response> getSummary_Agent(@NonNull String shop_id, @NonNull String agent_id) {
        Call<get_shop_products_summary_response> call = fetch_shop_admin_category_summary.get(shop_id, agent_id);
        call.enqueue(new Callback<get_shop_products_summary_response>() {
            @Override
            public void onResponse(Call<get_shop_products_summary_response> call, Response<get_shop_products_summary_response> response) {
                if (response.isSuccessful()) {
                    summary_agent.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_shop_products_summary_response> call, Throwable t) {

            }
        });
        return summary_agent;
    }
}
