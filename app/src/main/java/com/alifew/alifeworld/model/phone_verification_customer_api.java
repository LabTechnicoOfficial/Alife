package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface phone_verification_customer_api {
    @GET("phone_verification_customer.php")
    Call<phone_verification_response> verification(@Query("phone") String phone);
}

