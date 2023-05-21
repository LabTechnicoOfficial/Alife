package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface delete_shop_admin_api {
    @FormUrlEncoded
    @POST("delete_shop_agent.php")
    Call<delete_shop_admin_response> getdata(@Field("id") String id);


}
