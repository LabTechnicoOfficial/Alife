package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_product_api4 {
    @FormUrlEncoded
    @POST("update_product4.php")
    Call<update_product_response> update_product(@Field("id") String id, @Field("name") String name, @Field("unit") String unit, @Field("price") String price, @Field("buy_price") String buy_price, @Field("discount") String discount, @Field("price_with_offer") String price_with_offer
            , @Field("sell_profit") String sell_profit, @Field("stock_amount") String stock_amount, @Field("unit_selling_price") String selling_unit_price, @Field("unit_price_with_offer") String unit_price_with_offer,
                                                 @Field("description") String description, @Field("vaoture_no") String vaoture_no, @Field("vaoture_image") String vaoture_image);

}
