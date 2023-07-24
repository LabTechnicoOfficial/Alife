package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_sell_payment_cash_api {
    @FormUrlEncoded
    @POST("add_payment_cash.php")
    Call<add_sell_payment_cash_response> add_sell_payment_cash(@Field("sell_id") String sell_id,@Field("payment_system") String payment_system, @Field("payment_amount") String payment_amount,@Field("date") String date);

}
