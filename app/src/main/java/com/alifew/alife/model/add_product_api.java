package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_product_api {
    @FormUrlEncoded
    @POST("add_product.php")
    Call<add_product_response> add_product(@Field("name") String name, @Field("id1") String id1, @Field("id2") String id2, @Field("unit") String unit, @Field("price") String price, @Field("discount") String discount, @Field("buy_price") String buy_price, @Field("sell_profit") String sell_profit, @Field("stock_amount") String stock_amount, @Field("image") String image, @Field("description") String description, @Field("vaoture_no") String vaoture_no, @Field("vaoture_image") String vaoture_image, @Field("brand") String vaoture_brand, @Field("product_code") String productCode, @Field("added_by") String added_by);


}
