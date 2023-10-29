package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

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
    MutableLiveData<List<Get_product_response>> data;
    MutableLiveData<Get_product_response> single_product;

    MutableLiveData<Fetch_product_detail_by_bar_code_response> product_detail_by_bar_code;
    private static get_product_repositories get_product_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public get_product_repositories() {
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
    MutableLiveData<List<Get_product_response>> getdata(@NonNull String id, @NonNull int page, @NonNull int limit) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<List<Get_product_response>> call = get_product.getproduct(id, page, limit);
        call.enqueue(new Callback<List<Get_product_response>>() {
            @Override
            public void onResponse(Call<List<Get_product_response>> call, Response<List<Get_product_response>> response) {

                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Get_product_response>> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());

            }


        });
        return data;
    }

    public @NonNull
    MutableLiveData<List<Get_product_response>> getCategoryProduct(@NonNull String id) {
        Call<List<Get_product_response>> call = category_product_by_search.getproduct(id);
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
    MutableLiveData<Get_product_response> getproduct(@NonNull String id) {

        Call<Get_product_response> call = get_single_product.getproduct(id);
        call.enqueue(new Callback<Get_product_response>() {
            @Override
            public void onResponse(Call<Get_product_response> call, Response<Get_product_response> response) {

                if (response.isSuccessful()) {
                    single_product.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<Get_product_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());

            }


        });
        return single_product;
    }


    public @NonNull
    MutableLiveData<Fetch_product_detail_by_bar_code_response> get_product_detail_by_bar_code(@NonNull String shopID, @NonNull String code) {
        if (product_detail_by_bar_code == null) {
            product_detail_by_bar_code = new MutableLiveData<>();
        }
        Call<Fetch_product_detail_by_bar_code_response> call = get_product.fetch_product_detail_by_qrcode(shopID, code);
        call.enqueue(new Callback<Fetch_product_detail_by_bar_code_response>() {
            @Override
            public void onResponse(Call<Fetch_product_detail_by_bar_code_response> call, Response<Fetch_product_detail_by_bar_code_response> response) {
                if (response.isSuccessful()) {
                   // Log.d("dataxx", "onResponse: success");
                    product_detail_by_bar_code.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Fetch_product_detail_by_bar_code_response> call, Throwable t) {
              //  Log.d("dataxx", "onResponse: failed "+t.getMessage());
            }
        });
        return product_detail_by_bar_code;
    }
}
