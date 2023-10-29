package com.alifew.alifeworld.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_login_repositories {
    String phone, password;
    Customer_login_api shopLogin;
    MutableLiveData<Shop_login_response> idMessage;
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

    }
    private static Customer_login_repositories customer_login_repositories;

    private Customer_login_repositories() {
        //this.phone = phone;
        //this.password = password;
        shopLogin = ApiUtilize.get_CustomerLoginresponse();
    }

    public synchronized static Customer_login_repositories getInstance() {
        if (customer_login_repositories == null) {
            return new Customer_login_repositories();
        }
        return customer_login_repositories;
    }

    public @NonNull
    MutableLiveData<Shop_login_response> getIdMessage(@NonNull String phone, @NonNull String password) {
        if (idMessage == null) {
            idMessage = new MutableLiveData<>();
        }
        Call<Shop_login_response> call = shopLogin.getCustomerLogin(phone, password);
        call.enqueue(new Callback<Shop_login_response>() {
            @Override
            public void onResponse(Call<Shop_login_response> call, Response<Shop_login_response> response) {
                if (response.isSuccessful()) {
                    Shop_login_response showresponse = response.body();
                    idMessage.postValue(showresponse);
                }

            }

            @Override
            public void onFailure(Call<Shop_login_response> call, Throwable t) {
                Shop_login_response show_response = new Shop_login_response();
                show_response.setId("-1");
                show_response.setMessage(t.getMessage());

                idMessage.postValue(show_response);

                // idMessage.setValue(t.getMessage());
            }


        });
        return idMessage;
    }
}
