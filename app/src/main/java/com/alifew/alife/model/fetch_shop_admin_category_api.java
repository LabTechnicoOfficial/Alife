package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_shop_admin_category_api {
    @GET("fetch_shop_agent_category.php")
    Call<List<Category_response>> getcategory(@Query("id") String id,@Query("page") int page,@Query("limit") int limit);

}
