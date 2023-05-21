package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_sub_shop_api {
    @GET("fetch_sub_shop.php")
    Call<List<fetch_sub_shop_response>> fetch_sub_shop(@Query("id") String id);
}
