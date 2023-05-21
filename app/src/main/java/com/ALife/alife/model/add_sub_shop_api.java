package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_sub_shop_api {
    @FormUrlEncoded
    @POST("add_sub_shop.php")
    Call<add_sub_shop_response> add_sub_shop(@Field("parent_id") String parent, @Field("child_id") String child);

}
