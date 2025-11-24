package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_category_summary_api {
    @GET("get_shop_category_summary.php")
    Call<get_shop_products_summary_response> get(@Query("id") String shop_id);
}
