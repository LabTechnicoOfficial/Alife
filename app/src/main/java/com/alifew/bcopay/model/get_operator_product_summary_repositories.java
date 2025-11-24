package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_operator_product_summary_repositories {
    private String shop_id, agent_id;
    private get_operator_all_product_summary_api get_operator_all_product_summary;
    private MutableLiveData<get_shop_products_summary_response> data;
    private static get_operator_product_summary_repositories get_operator_product_summary_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_operator_product_summary_repositories() {
       /* this.shop_id = shop_id;
        this.agent_id = agent_id;*/
        get_operator_all_product_summary = ApiUtilize.get_operator_all_product_summary_response();
        data = new MutableLiveData<>();
    }

    public synchronized static get_operator_product_summary_repositories getInstance() {
        if (get_operator_product_summary_repositories == null) {
            return new get_operator_product_summary_repositories();
        }
        return get_operator_product_summary_repositories;
    }

    public @NonNull
    MutableLiveData<get_shop_products_summary_response> getData(@NonNull String shop_id, @NonNull String agent_id) {
        Call<get_shop_products_summary_response> call = get_operator_all_product_summary.get(shop_id, agent_id);
        call.enqueue(new Callback<get_shop_products_summary_response>() {
            @Override
            public void onResponse(Call<get_shop_products_summary_response> call, Response<get_shop_products_summary_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_shop_products_summary_response> call, Throwable t) {

            }
        });
        return data;

    }

}
