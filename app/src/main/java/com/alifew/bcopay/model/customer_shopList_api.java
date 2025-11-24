package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface customer_shopList_api {
    @GET("customer_shop_list.php")
    Call<List<customer_shopList_response>> getshop(@Query("id") String id,@Query("page")int page,@Query("limit") int limit);
}
