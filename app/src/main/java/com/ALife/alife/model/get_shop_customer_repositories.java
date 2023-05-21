package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_shop_customer_repositories {
    private String shop_id, search;
    private int page, limit;
    get_shop_customer_api get_customer;
    get_shop_customer_by_search_api get_shop_customer_by_search;
    fetch_shop_selected_CustomerList_api fetch_shop_selected_customerList;
    MutableLiveData<List<get_shop_customer_response>> Data;
    private static get_shop_customer_repositories get_shop_customer_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_shop_customer_repositories() {
        //this.shop_id = shop_id;

        fetch_shop_selected_customerList = ApiUtilize.fetch_shop_selected_customerList_response();

        get_shop_customer_by_search = ApiUtilize.get_shop_customer_by_search_response();

        get_customer = ApiUtilize.get_shop_customer_response();
        Data = new MutableLiveData<>();
    }

    public synchronized static get_shop_customer_repositories getInstance() {
        if (get_shop_customer_repositories == null) {
            return new get_shop_customer_repositories();
        }
        return get_shop_customer_repositories;
    }


    public @NonNull
    MutableLiveData<List<get_shop_customer_response>> getData(@NonNull String shop_id, @NonNull int page, @NonNull int limit) {
        Call<List<get_shop_customer_response>> call = get_customer.getcustomer(shop_id, page, limit);
        call.enqueue(new Callback<List<get_shop_customer_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_customer_response>> call, Response<List<get_shop_customer_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_customer_response>> call, Throwable t) {

            }
        });
        return Data;
    }

    // fetch all customer(requested and shop's)
    public @NonNull
    MutableLiveData<List<get_shop_customer_response>> get_selected_customer(@NonNull String shop_id) {
        Call<List<get_shop_customer_response>> call = fetch_shop_selected_customerList.getcustomer(shop_id);
        call.enqueue(new Callback<List<get_shop_customer_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_customer_response>> call, Response<List<get_shop_customer_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_customer_response>> call, Throwable t) {

            }
        });
        return Data;
    }

    public @NonNull
    MutableLiveData<List<get_shop_customer_response>> getSearchData(@NonNull String shop_id, @NonNull String search) {
        Call<List<get_shop_customer_response>> call = get_shop_customer_by_search.getcustomer(shop_id, search);
        call.enqueue(new Callback<List<get_shop_customer_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_customer_response>> call, Response<List<get_shop_customer_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_customer_response>> call, Throwable t) {

            }
        });
        return Data;

    }
}
