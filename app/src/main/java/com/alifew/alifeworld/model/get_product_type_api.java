package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_product_type_api {
    @GET("get_product_type.php")
    Call<List<get_product_type_response>> getproduct_type(@Query("id") String id);
}
