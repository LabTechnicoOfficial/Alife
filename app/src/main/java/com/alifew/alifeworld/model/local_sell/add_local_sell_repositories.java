package com.alifew.alifeworld.model.local_sell;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_local_sell_repositories {
    private static add_local_sell_repositories add_local_sell_repositories;
    private MutableLiveData<add_local_sell_details_response> add_details;
    private MutableLiveData<add_local_sell_image_response> add_image;
    private MutableLiveData<add_local_sell_product_response> add_product;
    private MutableLiveData<delete_local_sell_product_response> delete_message, edit_message;
    private local_sell_api api;

    private add_local_sell_repositories() {
        add_details = new MutableLiveData<>();
        add_image = new MutableLiveData<>();
        add_product = new MutableLiveData<>();
        delete_message = new MutableLiveData<>();
        edit_message = new MutableLiveData<>();
        api = ApiUtilize.local_sell_api();
    }

    public synchronized static add_local_sell_repositories getInstance() {
        if (add_local_sell_repositories == null)
            return new add_local_sell_repositories();
        return add_local_sell_repositories;
    }

    public MutableLiveData<add_local_sell_details_response> getAdd_details(String sell_id, String description) {
        Call<add_local_sell_details_response> call = api.add_local_sell_details(sell_id, description);
        call.enqueue(new Callback<add_local_sell_details_response>() {
            @Override
            public void onResponse(Call<add_local_sell_details_response> call, Response<add_local_sell_details_response> response) {
                if (response.isSuccessful()) {
                    add_details.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<add_local_sell_details_response> call, Throwable throwable) {

            }
        });
        return add_details;
    }

    public MutableLiveData<add_local_sell_image_response> getAdd_image(String sell_id, String image, int check) {
        Call<add_local_sell_image_response> call = api.add_local_sell_image(sell_id, image, check);
        call.enqueue(new Callback<add_local_sell_image_response>() {
            @Override
            public void onResponse(Call<add_local_sell_image_response> call, Response<add_local_sell_image_response> response) {
                if (response.isSuccessful())
                    add_image.postValue(response.body());
            }

            @Override
            public void onFailure(Call<add_local_sell_image_response> call, Throwable throwable) {

            }
        });
        return add_image;
    }

    public MutableLiveData<add_local_sell_product_response> getAdd_product(String shop_id, String product_details, String price, String buyprice, String image) {
        Call<add_local_sell_product_response> call = api.add_local_sell_product(shop_id, product_details, price, buyprice, image);
        call.enqueue(new Callback<add_local_sell_product_response>() {
            @Override
            public void onResponse(Call<add_local_sell_product_response> call, Response<add_local_sell_product_response> response) {
                if (response.isSuccessful())
                    add_product.postValue(response.body());
            }

            @Override
            public void onFailure(Call<add_local_sell_product_response> call, Throwable throwable) {

            }
        });
        return add_product;
    }


    //delete product
    public MutableLiveData<delete_local_sell_product_response> deleteProduct(String product_id) {
        Call<delete_local_sell_product_response> call = api.delete_local_sell_product(product_id);
        call.enqueue(new Callback<delete_local_sell_product_response>() {
            @Override
            public void onResponse(Call<delete_local_sell_product_response> call, Response<delete_local_sell_product_response> response) {
                if (response.isSuccessful())
                    delete_message.postValue(response.body());
            }

            @Override
            public void onFailure(Call<delete_local_sell_product_response> call, Throwable t) {

            }
        });
        return delete_message;
    }

    //edit product
    public MutableLiveData<delete_local_sell_product_response> editProduct(String product_id, String product_details, String price, String buy_price, String image) {
        Call<delete_local_sell_product_response> call = api.edit_local_sell_product(product_id, product_details, price, buy_price, image);
        call.enqueue(new Callback<delete_local_sell_product_response>() {
            @Override
            public void onResponse(Call<delete_local_sell_product_response> call, Response<delete_local_sell_product_response> response) {
                if (response.isSuccessful())
                    edit_message.postValue(response.body());
            }

            @Override
            public void onFailure(Call<delete_local_sell_product_response> call, Throwable t) {

            }
        });
        return edit_message;
    }
}
