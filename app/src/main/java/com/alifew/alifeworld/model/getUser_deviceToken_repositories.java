package com.alifew.alifeworld.model;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getUser_deviceToken_repositories {
    private getUser_deviceToken_api api;
    private MutableLiveData<getUser_deviceToken_response> data;
    private static getUser_deviceToken_repositories getUser_deviceToken_repositories;

    private getUser_deviceToken_repositories() {
        api = ApiUtilize.getUserToken();
        data = new MutableLiveData<>();
    }

    public static synchronized getUser_deviceToken_repositories getInstance() {
        if (getUser_deviceToken_repositories == null)
            return new getUser_deviceToken_repositories();
        return getUser_deviceToken_repositories;
    }

    public MutableLiveData<getUser_deviceToken_response> getData(String user_id, String user_type) {
        Call<getUser_deviceToken_response> call = api.getToken(user_id, user_type);
        call.enqueue(new Callback<getUser_deviceToken_response>() {
            @Override
            public void onResponse(Call<getUser_deviceToken_response> call, Response<getUser_deviceToken_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<getUser_deviceToken_response> call, Throwable t) {

            }
        });
        return data;
    }
    public MutableLiveData<getUser_deviceToken_response> getMessage(String user_id, String user_type,String token) {
        Call<getUser_deviceToken_response> call = api.getMessage(user_id, user_type,token);
        call.enqueue(new Callback<getUser_deviceToken_response>() {
            @Override
            public void onResponse(Call<getUser_deviceToken_response> call, Response<getUser_deviceToken_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<getUser_deviceToken_response> call, Throwable t) {

            }
        });
        return data;
    }
}
