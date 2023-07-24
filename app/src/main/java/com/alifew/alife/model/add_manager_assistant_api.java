package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_manager_assistant_api {
    @FormUrlEncoded
    @POST("add_manager_assistant.php")
    Call<add_manager_assistant_response> add_manager_assistant(@Field("manager_id") String manager_id, @Field("assistant_id") String assistant_id);


}
