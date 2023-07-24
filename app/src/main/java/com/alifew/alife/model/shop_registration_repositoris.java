package com.alifew.alife.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_registration_repositoris {
    Shop_registration_api registration;
    shop_registration_varefy varefy;
    String name, owner, location, phone, password, image, token;
    MutableLiveData<String> message, varefication;
    private static shop_registration_repositoris shop_registration_repositoris;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    private shop_registration_repositoris() {
        /*this.name = name;
        this.owner = owner;
        this.location = location;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.token=token;*/
        registration = ApiUtilize.getshop_registration_response();
        varefy = ApiUtilize.getshop_varefy_response();
    }


    public synchronized static shop_registration_repositoris getInstance() {
        if (shop_registration_repositoris == null) {
            return new shop_registration_repositoris();
        }
        return shop_registration_repositoris;
    }

    public @NonNull
    MutableLiveData<String> getVarefication(@NonNull String phone) {
        if (varefication == null) {
            varefication = new MutableLiveData<>();
        }
        Call<varefy_response> call = varefy.getvarefy(phone);
        call.enqueue(new Callback<varefy_response>() {
            @Override
            public void onResponse(Call<varefy_response> call, Response<varefy_response> response) {
                if (response.isSuccessful()) {
                    varefy_response showresponse = response.body();
                    varefication.postValue(showresponse.getMessage());
                }

            }

            @Override
            public void onFailure(Call<varefy_response> call, Throwable t) {
                varefication.postValue(t.getMessage());
            }


        });
        return varefication;
    }

    public @NonNull
    MutableLiveData<String> getMessage(@NonNull String name, @NonNull String owner, @NonNull String location, @NonNull String phone, @NonNull String password, @NonNull String image, @NonNull String token) {
        if (message == null) {
            message = new MutableLiveData<>();
        }
        Call<registration_response> call = registration.shop_registration(name, owner, location, phone, password, image, token);

        call.enqueue(new Callback<registration_response>() {
            @Override
            public void onResponse(Call<registration_response> call, Response<registration_response> response) {
                if (response.isSuccessful()) {
                    registration_response showresponse = response.body();
                    message.postValue(showresponse.getMessage());
                }

            }

            @Override
            public void onFailure(Call<registration_response> call, Throwable t) {
                message.postValue(t.getMessage());
            }


        });
        return message;
    }
}
