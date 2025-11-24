package com.alifew.bcopay.model;

import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class user_instraction_repositories {
    private user_instraction_api user_instraction_api;
    private MutableLiveData<List<user_instruction_response>> data;
    private static user_instraction_repositories user_instraction_repositories;

    public user_instraction_repositories() {
        user_instraction_api= ApiUtilize.user_instraction();
        data=new MutableLiveData<>();
    }
    public synchronized static user_instraction_repositories getInstance()
    {
        if(user_instraction_repositories==null)
        {
            return new user_instraction_repositories();
        }
        return user_instraction_repositories;
    }
    public MutableLiveData<List<user_instruction_response>> getData(String token)
    {
        Call<List<user_instruction_response>> call=user_instraction_api.getInstraction(token);
        call.enqueue(new Callback<List<user_instruction_response>>() {
            @Override
            public void onResponse(Call<List<user_instruction_response>> call, Response<List<user_instruction_response>> response) {
                if(response.isSuccessful())
                {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<user_instruction_response>> call, Throwable t) {

            }
        });
        return data;
    }
}
