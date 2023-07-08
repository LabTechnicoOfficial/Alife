package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_admin_all_product_api {
    @GET("fetch_agent_product.php")
    Call<List<Get_product_response>> get_agentproduct(@Query("id") String id);
}
