package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_product_repositories {
    update_product_api1 update1;
    update_product_api2 update2;
    update_product_api3 update3;
    update_product_api4 update4;
    MutableLiveData<update_product_response> data;
    String product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, product_image, unit_selling_price, unit_price_with_offer, product_description, vaoture_no, vaoture_image;
    private static update_product_repositories update_product_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public update_product_repositories() {

        /*this.product_id = product_id;
        this.product_name = product_name;
        this.product_unit = product_unit;
        this.selling_price = selling_price;
        this.buy_price = buy_price;
        this.product_offer = product_offer;
        this.price_with_offer = price_with_offer;
        this.sell_profit = sell_profit;
        this.stock_amount = stock_amount;
        this.product_image = product_image;
        this.unit_selling_price = unit_selling_price;
        this.unit_price_with_offer = unit_price_with_offer;
        this.product_description = product_description;
        this.vaoture_no = vaoture_no;
        this.vaoture_image = vaoture_image;*/
        update1 = ApiUtilize.update_product_response1();
        update2 = ApiUtilize.update_product_response2();
        update3 = ApiUtilize.update_product_response3();
        update4 = ApiUtilize.update_product_response4();
        data = new MutableLiveData<>();

    }

    public synchronized static update_product_repositories getInstance() {
        if (update_product_repositories == null) {
            return new update_product_repositories();
        }
        return update_product_repositories;
    }

    public @NonNull
    MutableLiveData<update_product_response> getData1(@NonNull String product_id, @NonNull String product_name, @NonNull String product_unit, @NonNull String selling_price, @NonNull String buy_price, @NonNull String product_offer, @NonNull String price_with_offer, @NonNull String sell_profit, @NonNull String stock_amount, @NonNull String product_image, @NonNull String unit_selling_price, @NonNull String unit_price_with_offer, @NonNull String product_description, @NonNull String vaoture_no, @NonNull String vaoture_image) {

        Call<update_product_response> call = update1.update_product(product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, unit_selling_price, unit_price_with_offer, product_description, vaoture_no);
        call.enqueue(new Callback<update_product_response>() {
            @Override
            public void onResponse(Call<update_product_response> call, Response<update_product_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_product_response> call, Throwable t) {
                update_product_response response = new update_product_response();
                response.setMessage("something wrong");
                data.postValue(response);
            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<update_product_response> getData2(@NonNull String product_id, @NonNull String product_name, @NonNull String product_unit, @NonNull String selling_price, @NonNull String buy_price, @NonNull String product_offer, @NonNull String price_with_offer, @NonNull String sell_profit, @NonNull String stock_amount, @NonNull String product_image, @NonNull String unit_selling_price, @NonNull String unit_price_with_offer, @NonNull String product_description, @NonNull String vaoture_no, @NonNull String vaoture_image) {

        Call<update_product_response> call = update2.update_product(product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, product_image, unit_selling_price, unit_price_with_offer, product_description, vaoture_no, vaoture_image);
        call.enqueue(new Callback<update_product_response>() {
            @Override
            public void onResponse(Call<update_product_response> call, Response<update_product_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_product_response> call, Throwable t) {
                update_product_response response = new update_product_response();
                response.setMessage("something wrong");
                data.postValue(response);
            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<update_product_response> getData3(@NonNull String product_id, @NonNull String product_name, @NonNull String product_unit, @NonNull String selling_price, @NonNull String buy_price, @NonNull String product_offer, @NonNull String price_with_offer, @NonNull String sell_profit, @NonNull String stock_amount, @NonNull String product_image, @NonNull String unit_selling_price, @NonNull String unit_price_with_offer, @NonNull String product_description, @NonNull String vaoture_no, @NonNull String vaoture_image) {

        Call<update_product_response> call = update3.update_product(product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, product_image, unit_selling_price, unit_price_with_offer, product_description, vaoture_no);
        call.enqueue(new Callback<update_product_response>() {
            @Override
            public void onResponse(Call<update_product_response> call, Response<update_product_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_product_response> call, Throwable t) {
                update_product_response response = new update_product_response();
                response.setMessage("something wrong");
                data.postValue(response);
            }
        });
        return data;
    }

    public @NonNull
    MutableLiveData<update_product_response> getData4(@NonNull String product_id, @NonNull String product_name, @NonNull String product_unit, @NonNull String selling_price, @NonNull String buy_price, @NonNull String product_offer, @NonNull String price_with_offer, @NonNull String sell_profit, @NonNull String stock_amount, @NonNull String product_image, @NonNull String unit_selling_price, @NonNull String unit_price_with_offer, @NonNull String product_description, @NonNull String vaoture_no, @NonNull String vaoture_image) {

        Call<update_product_response> call = update4.update_product(product_id, product_name, product_unit, selling_price, buy_price, product_offer, price_with_offer, sell_profit, stock_amount, unit_selling_price, unit_price_with_offer, product_description, vaoture_no, vaoture_image);
        call.enqueue(new Callback<update_product_response>() {
            @Override
            public void onResponse(Call<update_product_response> call, Response<update_product_response> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_product_response> call, Throwable t) {
                update_product_response response = new update_product_response();
                response.setMessage("something wrong");
                data.postValue(response);
            }
        });
        return data;
    }
}





