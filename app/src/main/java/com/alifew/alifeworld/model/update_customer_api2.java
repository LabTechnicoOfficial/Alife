package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_customer_api2 {
    @FormUrlEncoded
    @POST("update_customer2.php")
    Call<update_customer_response> update_customer(@Field("id") String id, @Field("name") String name, @Field("location") String location);

}
