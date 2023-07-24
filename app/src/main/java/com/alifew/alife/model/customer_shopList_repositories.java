package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customer_shopList_repositories {
    customer_shopList_api customer_shopList;
    get_customer_shop_by_search_api get_customer_shop_by_search;
    private String customer_id, search;
    private int page, limit;
    MutableLiveData<List<customer_shopList_response>> data;
    private static customer_shopList_repositories customer_shopList_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public customer_shopList_repositories() {
        //this.customer_id = customer_id;
        //this.page=page;
        //this.limit=limit;
        customer_shopList = ApiUtilize.customer_shopList_response();
        get_customer_shop_by_search = ApiUtilize.get_customer_shop_by_search_response();
        data = new MutableLiveData<>();
    }

    public synchronized static customer_shopList_repositories getInstance() {
        if (customer_shopList_repositories == null) {
            return new customer_shopList_repositories();
        }
        return customer_shopList_repositories;
    }


    public @NonNull
    MutableLiveData<List<customer_shopList_response>> getData(@NonNull String customer_id, @NonNull int page, @NonNull int limit) {
        Call<List<customer_shopList_response>> call = customer_shopList.getshop(customer_id, page, limit);
        call.enqueue(new Callback<List<customer_shopList_response>>() {
            @Override
            public void onResponse(Call<List<customer_shopList_response>> call, Response<List<customer_shopList_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<customer_shopList_response>> call, Throwable t) {

            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<List<customer_shopList_response>> getSearchData(@NonNull String customer_id, @NonNull String search) {
        Call<List<customer_shopList_response>> call = get_customer_shop_by_search.getshop(customer_id, search);
        call.enqueue(new Callback<List<customer_shopList_response>>() {
            @Override
            public void onResponse(Call<List<customer_shopList_response>> call, Response<List<customer_shopList_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<customer_shopList_response>> call, Throwable t) {

            }
        });
        return data;
    }
}
