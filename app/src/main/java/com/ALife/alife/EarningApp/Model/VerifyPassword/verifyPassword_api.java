package com.ALife.alife.EarningApp.Model.VerifyPassword;

import com.ALife.alife.EarningApp.Model.Team.Team_response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface verifyPassword_api {
    @GET("verify_user_password.php")
    Call<verifyPassword_response> getResponse(@Query("id") String id,@Query("password") String password,@Query("type") String type);
}
