package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_shop_all_due_details_repositories {
    private get_shop_all_due_details_api get_due_details;
    private get_shop_selected_days_due_details_api get_shop_selected_days_due_details;
    private get_shop_daily_due_details_api get_shop_daily_due_details;
    private String shop_id, date, date1, date2;
    private int page, limit;
    private MutableLiveData<List<get_shop_all_due_details_response>> Data;
    private static get_shop_all_due_details_repositories get_shop_all_due_details_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_shop_all_due_details_repositories() {
        // this.shop_id = shop_id;
        //this.page=page;
        //this.limit=limit;
        get_due_details = ApiUtilize.get_shop_all_due_details_response();
        // Data = new MutableLiveData<>();
        get_shop_daily_due_details = ApiUtilize.shop_daily_due_details();
        //Data = new MutableLiveData<>();
        get_shop_selected_days_due_details = ApiUtilize.shop_selected_days_due_details();
        Data = new MutableLiveData<>();
    }

    public synchronized static get_shop_all_due_details_repositories getInstance() {
        if (get_shop_all_due_details_repositories == null) {
            return new get_shop_all_due_details_repositories();
        }
        return get_shop_all_due_details_repositories;
    }


    public @NonNull
    MutableLiveData<List<get_shop_all_due_details_response>> getData(@NonNull String shop_id, @NonNull int page, @NonNull int limit) {
        Call<List<get_shop_all_due_details_response>> call = get_due_details.get_due_details(shop_id, page, limit);
        call.enqueue(new Callback<List<get_shop_all_due_details_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_all_due_details_response>> call, Response<List<get_shop_all_due_details_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_all_due_details_response>> call, Throwable t) {

            }
        });
        return Data;
    }

    public @NonNull
    MutableLiveData<List<get_shop_all_due_details_response>> get_daily_details(@NonNull String shop_id, @NonNull String date, @NonNull int page, @NonNull int limit) {
        Call<List<get_shop_all_due_details_response>> call = get_shop_daily_due_details.get_due_details(shop_id, date, page, limit);
        call.enqueue(new Callback<List<get_shop_all_due_details_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_all_due_details_response>> call, Response<List<get_shop_all_due_details_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_all_due_details_response>> call, Throwable t) {

            }
        });
        return Data;
    }

    public @NonNull
    MutableLiveData<List<get_shop_all_due_details_response>> get_selected_days_details(@NonNull String shop_id, @NonNull String date1, @NonNull String date2, @NonNull int page, @NonNull int limit) {
        Call<List<get_shop_all_due_details_response>> call = get_shop_selected_days_due_details.get_due_details(shop_id, date1, date2, page, limit);
        call.enqueue(new Callback<List<get_shop_all_due_details_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_all_due_details_response>> call, Response<List<get_shop_all_due_details_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_all_due_details_response>> call, Throwable t) {

            }
        });
        return Data;
    }
}
