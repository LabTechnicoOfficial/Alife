package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_shop_customer_manually_api {
    @FormUrlEncoded
    @POST("add_shop_customer_manually.php")
    Call<add_remove_shop_customer_response> add_customer(@Field("shop_id") String shop, @Field("customer_id") String customer);

}
