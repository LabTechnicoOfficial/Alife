package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_normal_product_image_api {

    @FormUrlEncoded
    @POST("add_normal_product_image.php")
    Call<add_normal_product_image_response> add_normal_image(@Field("id") String id, @Field("image") String image);

}