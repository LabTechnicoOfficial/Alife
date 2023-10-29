package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_shop_business_summary_repositories {
    private String shop_id;
    private int page, limit;
    private get_shop_business_summary_api shop_business_summary;
    private get_shop_business_summary_details_api summary_details;
    private MutableLiveData<List<get_shop_business_summary_response>> data;
    private MutableLiveData<get_shop_business_summary_details_response> data_details;
    private static get_shop_business_summary_repositories get_shop_business_summary_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_shop_business_summary_repositories() {
        //this.shop_id = shop_id;
        //this.page=page;
        //this.limit=limit;
        shop_business_summary = ApiUtilize.shop_business_summary();
        summary_details = ApiUtilize.get_shop_business_summary_details();
        data_details = new MutableLiveData<>();
        data = new MutableLiveData<>();
    }

    public synchronized static get_shop_business_summary_repositories getInstance() {
        if (get_shop_business_summary_repositories == null) {
            return new get_shop_business_summary_repositories();
        }
        return get_shop_business_summary_repositories;
    }


    public @NonNull
    MutableLiveData<List<get_shop_business_summary_response>> getData(@NonNull String shop_id, @NonNull int page, @NonNull int limit) {
        Call<List<get_shop_business_summary_response>> call = shop_business_summary.getresponse(shop_id, page, limit);
        call.enqueue(new Callback<List<get_shop_business_summary_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_business_summary_response>> call, Response<List<get_shop_business_summary_response>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_business_summary_response>> call, Throwable t) {

            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<get_shop_business_summary_details_response> getDetails(@NonNull String shop_id) {
        Call<get_shop_business_summary_details_response> call = summary_details.getresponse(shop_id);
        call.enqueue(new Callback<get_shop_business_summary_details_response>() {
            @Override
            public void onResponse(Call<get_shop_business_summary_details_response> call, Response<get_shop_business_summary_details_response> response) {
                if (response.isSuccessful()) {
                    data_details.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<get_shop_business_summary_details_response> call, Throwable t) {

            }
        });
        return data_details;
    }
}
