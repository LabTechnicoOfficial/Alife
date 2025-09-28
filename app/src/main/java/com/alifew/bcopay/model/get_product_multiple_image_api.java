package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_product_multiple_image_api {
    @GET("fetch_product_image.php")
    Call<List<get_product_multiple_image_response>> getproductimage(@Query("id") String id);
}
