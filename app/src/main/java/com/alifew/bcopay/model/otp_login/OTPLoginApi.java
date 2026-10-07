package com.alifew.bcopay.model.otp_login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OTPLoginApi {
    @GET("login_with_otp.php")
    Call<OTPLoginResponse> loginWithApi(@Query("phone") String phone);
}
