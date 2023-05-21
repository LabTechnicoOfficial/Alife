package com.ALife.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_all_product_offer_repositories {
    private String minimum_amount, minimum_price, offer_percentage, id;
    private add_shop_all_product_offer_api add_shop_all_product_offer;
    private get_shop_all_product_offer_api get_shop_all_product_offer;
    private delete_shop_all_product_offer_api delete_shop_all_product_offer;
    private MutableLiveData<List<get_shop_all_product_offer_response>> Data_offer;
    private MutableLiveData<add_shop_all_product_offer_response> add_offer;
    private MutableLiveData<delete_shop_all_product_offer_response> delete_offer;
    private static shop_all_product_offer_repositories shop_all_product_offer_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public shop_all_product_offer_repositories() {
        /*this.minimum_amount = minimum_amount;
        this.minimum_price = minimum_price;
        this.offer_percentage = offer_percentage;
        this.id = id;*/
        add_shop_all_product_offer = ApiUtilize.add_shop_all_product_offer();
        add_offer = new MutableLiveData<>();
        Data_offer = new MutableLiveData<>();
        delete_offer = new MutableLiveData<>();
        get_shop_all_product_offer = ApiUtilize.get_shop_all_product_offer();
        delete_shop_all_product_offer = ApiUtilize.delete_shop_all_product_offer();
    }

    public synchronized static shop_all_product_offer_repositories getInstance() {
        if (shop_all_product_offer_repositories == null) {
            return new shop_all_product_offer_repositories();
        }
        return shop_all_product_offer_repositories;
    }


    public @NonNull
    MutableLiveData<add_shop_all_product_offer_response> add_offer(@NonNull String minimum_amount, @NonNull String minimum_price, @NonNull String offer_percentage, @NonNull String id) {
        Call<add_shop_all_product_offer_response> call = add_shop_all_product_offer.getresponse(id, minimum_amount, minimum_price, offer_percentage);
        call.enqueue(new Callback<add_shop_all_product_offer_response>() {
            @Override
            public void onResponse(Call<add_shop_all_product_offer_response> call, Response<add_shop_all_product_offer_response> response) {
                if (response.isSuccessful()) {
                    add_offer.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_shop_all_product_offer_response> call, Throwable t) {

            }
        });
        return add_offer;
    }

    public @NonNull
    MutableLiveData<List<get_shop_all_product_offer_response>> getData_offer(@NonNull String id) {
        Call<List<get_shop_all_product_offer_response>> call = get_shop_all_product_offer.get_response(id);
        call.enqueue(new Callback<List<get_shop_all_product_offer_response>>() {
            @Override
            public void onResponse(Call<List<get_shop_all_product_offer_response>> call, Response<List<get_shop_all_product_offer_response>> response) {
                if (response.isSuccessful()) {
                    Data_offer.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_shop_all_product_offer_response>> call, Throwable t) {

            }
        });
        return Data_offer;
    }

    public @NonNull
    MutableLiveData<delete_shop_all_product_offer_response> delete_offer(@NonNull String id) {
        Call<delete_shop_all_product_offer_response> call = delete_shop_all_product_offer.getdata(id);
        call.enqueue(new Callback<delete_shop_all_product_offer_response>() {
            @Override
            public void onResponse(Call<delete_shop_all_product_offer_response> call, Response<delete_shop_all_product_offer_response> response) {
                if (response.isSuccessful()) {
                    delete_offer.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<delete_shop_all_product_offer_response> call, Throwable t) {

            }
        });
        return delete_offer;
    }
}
