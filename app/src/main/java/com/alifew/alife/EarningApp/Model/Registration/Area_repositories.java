package com.alifew.alife.EarningApp.Model.Registration;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.alifew.alife.EarningApp.Model.APIUtilize;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Area_repositories {
    private static Area_repositories area_repositories;
    Area_API areaApi;
    MutableLiveData<List<Area_response>> message;

    private Area_repositories() {
        areaApi = APIUtilize.areaApi();
    }

    public synchronized static Area_repositories getInstance() {
        if (area_repositories == null) {
            return new Area_repositories();
        }
        return area_repositories;
    }

    public @NonNull
    MutableLiveData<List<Area_response>> getMessage() {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<List<Area_response>> call = areaApi.getResponse();

        call.enqueue(new Callback<List<Area_response>>() {
            @Override
            public void onResponse(Call<List<Area_response>> call, Response<List<Area_response>> response) {

                if (response.isSuccessful()) {
                    List<Area_response> area_responses = response.body();
                    message.postValue(area_responses);
                }
            }

            @Override
            public void onFailure(Call<List<Area_response>> call, Throwable t) {
                List<Area_response> response = new ArrayList<>();
                message.postValue(response);
            }
        });
        return message;
    }
}
