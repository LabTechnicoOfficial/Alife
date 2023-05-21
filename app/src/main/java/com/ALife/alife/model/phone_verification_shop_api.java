package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface phone_verification_shop_api {
    @GET("phone_verification_shop.php")
    Call<phone_verification_response> verification(@Query("phone") String phone);
}
