package com.ALife.alife.EarningApp.Model.Profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.ALife.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_repositories {
    private static Profile_repositories profile_repositories;
    Profile_API profileApi;
    MutableLiveData<Profile_response> message;

    private Profile_repositories() {
        profileApi = APIUtilize.profileApi();
    }

    public synchronized static Profile_repositories getInstance() {
        if (profile_repositories == null) {
            return new Profile_repositories();
        }
        return profile_repositories;
    }

    public @NonNull
    MutableLiveData<Profile_response> getData( @NonNull String userID) {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<Profile_response> call = profileApi.getResponse(userID);

        call.enqueue(new Callback<Profile_response>() {
            @Override
            public void onResponse(Call<Profile_response> call, Response<Profile_response> response) {

                if (response.isSuccessful()) {
                    Profile_response profile_response = response.body();
                    message.postValue(profile_response);
                }

            }

            @Override
            public void onFailure(Call<Profile_response> call, Throwable t) {
                Profile_response response = new Profile_response();
                message.postValue(response);

                Log.d("ErrorXXX", t.getMessage());
            }
        });
        return message;
    }
}
