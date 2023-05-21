package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Customer_login_api {
    @GET("customer_login.php")
    Call<Shop_login_response> getCustomerLogin(@Query("phone") String phone,
                                           @Query("password") String password);
}
