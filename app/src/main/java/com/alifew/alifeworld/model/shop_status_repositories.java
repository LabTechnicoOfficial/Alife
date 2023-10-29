package com.alifew.alifeworld.model;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop_status_repositories {
    private shop_status_api shop_status;
    private MutableLiveData<shop_status_response> data;
    private static shop_status_repositories shop_status_repositories;
    public shop_status_repositories()
    {
        shop_status= ApiUtilize.shop_status();
        data=new MutableLiveData<>();
    }
    public synchronized static shop_status_repositories getInstance()
    {
        if(shop_status_repositories==null)
        {
            return new shop_status_repositories();
        }
        return shop_status_repositories;
    }
    public MutableLiveData<shop_status_response> getData(String id)
    {
        Call<shop_status_response> call=shop_status.getStatus(id);
        call.enqueue(new Callback<shop_status_response>() {
            @Override
            public void onResponse(Call<shop_status_response> call, Response<shop_status_response> response) {
                if(response.isSuccessful())
                {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<shop_status_response> call, Throwable t) {

            }
        });
        return data;
    }
}
