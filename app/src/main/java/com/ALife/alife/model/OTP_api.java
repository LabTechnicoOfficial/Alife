package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OTP_api {

    @GET("otp_send.php")
    Call<OTP_response> otpresponse( @Query("to") String to, @Query("text") String text);
}
