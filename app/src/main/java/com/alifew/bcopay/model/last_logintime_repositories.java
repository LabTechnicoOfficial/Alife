package com.alifew.bcopay.model;

import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class last_logintime_repositories {
    private last_logintime_api last_logintime;
    private update_last_logintime_api update_last_logintime;
    private MutableLiveData<last_logintime_response> data;
    private MutableLiveData<update_last_logintime_response> update_response;
    private static last_logintime_repositories last_logintime_repositories;
    private last_logintime_repositories()
    {
        last_logintime= ApiUtilize.last_logintime();
        update_last_logintime=ApiUtilize.update_last_logintime();
        data=new MutableLiveData<>();
        update_response=new MutableLiveData<>();
    }
    public static synchronized last_logintime_repositories getInstance()
    {
        if(last_logintime_repositories==null)
            return new last_logintime_repositories();
        return last_logintime_repositories;
    }

    public MutableLiveData<last_logintime_response> getData(String user_id,String type)
    {
        Call<last_logintime_response> call=last_logintime.getresponse(user_id, type);
        call.enqueue(new Callback<last_logintime_response>() {
            @Override
            public void onResponse(Call<last_logintime_response> call, Response<last_logintime_response> response) {
                if(response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<last_logintime_response> call, Throwable t) {

            }
        });
        return data;
    }
    public MutableLiveData<update_last_logintime_response> getUpdate_response(String user_id,String logintime,String user_type)
    {
        Call<update_last_logintime_response> call=update_last_logintime.update(user_id, logintime, user_type);
        call.enqueue(new Callback<update_last_logintime_response>() {
            @Override
            public void onResponse(Call<update_last_logintime_response> call, Response<update_last_logintime_response> response) {
                if(response.isSuccessful())
                    update_response.postValue(response.body());
            }

            @Override
            public void onFailure(Call<update_last_logintime_response> call, Throwable t) {

            }
        });
        return update_response;
    }
}
