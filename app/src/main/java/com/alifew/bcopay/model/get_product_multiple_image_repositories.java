package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class get_product_multiple_image_repositories {
    get_product_multiple_image_api get_product_multiple_image_api;
    private String product_id;
    MutableLiveData<List<get_product_multiple_image_response>> data;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
private static get_product_multiple_image_repositories get_product_multiple_image_repositories;
    public get_product_multiple_image_repositories() {
       // this.product_id = product_id;
        get_product_multiple_image_api= ApiUtilize.get_product_multiple_image_response();
        data=new MutableLiveData<>();
    }
    public synchronized static get_product_multiple_image_repositories getInstance() {
        if (get_product_multiple_image_repositories == null) {
            return new get_product_multiple_image_repositories();
        }
        return get_product_multiple_image_repositories;
    }
    public  @NonNull
    MutableLiveData<List<get_product_multiple_image_response>> getData( @NonNull String product_id)
    {
        Call<List<get_product_multiple_image_response>> call=get_product_multiple_image_api.getproductimage(product_id);
        call.enqueue(new Callback<List<get_product_multiple_image_response>>() {
            @Override
            public void onResponse(Call<List<get_product_multiple_image_response>> call, Response<List<get_product_multiple_image_response>> response) {
                if (response.isSuccessful()){
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<get_product_multiple_image_response>> call, Throwable t) {

            }
        });
        return  data;
    }
}
