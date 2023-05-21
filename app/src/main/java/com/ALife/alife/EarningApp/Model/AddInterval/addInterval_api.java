package com.ALife.alife.EarningApp.Model.AddInterval;

import com.ALife.alife.EarningApp.Model.AddLimit.addLimit_response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface addInterval_api {
    @GET("get_addIntervalTime.php")
    Call<addInterval_response> getResponse(@Query("user_id") String user_id,
                                        @Query("date") String date,@Query("key") int key);
}
