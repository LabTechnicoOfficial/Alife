package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface follow_customer_shop_api {
    @FormUrlEncoded
    @POST("add_customer_store.php")
    Call<follow_customer_shop_response> follow_shop(@Field("shop_id") String shop, @Field("customer_id") String customer);

}
