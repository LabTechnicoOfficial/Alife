package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_shop_password_api {
    @FormUrlEncoded
    @POST("update_shop_password.php")
    Call<update_password_response> update_password(@Field("id") String id, @Field("password") String password);

}
