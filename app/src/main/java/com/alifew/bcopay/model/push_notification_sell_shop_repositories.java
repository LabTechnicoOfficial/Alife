package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class push_notification_sell_shop_repositories {
    private String shop_id, customer_name, price, due;
    private push_notification_sell_shop_api push_notification;
    private MutableLiveData<push_notification_response> Data;
    private static push_notification_sell_shop_repositories push_notification_sell_shop_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public push_notification_sell_shop_repositories() {
       /* this.shop_id = shop_id;
        this.customer_name = customer_name;
        this.price = price;
        this.due = due;*/
        push_notification = ApiUtilize.sell_shop_notification();
        Data = new MutableLiveData<>();
    }

    public synchronized static push_notification_sell_shop_repositories getInstance() {
        if (push_notification_sell_shop_repositories == null) {
            return new push_notification_sell_shop_repositories();
        }
        return push_notification_sell_shop_repositories;
    }

    public @NonNull
    MutableLiveData<push_notification_response> getData(@NonNull String shop_id, @NonNull String customer_name, @NonNull String price, @NonNull String due) {
        Call<push_notification_response> call = push_notification.get_notification(shop_id, customer_name, price, due);
        call.enqueue(new Callback<push_notification_response>() {
            @Override
            public void onResponse(Call<push_notification_response> call, Response<push_notification_response> response) {
                if (response.isSuccessful()) {
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
