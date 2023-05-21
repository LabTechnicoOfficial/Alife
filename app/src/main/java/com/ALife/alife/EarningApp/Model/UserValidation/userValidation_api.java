package com.ALife.alife.EarningApp.Model.UserValidation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface userValidation_api {
    @GET("userPhoneValidation.php")
    Call<userValidation_response> getResponse(@Query("phone") String phone,
                                     @Query("token") String token);
}
