package com.alifew.bcopay.model;

import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class payment_method_repositories {
    private payment_method_api payment_method_api;
    private MutableLiveData<List<payment_method_response>> Data;
    private static payment_method_repositories payment_method_repositories;

    public payment_method_repositories() {
        payment_method_api= ApiUtilize.payment_method_response();
        Data=new MutableLiveData<>();
    }
    public synchronized static payment_method_repositories getInstance()
    {
        if(payment_method_repositories==null)
        {
            return new payment_method_repositories();
        }
        return payment_method_repositories;
    }
    public MutableLiveData<List<payment_method_response>> getData(String token)
    {
        Call<List<payment_method_response>> call=payment_method_api.payment_response(token);
        call.enqueue(new Callback<List<payment_method_response>>() {
            @Override
            public void onResponse(Call<List<payment_method_response>> call, Response<List<payment_method_response>> response) {
                if(response.isSuccessful())
                {
                    Data.postValue(response.body());
                    //Log.d("response:","yess");
                }
            }

            @Override
            public void onFailure(Call<List<payment_method_response>> call, Throwable t) {
                //Log.d("response:",t.getMessage());
            }
        });
        return Data;
    }
}
