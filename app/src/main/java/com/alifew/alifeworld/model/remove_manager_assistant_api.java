package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface remove_manager_assistant_api {
    @FormUrlEncoded
    @POST("remove_manager_assistant.php")
    Call<remove_manager_assistant_response> remove_assistant(@Field("manager_id") String manager_id, @Field("assistant_id") String assistant_id);

}
