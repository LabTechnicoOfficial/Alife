package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface delete_type_count_api {
    @FormUrlEncoded
    @POST("delete_type_count.php")
    Call<delete_type_count_response> getdata(@Field("id") String id);


}
