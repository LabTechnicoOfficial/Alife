package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shop_details_repositories {
    String id;
    Shop_details_api shop_details;
    MutableLiveData<Shop_response> data;
    private static Shop_details_repositories shop_details_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public Shop_details_repositories() {
        //this.id = id;
        shop_details = ApiUtilize.get_Shop();
        data = new MutableLiveData<>();
    }

    public synchronized static Shop_details_repositories getInstance() {
        if (shop_details_repositories == null) {
            return new Shop_details_repositories();
        }
        return shop_details_repositories;
    }

    public @NonNull
    MutableLiveData<Shop_response> getdata(@NonNull String id) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        Call<Shop_response> call = shop_details.getdata(id);
        call.enqueue(new Callback<Shop_response>() {
            @Override
            public void onResponse(Call<Shop_response> call, Response<Shop_response> response) {
                Shop_response showresponse = response.body();
                if (response.isSuccessful()) {
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<Shop_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
            }


        });
        return data;
    }


}
