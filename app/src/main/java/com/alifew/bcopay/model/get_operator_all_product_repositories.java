package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_operator_all_product_repositories {
    private String agent_id;
    private int page, limit;
    private get_operator_all_product_api get_operator_product;
    private get_operator_all_product_by_search_api get_operator_all_product_by_search;
    private MutableLiveData<List<Get_product_response>> data;
    private static get_operator_all_product_repositories get_operator_all_product_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_operator_all_product_repositories() {
        //this.agent_id = agent_id;
        //this.page=page;
        //this.limit=limit;
        data = new MutableLiveData<>();
        get_operator_product = ApiUtilize.get_operator_all_product_response();
        get_operator_all_product_by_search = ApiUtilize.get_operator_all_product_by_search_response();

    }

    public synchronized static get_operator_all_product_repositories getInstance() {
        if (get_operator_all_product_repositories == null) {
            return new get_operator_all_product_repositories();
        }
        return get_operator_all_product_repositories;
    }


    public @NonNull
    MutableLiveData<List<Get_product_response>> getData(@NonNull String agent_id, @NonNull int page, @NonNull int limit) {
        Call<List<Get_product_response>> call = get_operator_product.getproduct(agent_id, page, limit);
        call.enqueue(new Callback<List<Get_product_response>>() {
            @Override
            public void onResponse(Call<List<Get_product_response>> call, Response<List<Get_product_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Get_product_response>> call, Throwable t) {

            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<List<Get_product_response>> getSearchData(@NonNull String agent_id) {
        Call<List<Get_product_response>> call = get_operator_all_product_by_search.getproduct(agent_id);
        call.enqueue(new Callback<List<Get_product_response>>() {
            @Override
            public void onResponse(Call<List<Get_product_response>> call, Response<List<Get_product_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Get_product_response>> call, Throwable t) {

            }
        });
        return data;
    }
}
