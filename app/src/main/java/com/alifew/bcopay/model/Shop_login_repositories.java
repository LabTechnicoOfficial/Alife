package com.alifew.bcopay.model;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shop_login_repositories {
    String phone, password;
    Shop_login_api shopLogin;
    MutableLiveData<Shop_login_response> idMessage;
    private static Shop_login_repositories shop_login_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    private Shop_login_repositories() {
        // this.phone = phone;
        //this.password = password;
        shopLogin = ApiUtilize.get_ShopLoginresponse();
        // idMessage = new MutableLiveData<>();
    }

    public synchronized static Shop_login_repositories getInstance() {
        if (shop_login_repositories == null) {
            return new Shop_login_repositories();
        }
        return shop_login_repositories;
    }

    public @NonNull
    MutableLiveData<Shop_login_response> getIdMessage(@NonNull String phone, @NonNull String password) {
        if (idMessage == null) {
            idMessage = new MutableLiveData<>();
        }
        Call<Shop_login_response> call = shopLogin.getShopLogin(phone, password);
        call.enqueue(new Callback<Shop_login_response>() {
            private Call<Shop_login_response> call;
            private Throwable t;
            private Response<Shop_login_response> response;

            @Override
            public void onResponse(@NonNull Call<Shop_login_response> call, @NonNull Response<Shop_login_response> response) {
                this.response = response;

                if (response.isSuccessful()) {
                    Shop_login_response showresponse = response.body();
                    idMessage.postValue(showresponse);
                }
            }



            @Override
            public void onFailure(@NonNull Call<Shop_login_response> call,
                                  @NonNull Throwable t) {
                this.call = call;
                this.t = t;
                Shop_login_response show_response = new Shop_login_response();
                show_response.setId("-1");
                show_response.setMessage(Objects.requireNonNull(t).getMessage());

                idMessage.postValue(show_response);
                Log.d("mesba",t.getMessage());
                // idMessage.setValue(t.getMessage());
            }


        });
        return idMessage;
    }

}
