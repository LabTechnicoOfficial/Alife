package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface unfollow_customer_shop_api {
    @FormUrlEncoded
    @POST("unfollow_customer_shop.php")
    Call<unfollow_customer_shop_response> unfollow_shop(@Field("shop_id") String shop, @Field("customer_id") String customer);

}
