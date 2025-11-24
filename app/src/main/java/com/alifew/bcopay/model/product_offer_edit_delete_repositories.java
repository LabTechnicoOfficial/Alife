package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_offer_edit_delete_repositories {
    private String offer_id, amount, percentage;
    private product_offer_edit_api offer_edit;
    private delete_product_offer_api offer_delete;
    private MutableLiveData<product_offer_edit_delete_response> Data;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    private static product_offer_edit_delete_repositories product_offer_edit_delete_repositories;

    public product_offer_edit_delete_repositories() {
        /*this.offer_id = offer_id;
        this.amount = amount;
        this.percentage = percentage;*/
        offer_edit = ApiUtilize.offer_edit_response();
        Data = new MutableLiveData<>();
        offer_delete = ApiUtilize.offer_delete_response();
        Data = new MutableLiveData<>();
    }

    public synchronized static product_offer_edit_delete_repositories getInstance() {
        if (product_offer_edit_delete_repositories == null) {
            return new product_offer_edit_delete_repositories();
        }
        return product_offer_edit_delete_repositories;
    }

    public @NonNull
    MutableLiveData<product_offer_edit_delete_response> getEditResponse(@NonNull String offer_id, @NonNull String amount, @NonNull String percentage) {
        Call<product_offer_edit_delete_response> call = offer_edit.edit_product_offer(amount, percentage, offer_id);
        call.enqueue(new Callback<product_offer_edit_delete_response>() {
            @Override
            public void onResponse(Call<product_offer_edit_delete_response> call, Response<product_offer_edit_delete_response> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<product_offer_edit_delete_response> call, Throwable t) {

            }
        });
        return Data;
    }

    public @NonNull
    MutableLiveData<product_offer_edit_delete_response> getDeleteResponse(@NonNull String offer_id) {
        Call<product_offer_edit_delete_response> call = offer_delete.delete_product_offer(offer_id);
        call.enqueue(new Callback<product_offer_edit_delete_response>() {
            @Override
            public void onResponse(Call<product_offer_edit_delete_response> call, Response<product_offer_edit_delete_response> response) {
                if (response.isSuccessful()) {
                    Data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<product_offer_edit_delete_response> call, Throwable t) {

            }
        });
        return Data;
    }
}
