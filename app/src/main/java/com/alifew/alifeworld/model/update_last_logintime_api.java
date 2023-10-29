package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_last_logintime_api {
    @FormUrlEncoded
    @POST("update_last_logintime.php")
    Call<update_last_logintime_response> update(@Field("id") String id, @Field("time") String logintime,@Field("type") String user_type);

}
