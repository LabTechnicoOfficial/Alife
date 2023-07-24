package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Shop_registration_api {
    @FormUrlEncoded
    @POST("shop_registration.php")
    Call<registration_response> shop_registration(@Field("name") String name,@Field("owner") String owner, @Field("location") String location,@Field("phone") String phone, @Field("password") String password,@Field("image") String image,@Field("token") String token);

}
