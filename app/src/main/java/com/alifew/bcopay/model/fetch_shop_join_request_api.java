package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_shop_join_request_api {
    @GET("fetch_shop_join_request.php")
    Call<List<fetch_shop_response>> getShop(@Query("id") String id);
}
