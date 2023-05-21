package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface remove_shop_customer_api {
    @FormUrlEncoded
    @POST("remove_store_customer.php")
    Call<add_remove_shop_customer_response> remove_customer(@Field("shop_id") String shop, @Field("customer_id") String customer);

}
