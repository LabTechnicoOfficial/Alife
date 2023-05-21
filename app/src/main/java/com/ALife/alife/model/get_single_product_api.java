package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_single_product_api {
    @GET("get_single_product.php")
    Call<get_product_response> getproduct(@Query("id") String id);
}
