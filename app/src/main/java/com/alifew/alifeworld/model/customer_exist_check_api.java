package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface customer_exist_check_api {
    @GET("customer_exist_check.php")
    Call<customer_exist_check_response> customer_exist_check(@Query("phone") String phone);
}
