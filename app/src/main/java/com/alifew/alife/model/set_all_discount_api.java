package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface set_all_discount_api {
    @FormUrlEncoded
    @POST("set_all_product_discount.php")
    Call<set_all_discount_response> set_all_discount(@Field("id") String id, @Field("discount") String discount);

}
