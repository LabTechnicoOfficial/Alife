package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_payment_transaction_api {
    @FormUrlEncoded
    @POST("add_payment_transaction_new.php")
    //@POST("test.php")
    Call<add_payment_transaction_response> add_sell_payment_due(@Field("sell_id") String sell_id,@Field("shop_id") String shop_id,@Field("customer_id") String customer_id,@Field("customer_phone") String customer_phone, @Field("transaction_type") String transaction_type, @Field("payment_amount") String payment_amount,@Field("method") String payment_method, @Field("date") String date);

}
