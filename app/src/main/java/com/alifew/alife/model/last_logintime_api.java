package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface last_logintime_api {
    @GET("get_last_logintime.php")
    Call<last_logintime_response> getresponse(@Query("id") String user_id,@Query("type") String type);
}
