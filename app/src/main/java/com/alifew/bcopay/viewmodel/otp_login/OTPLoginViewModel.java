package com.alifew.bcopay.viewmodel.otp_login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.bcopay.model.otp_login.OTPLoginRepositories;
import com.alifew.bcopay.model.otp_login.OTPLoginResponse;

public class OTPLoginViewModel extends ViewModel {
    public LiveData<OTPLoginResponse> getOtpLogin(String phone){
        return OTPLoginRepositories.getInstance().otpLoginInfo(phone);
    }
}
