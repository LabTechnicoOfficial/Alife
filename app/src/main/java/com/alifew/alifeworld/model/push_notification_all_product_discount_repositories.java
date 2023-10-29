package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class push_notification_all_product_discount_repositories {
    private String shop_id, all_discount;
    private push_notification_all_product_discount_api push_notification;
    private MutableLiveData<push_notification_response> Data;
    private static push_notification_all_product_discount_repositories push_notification_all_product_discount_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public push_notification_all_product_discount_repositories() {
        //this.shop_id = shop_id;
        // this.all_discount = all_discount;
        push_notification = ApiUtilize.all_discount_notification();
        Data = new MutableLiveData<>();
    }

    public synchronized static push_notification_all_product_discount_repositories getInstance() {
        if (push_notification_all_product_discount_repositories == null) {
            return new push_notification_all_product_discount_repositories();
        }
        return push_notification_all_product_discount_repositories;
    }

    public @NonNull MutableLiveData<push_notification_response> getData(@NonNull String shop_id,@NonNull  String all_discount) {
        Call<push_notification_response> call = push_notification.get_notification(shop_id, all_discount);
        call.enqueue(new Callback<push_notification_response>() {
            @Override
            public void onResponse(Call<push_notification_response> call, Response<push_notification_response> response) {
                if (response.isSuccessful()){
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<push_notification_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
