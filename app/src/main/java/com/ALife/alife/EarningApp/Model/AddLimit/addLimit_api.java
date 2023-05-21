package com.ALife.alife.EarningApp.Model.AddLimit;

import com.ALife.alife.EarningApp.Model.Login.Login_response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface addLimit_api {
    @GET("get_todays_addCount.php")
    Call<addLimit_response> getResponse(@Query("user_id") String user_id,
                                     @Query("date") String date);
}
