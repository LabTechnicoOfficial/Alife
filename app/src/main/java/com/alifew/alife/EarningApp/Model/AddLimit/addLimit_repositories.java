package com.alifew.alife.EarningApp.Model.AddLimit;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addLimit_repositories {
    private addLimit_api addLimit;
    private MutableLiveData<addLimit_response> data;
    private static addLimit_repositories addLimit_repositories;
    private addLimit_repositories()
    {
        addLimit= APIUtilize.addLimit();
        data=new MutableLiveData<>();
    }
    public static synchronized addLimit_repositories getInstance()
    {
        if(addLimit_repositories==null)
            return new addLimit_repositories();
        return addLimit_repositories;
    }
    public MutableLiveData<addLimit_response> getData(String user_id,String date)
    {
        Call<addLimit_response> call=addLimit.getResponse(user_id, date);
        call.enqueue(new Callback<addLimit_response>() {
            @Override
            public void onResponse(Call<addLimit_response> call, Response<addLimit_response> response) {
                if(response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<addLimit_response> call, Throwable throwable) {

            }
        });
        return data;
    }
}
