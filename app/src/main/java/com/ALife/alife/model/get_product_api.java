package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_product_api {
    @GET("get_product.php")
    Call<List<get_product_response>> getproduct(@Query("id") String id,@Query("page") int page,@Query("limit") int limit);
}
