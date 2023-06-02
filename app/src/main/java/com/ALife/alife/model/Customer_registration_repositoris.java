package com.ALife.alife.model;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.API.ApiUtilize;

import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_registration_repositoris {
    Customer_registration_api registration;
    Customer_registration_varefy varefy;
    String name, location, phone, password, image, token;
    MutableLiveData<String> varefication;
    MutableLiveData<customer_registration_response> message;
    private static Customer_registration_repositoris customer_registration_repositories;

    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }

    public Customer_registration_repositoris() {
        /*this.name = name;
        this.location = location;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.token=token;*/
        registration = ApiUtilize.getcustomer_registration_response();
        varefy = ApiUtilize.getcustomer_varefy_response();
    }


    public synchronized static Customer_registration_repositoris getInstance() {
        if (customer_registration_repositories == null) {
            return new Customer_registration_repositoris();
        }
        return customer_registration_repositories;
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
                varefication.postValue("error");
            }


        });
        return varefication;
    }

    public @NonNull
    MutableLiveData<customer_registration_response> getMessage(@NonNull String name, @NonNull String location, @NonNull String phone, @NonNull String password, @NonNull String image, @NonNull String token) {
        if (message == null) {
            message = new MutableLiveData<>();
        }

        LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put("name", name);
        body.put("location", location);
        body.put("phone", phone);
        body.put("password", password);
        body.put("token", token);
        body.put("image", image);


        Log.d("dataxx", "getMessage: "+body);

        Call<customer_registration_response> call = registration.customer_registration(name, location, phone, password, image, token);

        call.enqueue(new Callback<customer_registration_response>() {
            @Override
            public void onResponse(Call<customer_registration_response> call, Response<customer_registration_response> response) {
                if (response.isSuccessful()) {
                    customer_registration_response showresponse = response.body();
                    message.postValue(showresponse);
                    //Log.d("phone: ",phone);
                }

            }

            @Override
            public void onFailure(Call<customer_registration_response> call, Throwable t) {
                //  message.postValue("error");
            }


        });
        return message;
    }
}
