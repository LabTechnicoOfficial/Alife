package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface product_offer_edit_api {
    @FormUrlEncoded
    @POST("edit_product_offer.php")
    Call<product_offer_edit_delete_response> edit_product_offer(@Field("amount") String type, @Field("percentage") String count, @Field("id") String id);

}
