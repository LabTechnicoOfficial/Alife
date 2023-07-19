package com.alifew.alife.model;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customer_exist_check_repositories {
    private static customer_exist_check_repositories customer_exist_check_repositories;
    private MutableLiveData<customer_exist_check_response> data;
    customer_exist_check_api api;
    private customer_exist_check_repositories()
    {
        data=new MutableLiveData<>();
        api= ApiUtilize.customer_exist_check_api();
    }
    public synchronized static customer_exist_check_repositories getInstance()
    {
        if(customer_exist_check_repositories==null)
            return new customer_exist_check_repositories();
        return customer_exist_check_repositories;
    }

    public MutableLiveData<customer_exist_check_response> getData(String phone)
    {
        Call<customer_exist_check_response> call= api.customer_exist_check(phone);
        call.enqueue(new Callback<customer_exist_check_response>() {
            @Override
            public void onResponse(Call<customer_exist_check_response> call, Response<customer_exist_check_response> response) {
                if(response.isSuccessful())
                {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<customer_exist_check_response> call, Throwable throwable) {

            }
        });
        return data;
    }
}
