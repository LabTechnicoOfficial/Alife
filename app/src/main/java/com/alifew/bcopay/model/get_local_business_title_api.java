package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_local_business_title_api {
    @GET("get_local_business_title.php")
    Call<List<get_local_business_title_response>> gettitle(@Query("id") String id, @Query("page") int page, @Query("limit") int limit);

}
