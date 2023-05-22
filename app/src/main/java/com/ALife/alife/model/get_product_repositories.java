package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_product_repositories {
    String id;
    int page, limit;
    get_product_api get_product;
    get_category_product_by_search_api category_product_by_search;
    get_single_product_api get_single_product;
    MutableLiveData<List<get_product_response>> data;
    MutableLiveData<get_product_response> single_product;
    private static get_product_repositories get_product_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public get_product_repositories() {
       /* this.id = id;
        this.page = page;
        this.limit = limit;*/
        get_product = ApiUtilize.get_product_response();
        get_single_product = ApiUtilize.get_single_product_response();
        category_product_by_search = ApiUtilize.category_product_search_response();
        data = new MutableLiveData<>();
        single_product = new MutableLiveData<>();
    }


    public synchronized static get_product_repositories getInstance() {
        if (get_product_repositories == null) {
            return new get_product_repositories();
        }
        return get_product_repositories;
    }

    public @NonNull
    MutableLiveData<List<get_product_response>> getdata(@NonNull String id, @NonNull int page, @NonNull int limit) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<List<get_product_response>> call = get_product.getproduct(id, page, limit);
        call.enqueue(new Callback<List<get_product_response>>() {
            @Override
            public void onResponse(Call<List<get_product_response>> call, Response<List<get_product_response>> response) {

                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<get_product_response>> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());

            }


        });
        return data;
    }

    public @NonNull
    MutableLiveData<List<get_product_response>> getCategoryProduct(@NonNull String id) {
        Call<List<get_product_response>> call = category_product_by_search.getproduct(id);
        call.enqueue(new Callback<List<get_product_response>>() {
            @Override
            public void onResponse(Call<List<get_product_response>> call, Response<List<get_product_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_product_response>> call, Throwable t) {

            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<get_product_response> getproduct(@NonNull String id) {

        Call<get_product_response> call = get_single_product.getproduct(id);
        call.enqueue(new Callback<get_product_response>() {
            @Override
            public void onResponse(Call<get_product_response> call, Response<get_product_response> response) {

                if (response.isSuccessful()) {
                    single_product.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<get_product_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());

            }


        });
        return single_product;
    }
}
