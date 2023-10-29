package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Category_fetch_api {
    @GET("fetch_catagory.php")
    Call<List<Category_response>> getdata(@Query("id") String id,@Query("page") int page,@Query("limit") int limit);
}
