package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_operator_all_product_summary_api {
    @GET("get_agent_all_product_accounting.php")
    Call<get_shop_products_summary_response> get(@Query("shop_id") String shop_id,@Query("agent_id") String agent_id);
}
