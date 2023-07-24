package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_shop_due_customer_api {
    @FormUrlEncoded
    @POST("add_shop_due_customer.php")
    Call<add_shop_due_customer_response> add_shop_due_customer(@Field("shop_id") String shop_id, @Field("customer_id") String customer_id, @Field("phone") String phone);

}
