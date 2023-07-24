package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_category_product_by_search_api {
    @GET("get_category_product_by_search.php")
    Call<List<Get_product_response>> getproduct(@Query("id") String id);
}
