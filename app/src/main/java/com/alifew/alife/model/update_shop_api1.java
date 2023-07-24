package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_shop_api1 {
    @FormUrlEncoded
    @POST("update_shop1.php")
    Call<update_shop_response> update_shop(@Field("id") String id, @Field("name") String name, @Field("owner") String owner, @Field("location") String location, @Field("image") String image);

}
