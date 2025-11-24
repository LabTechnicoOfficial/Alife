package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_shop_admin_status_api {
    @FormUrlEncoded
    @POST("active_inactive_shop_agent.php")
    Call<update_shop_admin_status_response> update_shop_admin_status(@Field("id") String id, @Field("value") String value);

}
