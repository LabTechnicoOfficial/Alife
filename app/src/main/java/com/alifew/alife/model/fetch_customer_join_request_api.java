package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_customer_join_request_api {
    @GET("fetch_customer_join_request.php")
    Call<List<get_shop_customer_response>> getcustomer(@Query("id") String id);
}
