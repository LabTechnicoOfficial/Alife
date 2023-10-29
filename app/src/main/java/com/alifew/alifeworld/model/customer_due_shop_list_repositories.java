package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customer_due_shop_list_repositories {
    private String customer_id;
    private customer_due_shop_list_api customer_due_shop_list;
    private MutableLiveData<List<customer_due_shop_list_response>> Data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static customer_due_shop_list_repositories customer_due_shop_list_repositories;
    public customer_due_shop_list_repositories() {
       // this.customer_id = customer_id;
        customer_due_shop_list= ApiUtilize.customer_due_shop_list();
        Data=new MutableLiveData<>();
    }
    public synchronized static customer_due_shop_list_repositories getInstance() {
        if (customer_due_shop_list_repositories == null) {
            return new customer_due_shop_list_repositories();
        }
        return customer_due_shop_list_repositories;
    }
    public @NonNull
    MutableLiveData<List<customer_due_shop_list_response>> getData(@NonNull String customer_id)
    {
        Call<List<customer_due_shop_list_response>> call=customer_due_shop_list.get_due_shop(customer_id);
        call.enqueue(new Callback<List<customer_due_shop_list_response>>() {
            @Override
            public void onResponse(Call<List<customer_due_shop_list_response>> call, Response<List<customer_due_shop_list_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<customer_due_shop_list_response>> call, Throwable t) {

            }
        });
        return Data;
    }
}
