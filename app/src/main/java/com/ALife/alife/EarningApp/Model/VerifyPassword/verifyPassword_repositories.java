package com.ALife.alife.EarningApp.Model.VerifyPassword;

import androidx.lifecycle.MutableLiveData;

import com.ALife.alife.EarningApp.Model.APIUtilize;
import com.google.firebase.database.MutableData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verifyPassword_repositories {
    private verifyPassword_api verifyPassword;
    private MutableLiveData<verifyPassword_response> data;
    private static verifyPassword_repositories verifyPassword_repositories;

    private verifyPassword_repositories() {
        verifyPassword = APIUtilize.verifyPassword();
        data = new MutableLiveData<>();
    }

    public static synchronized verifyPassword_repositories getInstance() {
        if (verifyPassword_repositories == null)
            return new verifyPassword_repositories();
        return verifyPassword_repositories;
    }

    public MutableLiveData<verifyPassword_response> getData(String id, String password, String type) {
        Call<verifyPassword_response> call = verifyPassword.getResponse(id, password, type);
        call.enqueue(new Callback<verifyPassword_response>() {
            @Override
            public void onResponse(Call<verifyPassword_response> call, Response<verifyPassword_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<verifyPassword_response> call, Throwable t) {

            }
        });
        return data;
    }
}
