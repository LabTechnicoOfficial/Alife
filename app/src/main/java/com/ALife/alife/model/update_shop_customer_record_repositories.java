package com.ALife.alife.model;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_shop_customer_record_repositories {
    private update_shop_customer_record_api update_shop_customer_record;
    private MutableLiveData<update_shop_customer_record_response> data;
    private static com.ALife.alife.model.update_shop_customer_record_repositories update_shop_customer_record_repositories;

    public update_shop_customer_record_repositories() {
        update_shop_customer_record = ApiUtilize.update_shop_customer_record();
        data = new MutableLiveData<>();

    }
    public synchronized static com.ALife.alife.model.update_shop_customer_record_repositories getInstance() {
        if (update_shop_customer_record_repositories== null) {
            return new update_shop_customer_record_repositories();
        }
        return update_shop_customer_record_repositories;
    }
    public MutableLiveData<update_shop_customer_record_response> getData(String customer_id, String customer_phone)
    {
        Call<update_shop_customer_record_response> call=update_shop_customer_record.update_record(customer_id, customer_phone);
        call.enqueue(new Callback<update_shop_customer_record_response>() {
            @Override
            public void onResponse(Call<update_shop_customer_record_response> call, Response<update_shop_customer_record_response> response) {
                if(response.isSuccessful())
                {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_shop_customer_record_response> call, Throwable t) {

            }
        });
        return data;
    }

}
