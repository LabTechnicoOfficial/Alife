package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_local_business_title_api {
    @FormUrlEncoded
    @POST("add_local_business_title1.php")
    Call<add_local_business_response> add(@Field("title") String title, @Field("id") String shop_id);


}
