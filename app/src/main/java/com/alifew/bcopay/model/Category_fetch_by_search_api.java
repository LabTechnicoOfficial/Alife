package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Category_fetch_by_search_api {
    @GET("fetch_category_by_search.php")
    Call<List<Category_response>> getdata(@Query("id") String id,@Query("search") String search);
}
