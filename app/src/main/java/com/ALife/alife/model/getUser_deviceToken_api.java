package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface getUser_deviceToken_api {
    @GET("get_user_device_token.php?")
    Call<getUser_deviceToken_response> getToken(@Query("id") String user_id,@Query("type") String user_type);
    @GET("get_user_device_token.php?")
    Call<getUser_deviceToken_response> getMessage(@Query("id") String user_id,@Query("type") String user_type,@Query("token") String token);
}
