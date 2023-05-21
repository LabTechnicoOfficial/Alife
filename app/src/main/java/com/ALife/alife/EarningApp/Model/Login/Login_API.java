package com.ALife.alife.EarningApp.Model.Login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Login_API {
    @GET("login.php")
    Call<Login_response> getResponse(@Query("phone") String phone,
                                     @Query("password") String password);
}
