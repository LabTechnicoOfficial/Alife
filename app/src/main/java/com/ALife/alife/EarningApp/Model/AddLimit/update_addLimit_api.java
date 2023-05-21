package com.ALife.alife.EarningApp.Model.AddLimit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface update_addLimit_api {
    @GET("update_addLimit.php")
    Call<update_addLimit_response> getResponse(@Query("user_id") String user_id);
}
