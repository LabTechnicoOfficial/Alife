package com.alifew.bcopay.model.otp_login;

import androidx.lifecycle.MutableLiveData;

import com.alifew.bcopay.API.ApiUtilize;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPLoginRepositories {
    private MutableLiveData<OTPLoginResponse> otpLoginResponse;
    private OTPLoginApi otpLoginApi;
    private static OTPLoginRepositories otpLoginRepositories;

    private OTPLoginRepositories(){
        otpLoginResponse = new MutableLiveData<>();
        otpLoginApi = ApiUtilize.otpLoginApi();
    }

    public synchronized static OTPLoginRepositories getInstance() {
        if (otpLoginRepositories == null) {
            return new OTPLoginRepositories();
        }
        return otpLoginRepositories;
    }

    public MutableLiveData<OTPLoginResponse> otpLoginInfo(String phone) {

        Call<OTPLoginResponse> call = otpLoginApi.loginWithApi(phone);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<OTPLoginResponse> call, Response<OTPLoginResponse> response) {
                if (response.isSuccessful())
                    otpLoginResponse.postValue(response.body());
            }

            @Override
            public void onFailure(Call<OTPLoginResponse> call, Throwable throwable) {

            }
        });
        return otpLoginResponse;
    }
}
