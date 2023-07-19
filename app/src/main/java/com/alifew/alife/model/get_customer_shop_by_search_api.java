package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_customer_shop_by_search_api {
    @GET("fetch_shop_customer_by_search.php")
    Call<List<customer_shopList_response>> getshop(@Query("id") String id, @Query("search") String search);
}
