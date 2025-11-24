package com.alifew.bcopay.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_profile_repositories {
    String id;
    shop_profile_api shop_profile;
    MutableLiveData<Shop_profile_response> data;
    private static shop_profile_repositories shop_profile_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public shop_profile_repositories() {
        //this.id = id;
        shop_profile = ApiUtilize.shop_profile_response();
        data = new MutableLiveData<>();
    }

    public synchronized static shop_profile_repositories getInstance() {
        if (shop_profile_repositories == null) {
            return new shop_profile_repositories();
        }
        return shop_profile_repositories;
    }

    public @NonNull
    MutableLiveData<Shop_profile_response> getdata(@NonNull String id) {

        Call<Shop_profile_response> call = shop_profile.getdata(id);
        call.enqueue(new Callback<Shop_profile_response>() {
            @Override
            public void onResponse(Call<Shop_profile_response> call, Response<Shop_profile_response> response) {
                if (response.isSuccessful()) {
                    Shop_profile_response showresponse = response.body();
                    data.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<Shop_profile_response> call, Throwable t) {
                //Toast.makeText(Shop_details_repositories.this,"something error.Try again",Toast.LENGTH_SHORT).show();

                // idMessage.setValue(t.getMessage());
            }


        });
        return data;
    }
}
