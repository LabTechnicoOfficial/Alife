package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Imagetoserver_api {
    @FormUrlEncoded
    @POST("add_product_image.php")
    Call<Imagetoserver_response> imagetoserver(@Field("image") String image, @Field("id") String id);

}
