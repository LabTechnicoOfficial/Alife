package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_shop_admin_category_summary_api {
    @GET("fetch_shop_agent_category_summary.php")
    Call<get_shop_products_summary_response> get(@Query("shop_id") String shop_id,@Query("agent_id") String agent_id);
}
