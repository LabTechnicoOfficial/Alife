package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_product_offer_api {
    @FormUrlEncoded
    @POST("add_product_offer.php")
    Call<add_product_offer_response> add_product_offer(@Field("amount") String type, @Field("price") String count, @Field("id") String id);


}

