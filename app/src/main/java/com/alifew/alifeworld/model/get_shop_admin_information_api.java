package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_admin_information_api {
    @GET("get_shop_agent_information.php")
    Call<get_shop_admin_information_response> get_shop_admin(@Query("agent_id") String agent_id_id);
}
