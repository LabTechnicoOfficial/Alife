package com.ALife.alife.model.shop_notification;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.model.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_notification_repositories {
    private static shop_notification_repositories shop_notification_repositories;
    private MutableLiveData<shop_notification_response> data;
    shop_notification_api api;

    private shop_notification_repositories() {
        data = new MutableLiveData<>();
        api = ApiUtilize.shop_notification_api();
    }

    public synchronized static shop_notification_repositories getInstance() {
        if (shop_notification_repositories == null)
            return new shop_notification_repositories();
        return shop_notification_repositories;
    }

    public MutableLiveData<shop_notification_response> getData(String shop_id, String message) {
        Call<shop_notification_response> call = api.add_shop_notification(shop_id, message);
        call.enqueue(new Callback<shop_notification_response>() {
            @Override
            public void onResponse(Call<shop_notification_response> call, Response<shop_notification_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<shop_notification_response> call, Throwable t) {
                Log.d("errorxx", t.getMessage());
            }
        });
        return data;
    }
}
