package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_customer_password_api {
    @FormUrlEncoded
    @POST("update_customer_password.php")
    Call<update_password_response> update_password(@Field("id") String id, @Field("password") String password);

}
