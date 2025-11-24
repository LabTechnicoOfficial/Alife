package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Customer_registration_api {
    @FormUrlEncoded
    @POST("customer_registration.php")
    Call<customer_registration_response> customer_registration(@Field("name") String name, @Field("location") String location, @Field("phone") String phone, @Field("password") String password, @Field("image") String image,@Field("token") String token);

}
