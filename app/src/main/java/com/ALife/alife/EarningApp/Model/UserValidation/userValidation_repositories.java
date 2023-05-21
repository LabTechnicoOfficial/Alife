package com.ALife.alife.EarningApp.Model.UserValidation;

import androidx.lifecycle.MutableLiveData;


import com.ALife.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userValidation_repositories {
    private userValidation_api userValidation;
    private MutableLiveData<userValidation_response> data;
    private static userValidation_repositories userValidation_repositories;

    public userValidation_repositories() {
        userValidation= APIUtilize.userValidation();
        data=new MutableLiveData<>();
    }
    public synchronized static userValidation_repositories getInstance()
    {
        if(userValidation_repositories==null)
            return new userValidation_repositories();
        return userValidation_repositories;
    }

    public MutableLiveData<userValidation_response> getData(String phone,String token)
    {
        Call<userValidation_response> call=userValidation.getResponse(phone, token);
        call.enqueue(new Callback<userValidation_response>() {
            @Override
            public void onResponse(Call<userValidation_response> call, Response<userValidation_response> response) {
                if(response.isSuccessful()){
                    data.postValue(response.body());
                }else {
                    userValidation_response userValidation_response = new userValidation_response();
                    userValidation_response.setUser_id("-1");
                    data.postValue(userValidation_response);
                }

            }

            @Override
            public void onFailure(Call<userValidation_response> call, Throwable t) {

            }
        });
        return data;
    }
}
