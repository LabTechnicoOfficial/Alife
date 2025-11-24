package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_shop_customer_due_list_repositories {
    String shop_id, customer_id;
    get_shop_customer_due_list_api get_due_list;
    MutableLiveData<List<get_shop_customer_due_list_response>> Data;
    private static get_shop_customer_due_list_repositories get_shop_customer_due_list_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_shop_customer_due_list_repositories() {
        //this.shop_id = shop_id;
        //this.customer_id = customer_id;

        get_due_list = ApiUtilize.get_shop_customer_due_list_response();
        Data = new MutableLiveData<>();
    }

    public synchronized static get_shop_customer_due_list_repositories getInstance() {
        if (get_shop_customer_due_list_repositories == null) {
            return new get_shop_customer_due_list_repositories();
        }
        return get_shop_customer_due_list_repositories;
    }

    public @NonNull
    MutableLiveData<List<get_shop_customer_due_list_response>> getData(@NonNull String shop_id, @NonNull String customer_id,@NonNull String customer_phone,int page,int limit) {
        Call<List<get_shop_customer_due_list_response>> call = get_due_list.shop_customer_due_list(shop_id, customer_id,customer_phone,page,limit);
        call.enqueue(new Callback<List<get_shop_customer_due_list_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_customer_due_list_response>> call, Response<List<get_shop_customer_due_list_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_customer_due_list_response>> call, Throwable t) {

            }
        });

        return Data;
    }
}
