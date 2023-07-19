package com.alifew.alife.EarningApp.Model.addRequest;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alife.EarningApp.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_request_repositories {
    private check_request_api check_request;
    private update_request_api update_request;
    private MutableLiveData<add_request_response> data;
    private static add_request_repositories add_request_repositories;

    private add_request_repositories() {
        check_request = APIUtilize.check_request();
        update_request = APIUtilize.update_request();
        data = new MutableLiveData<>();
    }

    public synchronized static add_request_repositories getInstance() {
        if (add_request_repositories == null)
            return new add_request_repositories();
        return add_request_repositories;
    }

    public MutableLiveData<add_request_response> getData(String user_id) {
        Call<add_request_response> call = check_request.getResponse(user_id);
        call.enqueue(new Callback<add_request_response>() {
            @Override
            public void onResponse(Call<add_request_response> call, Response<add_request_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<add_request_response> call, Throwable throwable) {

            }
        });
        return data;
    }

    public MutableLiveData<add_request_response> updateData(String user_id) {
        Call<add_request_response> call = update_request.getResponse(user_id);
        call.enqueue(new Callback<add_request_response>() {
            @Override
            public void onResponse(Call<add_request_response> call, Response<add_request_response> response) {
                if (response.isSuccessful())
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<add_request_response> call, Throwable throwable) {

            }
        });
        return data;
    }
}
