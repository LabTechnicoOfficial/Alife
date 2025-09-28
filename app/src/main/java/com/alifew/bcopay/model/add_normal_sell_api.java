package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_normal_sell_api {
    @FormUrlEncoded
    @POST("add_normal_product_sell.php")
    Call<add_normal_sell_response> add_normal_sell(@Field("sell_id") String sell_id, @Field("description") String description);

}
