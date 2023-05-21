package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_all_shop_product_by_search_api {
    @GET("fetch_all_product_by_search.php")
    Call<List<get_product_response>> get_allproduct(@Query("id") String id);
}
