package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_local_business_subtitle_api {
    @FormUrlEncoded
    @POST("add_local_business_subtitle.php")
    Call<add_local_business_response> add(@Field("subtitle") String subtitle, @Field("id") String title_id);


}
