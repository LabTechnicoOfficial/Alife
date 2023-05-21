package com.ALife.alife.EarningApp.Model.AddLimit;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_addLimit_repositories {
    private update_addLimit_api update_addLimit;
    private MutableLiveData<update_addLimit_response> data;
    private static update_addLimit_repositories update_addLimit_repositories;
    private update_addLimit_repositories()
    {
        update_addLimit= APIUtilize.update_addLimit();
        data=new MutableLiveData<>();
    }
    public static synchronized update_addLimit_repositories getInstance()
    {
        if(update_addLimit_repositories==null)
            return new update_addLimit_repositories();
        return update_addLimit_repositories;
    }
    public MutableLiveData<update_addLimit_response> getData(String user_id)
    {
        Call<update_addLimit_response> call=update_addLimit.getResponse(user_id);
        call.enqueue(new Callback<update_addLimit_response>() {
            @Override
            public void onResponse(Call<update_addLimit_response> call, Response<update_addLimit_response> response) {
                if(response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<update_addLimit_response> call, Throwable throwable) {

            }
        });
        return data;
    }
}
