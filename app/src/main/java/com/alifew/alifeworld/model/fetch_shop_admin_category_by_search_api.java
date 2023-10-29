package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_shop_admin_category_by_search_api {
    @GET("fetch_shop_agent_category_by_search.php")
    Call<List<Category_response>> getcategory(@Query("id") String id, @Query("search") String search );

}
