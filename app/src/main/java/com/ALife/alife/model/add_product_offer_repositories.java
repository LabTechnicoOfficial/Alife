package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_product_offer_repositories {
    String amount,price,id;
    add_product_offer_api add_product_offer;
    MutableLiveData<add_product_offer_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_product_offer_repositories add_product_offer_repositories;
    private add_product_offer_repositories() {
        /*this.amount = amount;
        this.price = price;
        this.id = id;*/
        data=new MutableLiveData<>();
        add_product_offer=ApiUtilize.add_product_offer_response();
    }
    public synchronized static add_product_offer_repositories getInstance() {
        if (add_product_offer_repositories == null) {
            return new add_product_offer_repositories();
        }
        return add_product_offer_repositories;
    }
    public @NonNull
    MutableLiveData<add_product_offer_response> getdata(@NonNull String amount,@NonNull String price,@NonNull String id)
    {
        Call<add_product_offer_response> call = add_product_offer.add_product_offer(amount,price,id);
        call.enqueue(new Callback<add_product_offer_response>() {
            @Override
            public void onResponse(Call<add_product_offer_response> call, Response<add_product_offer_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_product_offer_response> call, Throwable t) {
                add_product_offer_response response=new add_product_offer_response();
                response.setMessage(t.getMessage());
                data.postValue(response);
            }
        });

        return  data;
    }
}
