package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Customer_registration_varefy {
    @GET("signup_customer.php")
    Call<varefy_response> getvarefy(@Query("phone") String phone);
}
