package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_sell_details_api {
    @FormUrlEncoded
    @POST("add_sell_details.php")
    Call<add_sell_details_response> add_sell_details(@Field("sell_id") String sell_id, @Field("product_id") String product_id, @Field("type_id") String type_id, @Field("product_amount") String product_amount, @Field("price") String price, @Field("buy_price") String buy_price);

    @FormUrlEncoded
    @POST("add_product_sell_details.php")
    Call<add_sell_details_response> add_product_sell_details(@Field("sell_id") String sell_id, @Field("product_id") String product_id,@Field("product_name") String product_name,@Field("product_image") String product_image, @Field("type_id") String type_id, @Field("product_amount") String product_amount, @Field("price") String price, @Field("buy_price") String buy_price);

}
