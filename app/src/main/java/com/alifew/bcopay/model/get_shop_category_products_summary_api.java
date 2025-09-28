package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_category_products_summary_api {
    @GET("get_product_accounting_category.php")
    Call<get_shop_products_summary_response> get(@Query("id") String id,@Query("shop_id") String shop_id);
}
