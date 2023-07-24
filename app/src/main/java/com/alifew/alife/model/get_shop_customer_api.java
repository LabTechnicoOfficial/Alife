package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_customer_api {
    @GET("fetch_shop_customer.php")
    Call<List<get_shop_customer_response>> getcustomer(@Query("id") String id,@Query("page") int page,@Query("limit") int limit);
}
