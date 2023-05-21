package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_shop_customer_record_api {
    @FormUrlEncoded
    @POST("update_shop_customer_record.php")
    Call<update_shop_customer_record_response> update_record(@Field("customer_id") String id, @Field("customer_phone") String phone);

}
