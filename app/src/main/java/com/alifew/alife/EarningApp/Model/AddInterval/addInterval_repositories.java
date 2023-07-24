package com.alifew.alife.EarningApp.Model.AddInterval;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addInterval_repositories {
    private MutableLiveData<addInterval_response> data;
    private addInterval_api api;
    private static addInterval_repositories addInterval_repositories;

    private addInterval_repositories() {
        data=new MutableLiveData<>();
        api= APIUtilize.addInterval();
    }
    public static synchronized addInterval_repositories getInstance()
    {
        if(addInterval_repositories==null)
            return new addInterval_repositories();
        return addInterval_repositories;
    }

    public MutableLiveData<addInterval_response> getData(String user_id,String date,int key)
    {
        Call<addInterval_response> call= api.getResponse(user_id, date, key);
        call.enqueue(new Callback<addInterval_response>() {
            @Override
            public void onResponse(Call<addInterval_response> call, Response<addInterval_response> response) {
                if(response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<addInterval_response> call, Throwable throwable) {

            }
        });
        return data;
    }

}
