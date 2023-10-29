package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_shop_api {
    @GET("fetch_shop.php")
    Call<List<fetch_shop_response>> fetch_shop(@Query("value") String value);
}
