package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface edit_type_count_api {
    @FormUrlEncoded
    @POST("update_type_count.php")
    Call<edit_type_count_response> edit_type_count(@Field("type") String type, @Field("count") String count, @Field("id") String id);


}
