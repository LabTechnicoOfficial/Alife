package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_shop_admin_api {
    @FormUrlEncoded
    @POST("add_shop_agent.php")
    Call<add_shop_admin_response> add_shop_admin(@Field("name") String name, @Field("phone") String phone, @Field("password") String password, @Field("image") String image, @Field("store_id") String store_id,@Field("access") String access);

}
