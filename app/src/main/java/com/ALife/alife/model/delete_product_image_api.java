package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface delete_product_image_api {
    @FormUrlEncoded
    @POST("delete_product_image.php")
    Call<delete_product_image_response> getdata(@Field("id") String id);

}
