package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface edit_category_api {
    @FormUrlEncoded
    @POST("edit_category.php")
    Call<edit_category_response> getdata(@Field("name") String name, @Field("id") String id);


}
