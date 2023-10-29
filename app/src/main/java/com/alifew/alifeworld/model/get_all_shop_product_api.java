package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_all_shop_product_api {
    @GET("fetch_all_product.php")
    Call<List<Get_product_response>> getproduct(@Query("id") String id);
}
