package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_daily_sell_details_api {
    @GET("get_daily_sell_history.php")
    Call<get_sell_details_response> get_daily_sell_summary(@Query("shop_id") String shop_id, @Query("date") String date);
}
