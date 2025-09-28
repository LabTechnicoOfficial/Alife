package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_business_summary_image_api {
    @FormUrlEncoded
    @POST("add_shop_business_summary_image.php")
    Call<add_business_summary_image_response> add_summary_image(@Field("id") String id, @Field("image") String image);

}
