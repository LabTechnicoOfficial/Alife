package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_all_shop_product_repositories {
    get_shop_all_product_api get_shop_all_product;
    get_all_shop_product_by_search_api get_all_shop_product_by_search;
    private String shop_id;
    private int page, limit;
    MutableLiveData<List<Get_product_response>> data;
    private static get_all_shop_product_repositories get_all_shop_product_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_all_shop_product_repositories() {
       /* this.shop_id = shop_id;
        this.page=page;
        this.limit=limit;*/
        get_shop_all_product = ApiUtilize.get_all_shop_product_response();
        get_all_shop_product_by_search = ApiUtilize.get_all_shop_product_by_search_response();
        data = new MutableLiveData<>();
    }

    public synchronized static get_all_shop_product_repositories getInstance() {
        if (get_all_shop_product_repositories == null) {
            return new get_all_shop_product_repositories();
        }
        return get_all_shop_product_repositories;
    }


    public @NonNull
    MutableLiveData<List<Get_product_response>> getData(@NonNull String shop_id, @NonNull int page, @NonNull int limit) {
        Call<List<Get_product_response>> call = get_shop_all_product.get_allproduct(shop_id, page, limit);
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
    MutableLiveData<List<Get_product_response>> getAllProductWithOutPagination(@NonNull String shopID){
        Call<List<Get_product_response>> call = get_shop_all_product.getAllProductWithOutPagination(shopID);
        call.enqueue(new Callback<List<Get_product_response>>() {
            @Override
            public void onResponse(Call<List<Get_product_response>> call, Response<List<Get_product_response>> response) {
                if(response.isSuccessful()){
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
    MutableLiveData<List<Get_product_response>> getSearchData(@NonNull String shop_id) {
        Call<List<Get_product_response>> call = get_all_shop_product_by_search.get_allproduct(shop_id);
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
