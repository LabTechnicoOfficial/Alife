package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_product_offer_repositories {
    String id;
    get_product_offer_api get_product_offer;
    get_all_product_offer_api get_all_product_offer;
    MutableLiveData<List<get_product_offer_response>> data;
    MutableLiveData<List<get_all_product_offer_response>> data_all;
    private static get_product_offer_repositories get_product_offer_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_product_offer_repositories() {
        // this.id = id;
        get_product_offer = ApiUtilize.get_product_offer_response();
        get_all_product_offer = ApiUtilize.get_all_product_offer();
        data = new MutableLiveData<>();
        data_all = new MutableLiveData<>();
    }

    public synchronized static get_product_offer_repositories getInstance() {
        if (get_product_offer_repositories == null) {
            return new get_product_offer_repositories();
        }
        return get_product_offer_repositories;
    }

    public @NonNull
    MutableLiveData<List<get_product_offer_response>> getdata(@NonNull String id) {

        Call<List<get_product_offer_response>> call = get_product_offer.getproduct_offer(id);
        call.enqueue(new Callback<List<get_product_offer_response>>() {
            @Override
            public void onResponse(Call<List<get_product_offer_response>> call, Response<List<get_product_offer_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_product_offer_response>> call, Throwable t) {
            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<List<get_all_product_offer_response>> get_all_data(@NonNull String id) {
        Call<List<get_all_product_offer_response>> call = get_all_product_offer.getAllproduct_offer(id);
        call.enqueue(new Callback<List<get_all_product_offer_response>>() {
            @Override
            public void onResponse(Call<List<get_all_product_offer_response>> call, Response<List<get_all_product_offer_response>> response) {
                if (response.isSuccessful()) {
                    data_all.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_all_product_offer_response>> call, Throwable t) {

            }
        });
        return data_all;
    }
}
