package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_product_repositories {
    add_product_api add_product;
    String name, id1, id2, unit, price, discount, buy_price, sell_profit, stock_amount, image, description, vaoture_no, vaoture_image, brand, productCode, added_by;
    MutableLiveData<add_product_response> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static add_product_repositories add_product_repositories;
    private add_product_repositories() {
      /*  this.name = name;
        this.id1 = id1;
        this.id2 = id2;
        this.unit = unit;
        this.price = price;
        this.discount = discount;
        this.buy_price = buy_price;
        this.sell_profit = sell_profit;
        this.stock_amount = stock_amount;
        this.image = image;
        this.description = description;
        this.vaoture_no = vaoture_no;
        this.vaoture_image = vaoture_image;
        this.brand = brand;
        this.productCode = productCode;
        this.added_by = added_by;*/
        add_product = ApiUtilize.add_product_response();
        data = new MutableLiveData<>();
    }
    public synchronized static add_product_repositories getInstance() {
        if (add_product_repositories == null) {
            return new add_product_repositories();
        }
        return add_product_repositories;
    }
    public @NonNull
    MutableLiveData<add_product_response> getMessage(@NonNull String name,@NonNull String id1,@NonNull String id2,@NonNull String unit,@NonNull String price,@NonNull String discount,@NonNull String buy_price,@NonNull String sell_profit,@NonNull String stock_amount,@NonNull String image,@NonNull String description,@NonNull String vaoture_no,@NonNull String vaoture_image,@NonNull String brand,@NonNull String productCode,@NonNull String added_by) {
        Call<add_product_response> call = add_product.add_product(name, id1, id2, unit, price, discount, buy_price, sell_profit, stock_amount, image, description, vaoture_no, vaoture_image, brand, productCode, added_by);

        call.enqueue(new Callback<add_product_response>() {
            @Override
            public void onResponse(Call<add_product_response> call, Response<add_product_response> response) {
                if (response.isSuccessful()){
                add_product_response showresponse = response.body();
                data.postValue(showresponse);}

            }

            @Override
            public void onFailure(Call<add_product_response> call, Throwable t) {
                add_product_response showresponse = new add_product_response();
                showresponse.setMessage(t.getMessage());
                showresponse.setId("-1");
                data.postValue(showresponse);
            }


        });
        return data;
    }
}
