package com.alifew.bcopay.model.cupon;

import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sendPackageCustomer_notification_repositories {
    private cupon_api api;
    private static sendPackageCustomer_notification_repositories repositories;
    private MutableLiveData<notify_response> data;


    private sendPackageCustomer_notification_repositories() {
        data = new MutableLiveData<>();
        api = ApiUtilize.cupon_response();
    }

    public synchronized static sendPackageCustomer_notification_repositories getInstance() {
        if (repositories == null)
            return new sendPackageCustomer_notification_repositories();
        return repositories;
    }

    public MutableLiveData<notify_response> getData(String customer_id, String message) {
        Call<notify_response> call = api.customernotifyCoupon(customer_id, message);
        call.enqueue(new Callback<notify_response>() {
            @Override
            public void onResponse(Call<notify_response> call, Response<notify_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<notify_response> call, Throwable t) {

            }
        });
        return data;
    }
}
