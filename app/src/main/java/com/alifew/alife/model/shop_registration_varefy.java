package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_registration_varefy {
    @GET("signup_shop.php")
    Call<varefy_response> getvarefy(@Query("phone") String phone);
}
