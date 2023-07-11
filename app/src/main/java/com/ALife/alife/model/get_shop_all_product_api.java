package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_all_product_api {
    @GET("fetch_all_product.php")
    Call<List<Get_product_response>> get_allproduct(@Query("id") String id, @Query("page") int page, @Query("limit") int limit);

    @GET("fetch_all_product_without_pagination.php")
    Call<List<Get_product_response>> getAllProductWithOutPagination(@Query("id") String id);

}

