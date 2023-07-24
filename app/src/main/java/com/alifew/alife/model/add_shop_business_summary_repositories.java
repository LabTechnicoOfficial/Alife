package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_shop_business_summary_repositories {
    private String shop_id,description,credit_in,credit_out,date,time,image;
    private add_shop_business_summary_api add_shop_business_summary;
    private MutableLiveData<add_shop_business_summary_response> Data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_shop_business_summary_repositories add_shop_business_summary_repositories;
    public add_shop_business_summary_repositories() {
       /* this.shop_id = shop_id;
        this.description=description;
        this.credit_in = credit_in;
        this.credit_out = credit_out;
        this.date = date;
        this.time = time;
        this.image = image;*/
        add_shop_business_summary= ApiUtilize.add_shop_business_summary();
        Data=new MutableLiveData<>();

    }
    public synchronized static add_shop_business_summary_repositories getInstance() {
        if (add_shop_business_summary_repositories == null) {
            return new add_shop_business_summary_repositories();
        }
        return add_shop_business_summary_repositories;
    }
    public @NonNull
    MutableLiveData<add_shop_business_summary_response> getData(@NonNull String shop_id,@NonNull String description,@NonNull String credit_in,@NonNull String credit_out,@NonNull String date,@NonNull String time,@NonNull String image)
    {
        Call<add_shop_business_summary_response> call=add_shop_business_summary.getresponse(shop_id,description,credit_in,credit_out,date,time,image);
        call.enqueue(new Callback<add_shop_business_summary_response>() {
            @Override
            public void onResponse(Call<add_shop_business_summary_response> call, Response<add_shop_business_summary_response> response) {
                if (response.isSuccessful()){
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_shop_business_summary_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
