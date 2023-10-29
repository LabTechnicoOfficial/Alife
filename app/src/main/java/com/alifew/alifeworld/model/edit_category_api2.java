package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface edit_category_api2 {
    @FormUrlEncoded
    @POST("edit_category2.php")
    Call<edit_category_response> getdata(@Field("image") String logo,@Field("name") String name, @Field("id") String id);

}
