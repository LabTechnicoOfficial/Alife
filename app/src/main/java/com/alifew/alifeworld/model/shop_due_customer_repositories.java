package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_due_customer_repositories {
    private String shop_id, search;
    private shop_due_customer_api due_customer;
    private shop_due_customer_by_search due_customer_by_search;
    private MutableLiveData<List<shop_due_customer_response>> Data;
    private static shop_due_customer_repositories shop_due_customer_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public shop_due_customer_repositories() {
        //this.shop_id = shop_id;
        due_customer = ApiUtilize.due_customer();
        due_customer_by_search = ApiUtilize.shop_due_customer_by_search();
        Data = new MutableLiveData<>();
    }

    public synchronized static shop_due_customer_repositories getInstance() {
        if (shop_due_customer_repositories == null) {
            return new shop_due_customer_repositories();
        }
        return shop_due_customer_repositories;
    }


    public @NonNull
    MutableLiveData<List<shop_due_customer_response>> getData(@NonNull String shop_id) {
        Call<List<shop_due_customer_response>> call = due_customer.getcustomer(shop_id);
        call.enqueue(new Callback<List<shop_due_customer_response>>() {
            @Override
            public void onResponse(Call<List<shop_due_customer_response>> call, Response<List<shop_due_customer_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<shop_due_customer_response>> call, Throwable t) {

            }
        });
        return Data;
    }

    public @NonNull
    MutableLiveData<List<shop_due_customer_response>> getSearchData(@NonNull String shop_id, @NonNull String search) {
        Call<List<shop_due_customer_response>> call = due_customer_by_search.getcustomer(shop_id, search);
        call.enqueue(new Callback<List<shop_due_customer_response>>() {
            @Override
            public void onResponse(Call<List<shop_due_customer_response>> call, Response<List<shop_due_customer_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<shop_due_customer_response>> call, Throwable t) {

            }
        });
        return Data;
    }
}
