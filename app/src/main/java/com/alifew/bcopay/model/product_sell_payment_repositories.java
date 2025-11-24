package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_sell_payment_repositories {
    private String sell_id, shop_id, customer_id, transaction_type, payment_system, payment_amount, date;
    add_sell_payment_cash_api paymentCashApi;
    add_payment_transaction_api paymentDueApi;
    private MutableLiveData<add_sell_payment_cash_response> data_cash;
    private MutableLiveData<add_payment_transaction_response> data_due;
    private static product_sell_payment_repositories product_sell_payment_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public product_sell_payment_repositories() {
        /*this.sell_id = sell_id;
        this.payment_system = payment_system;
        this.payment_amount = payment_amount;
        this.date=date;*/
        paymentCashApi = ApiUtilize.add_sell_payment_cash_response();
        paymentDueApi = ApiUtilize.add_sell_payment_due_response();
        data_due = new MutableLiveData<>();
        data_cash = new MutableLiveData<>();
    }

    public synchronized static product_sell_payment_repositories getInstance() {
        if (product_sell_payment_repositories == null) {
            return new product_sell_payment_repositories();
        }
        return product_sell_payment_repositories;
    }


    public @NonNull
    MutableLiveData<add_sell_payment_cash_response> getData_cash(@NonNull String sell_id, @NonNull String payment_system, @NonNull String payment_amount, @NonNull String date) {
        Call<add_sell_payment_cash_response> call = paymentCashApi.add_sell_payment_cash(sell_id, payment_system, payment_amount, date);
        call.enqueue(new Callback<add_sell_payment_cash_response>() {
            @Override
            public void onResponse(Call<add_sell_payment_cash_response> call, Response<add_sell_payment_cash_response> response) {
                if (response.isSuccessful()) {
                    data_cash.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_sell_payment_cash_response> call, Throwable t) {

            }
        });
        return data_cash;
    }

    public @NonNull
    MutableLiveData<add_payment_transaction_response> getData_due(@NonNull String sell_id, @NonNull String shop_id, @NonNull String customer_id,@NonNull String customer_phone, @NonNull String transaction_type, @NonNull String payment_amount, @NonNull String payment_system, @NonNull String date) {
        Call<add_payment_transaction_response> call = paymentDueApi.add_sell_payment_due(sell_id, shop_id, customer_id,customer_phone, transaction_type, payment_amount, payment_system, date);
        call.enqueue(new Callback<add_payment_transaction_response>() {
            @Override
            public void onResponse(Call<add_payment_transaction_response> call, Response<add_payment_transaction_response> response) {
                if (response.isSuccessful()) {
                    data_due.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_payment_transaction_response> call, Throwable t) {

            }
        });
        return data_due;
    }
}
