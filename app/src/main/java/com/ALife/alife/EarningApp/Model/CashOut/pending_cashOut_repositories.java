package com.ALife.alife.EarningApp.Model.CashOut;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.EarningApp.Model.APIUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pending_cashOut_repositories {
    private pending_cashOut_api pending_cashOut;
    private MutableLiveData<List<pending_cashOut_response>> data;
    private static pending_cashOut_repositories pending_cashOut_repositories;

    public pending_cashOut_repositories() {
        pending_cashOut= APIUtilize.pending_cashOut_response();
        data=new MutableLiveData<>();

    }
    public synchronized static pending_cashOut_repositories getInstance()
    {
        if(pending_cashOut_repositories==null)
            return new pending_cashOut_repositories();
        return pending_cashOut_repositories;
    }
    public MutableLiveData<List<pending_cashOut_response>> getData(String user_id,String token)
    {
        Call<List<pending_cashOut_response>> call=pending_cashOut.getResponse(user_id, token);
        call.enqueue(new Callback<List<pending_cashOut_response>>() {
            @Override
            public void onResponse(Call<List<pending_cashOut_response>> call, Response<List<pending_cashOut_response>> response) {
                if(response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<pending_cashOut_response>> call, Throwable t) {

            }
        });
        return data;

    }
}
