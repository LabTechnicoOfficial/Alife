package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface fetch_shop_adminList_api {
    @GET("get_shop_agentList.php")
    Call<List<fetch_shop_admin_response>> fetch_shop_admin(@Query("id") String shop_id);
}
