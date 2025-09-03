package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_sell_repositories {
    private String shop_id, customer_id, customer_name, customer_phone, price, buy_price, selled_by, sell_type, date;
    private String sell_id, product_id, type_id, product_amount, product_price, product_buy_price;
    private String stock;
    private String type_count, product_type_id, type;
    private add_product_sell_api product_sell_api;
    private add_sell_details_api sell_details_api;
    private update_product_stock_by_sell_api product_stock_by_sell_api;
    private update_product_type_by_sell_api product_type_by_sell_api;
    private MutableLiveData<add_product_sell_response> data_sell_product;
    private MutableLiveData<add_sell_details_response> data_sell_details;
    private MutableLiveData<update_product_stock_by_sell_response> data_stock;
    private MutableLiveData<update_product_type_by_sell_response> data_type;
    private static product_sell_repositories product_sell_repositories;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    public product_sell_repositories() {

        product_sell_api = ApiUtilize.add_product_sell_response();
        data_sell_product = new MutableLiveData<>();
        sell_details_api = ApiUtilize.add_sell_details_response();
        data_sell_details = new MutableLiveData<>();
        product_stock_by_sell_api = ApiUtilize.update_product_stock_by_sell_response();
        data_stock = new MutableLiveData<>();
        product_type_by_sell_api = ApiUtilize.update_product_type_by_sell_response();
        data_type = new MutableLiveData<>();
    }

    public synchronized static product_sell_repositories getInstance() {
        if (product_sell_repositories == null) {
            return new product_sell_repositories();
        }
        return product_sell_repositories;
    }


    public @NonNull
    MutableLiveData<add_product_sell_response> getData_sell_product(@NonNull String shop_id, @NonNull String customer_id, @NonNull String customer_name, @NonNull String customer_phone, @NonNull String price, @NonNull String buy_price,String duePrice, String points, @NonNull String selled_by, @NonNull String sell_type, Boolean dueCheck) {
        //date time
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // Define formatters
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Convert to String
        String dateStr = date.format(dateFormatter);
        String timeStr = time.format(timeFormatter);

        Call<add_product_sell_response> call = product_sell_api.add_product_sell(shop_id, customer_id, customer_name, customer_phone, price, buy_price, selled_by, sell_type, dateStr, timeStr, duePrice, points, dueCheck);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<add_product_sell_response> call, Response<add_product_sell_response> response) {
                if (response.isSuccessful()) {
                    data_sell_product.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_product_sell_response> call, Throwable t) {

            }
        });
        return data_sell_product;
    }

    public @NonNull
    MutableLiveData<add_sell_details_response> getData_sell_details(@NonNull String sell_id, @NonNull String product_id,String product_name,String product_image, @NonNull String type_id, @NonNull String product_amount, @NonNull String product_price, @NonNull String product_buy_price) {
        Call<add_sell_details_response> call = sell_details_api.add_product_sell_details(sell_id, product_id,product_name,product_image, type_id, product_amount, product_price, product_buy_price);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<add_sell_details_response> call, Response<add_sell_details_response> response) {
                if (response.isSuccessful()) {
                    data_sell_details.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_sell_details_response> call, Throwable t) {

            }
        });
        return data_sell_details;
    }

    public @NonNull
    MutableLiveData<update_product_stock_by_sell_response> getData_stock(@NonNull String stock, @NonNull String product_id) {
        Call<update_product_stock_by_sell_response> call = product_stock_by_sell_api.update_product_stock(stock, product_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<update_product_stock_by_sell_response> call, Response<update_product_stock_by_sell_response> response) {
                if (response.isSuccessful()) {
                    data_stock.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_product_stock_by_sell_response> call, Throwable t) {

            }
        });
        return data_stock;
    }

    public @NonNull
    MutableLiveData<update_product_type_by_sell_response> getData_type(@NonNull String type_count, @NonNull String product_type_id, @NonNull String type) {
        Call<update_product_type_by_sell_response> call = product_type_by_sell_api.update_product_type(type_count, product_type_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<update_product_type_by_sell_response> call, Response<update_product_type_by_sell_response> response) {
                if (response.isSuccessful()) {
                    data_type.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<update_product_type_by_sell_response> call, Throwable t) {

            }
        });
        return data_type;
    }
}
