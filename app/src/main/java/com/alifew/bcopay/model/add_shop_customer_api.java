package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_shop_customer_api {
    @FormUrlEncoded
    @POST("add_store_customer.php")
    Call<add_remove_shop_customer_response> add_customer(@Field("shop_id") String shop, @Field("customer_id") String customer);

}
