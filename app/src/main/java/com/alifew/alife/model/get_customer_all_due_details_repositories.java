package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_customer_all_due_details_repositories {
    private String customer_id, date1, date2;
    int page, limit;
    private get_customer_all_due_details_api get_customer_all_due_details;
    private get_customer_daily_due_details_api get_customer_daily_due_details;
    private get_customer_selected_days_due_details_api get_customer_selected_days_due_details;
    private MutableLiveData<List<get_customer_all_due_details_response>> Data;
    private static get_customer_all_due_details_repositories get_customer_all_due_details_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public get_customer_all_due_details_repositories() {
        //this.customer_id = customer_id;
        //this.page=page;
        //this.limit=limit;
        get_customer_all_due_details = ApiUtilize.customer_due_details();
        get_customer_daily_due_details = ApiUtilize.get_customer_daily_due_details();
        get_customer_selected_days_due_details = ApiUtilize.get_customer_selected_days_due_details();
        Data = new MutableLiveData<>();
    }

    public synchronized static get_customer_all_due_details_repositories getInstance() {
        if (get_customer_all_due_details_repositories == null) {
            return new get_customer_all_due_details_repositories();
        }
        return get_customer_all_due_details_repositories;
    }


    @NonNull
    public MutableLiveData<List<get_customer_all_due_details_response>> getData(@NonNull String customer_id, @NonNull int page, @NonNull int limit) {
        Call<List<get_customer_all_due_details_response>> call = get_customer_all_due_details.get_due_details(customer_id, page, limit);
        call.enqueue(new Callback<List<get_customer_all_due_details_response>>() {
            @Override
            public void onResponse(Call<List<get_customer_all_due_details_response>> call, Response<List<get_customer_all_due_details_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_customer_all_due_details_response>> call, Throwable t) {

            }
        });
        return Data;
    }

    @NonNull
    public MutableLiveData<List<get_customer_all_due_details_response>> get_daily_details(@NonNull String customer_id, @NonNull String date1, int page, int limit) {
        Call<List<get_customer_all_due_details_response>> call = get_customer_daily_due_details.get_due_details(customer_id, date1, page, limit);
        call.enqueue(new Callback<List<get_customer_all_due_details_response>>() {
            @Override
            public void onResponse(Call<List<get_customer_all_due_details_response>> call, Response<List<get_customer_all_due_details_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_customer_all_due_details_response>> call, Throwable t) {

            }
        });
        return Data;
    }

    @NonNull
    public MutableLiveData<List<get_customer_all_due_details_response>> get_selected_details(@NonNull String customer_id, @NonNull String date1, @NonNull String date2, @NonNull int page, @NonNull int limit) {
        Call<List<get_customer_all_due_details_response>> call = get_customer_selected_days_due_details.get_due_details(customer_id, date1, date2, page, limit);
        call.enqueue(new Callback<List<get_customer_all_due_details_response>>() {
            @Override
            public void onResponse(Call<List<get_customer_all_due_details_response>> call, Response<List<get_customer_all_due_details_response>> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_customer_all_due_details_response>> call, Throwable t) {

            }
        });
        return Data;
    }
}
