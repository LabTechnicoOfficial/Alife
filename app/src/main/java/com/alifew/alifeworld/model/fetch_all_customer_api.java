package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_all_customer_api {
    @GET("fetch_customer.php")
    Call<List<Get_shop_customer_response>> fetch_customer(@Query("value") String value);
}
