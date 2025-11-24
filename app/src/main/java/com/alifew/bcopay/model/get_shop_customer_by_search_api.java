package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_customer_by_search_api {
    @GET("fetch_shop_customer_by_search.php")
    Call<List<Get_shop_customer_response>> getcustomer(@Query("id") String id, @Query("search") String search);
}
