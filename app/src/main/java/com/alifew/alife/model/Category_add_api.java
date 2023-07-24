package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Category_add_api {
    @FormUrlEncoded
    @POST("add_catagory.php")
    Call<Category_add_response> add_category(@Field("image") String logo,@Field("name") String name,@Field("unit") String unit, @Field("id") String id);

}
