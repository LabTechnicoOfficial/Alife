package com.alifew.alifeworld.model.logout;

import androidx.lifecycle.MutableLiveData;

import com.alifew.alifeworld.API.ApiUtilize;
import com.alifew.alifeworld.model.CommonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogOutRepositories {

    private MutableLiveData<CommonResponse> commonResponse;
    private static LogOutRepositories logOutRepositories;
    Logout_api logoutApi;

    private LogOutRepositories() {
        commonResponse = new MutableLiveData<>();
        logoutApi = ApiUtilize.logoutApi();
    }

    public synchronized static LogOutRepositories getInstance() {
        if (logOutRepositories == null) {
            return new LogOutRepositories();
        }
        return logOutRepositories;
    }

    public MutableLiveData<CommonResponse> customerLogout(String id) {
        Call<CommonResponse> call = logoutApi.customerLogout(id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    commonResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });

        return commonResponse;
    }

    public MutableLiveData<CommonResponse> shopLogout(String id) {
        Call<CommonResponse> call = logoutApi.shopLogout(id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    commonResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });

        return commonResponse;
    }
}
