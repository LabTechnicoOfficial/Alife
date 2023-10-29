package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_shop_all_product_offer_api {
    @FormUrlEncoded
    @POST("add_shop_all_product_offer.php")
    Call<add_shop_all_product_offer_response> getresponse(@Field("id") String shop_id, @Field("amount") String minimum_amount, @Field("price") String minimum_price, @Field("offer_percentage") String offer_percentage);

}
