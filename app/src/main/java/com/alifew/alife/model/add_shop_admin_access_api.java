package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_shop_admin_access_api {
    @FormUrlEncoded
    @POST("add_agent_access.php")
    Call<add_shop_admin_access_response> add_admin_access(@Field("agent_id") String agent, @Field("category_id") String category);

}
