package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_product_sell_api {
    @FormUrlEncoded
    @POST("add_product_sell.php")
    Call<add_product_sell_response> add_product_sell(@Field("shop_id") String shop_id, 
                                                     @Field("customer_id") String customer_id, 
                                                     @Field("customer_name") String customer_name,
                                                     @Field("customer_phone") String customer_phone,
                                                     @Field("price") String price,
                                                     @Field("buy_price") String buy_price,
                                                     @Field("selled_by") String selled_by,
                                                     @Field("sell_type") String sell_type,
                                                     @Field("date") String date,
                                                     @Field("time") String time,
                                                     @Field("due_price") String duePrice,
                                                     @Field("points") String points,
                                                     @Field("due_check") Boolean dueCheck);


}
