package com.alifew.bcopay.model.app_info;

import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;
import com.alifew.bcopay.model.cupon.add_response;
import com.alifew.bcopay.model.cupon.cupon_repositories;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppInfoRepositories {
    private MutableLiveData<AppInfoResponse> appInfoResponse;
    private static  AppInfoRepositories appInfoRepositories;
    private AppInfoApi appInfoApi;

    private AppInfoRepositories(){
        appInfoResponse = new MutableLiveData<>();
        appInfoApi = ApiUtilize.appInfoApi();
    }

    public synchronized static AppInfoRepositories getInstance() {
        if (appInfoRepositories == null) {
            return new AppInfoRepositories();
        }
        return appInfoRepositories;
    }

    public MutableLiveData<AppInfoResponse> getAppInfo() {

        Call<AppInfoResponse> call = appInfoApi.getAppInfo();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AppInfoResponse> call, Response<AppInfoResponse> response) {
                if (response.isSuccessful())
                    appInfoResponse.postValue(response.body());
            }

            @Override
            public void onFailure(Call<AppInfoResponse> call, Throwable throwable) {

            }
        });
        return appInfoResponse;
    }
}
