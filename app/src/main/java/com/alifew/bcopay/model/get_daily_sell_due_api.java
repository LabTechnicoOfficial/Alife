package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_daily_sell_due_api {
    @GET("get_daily_due_sell.php")
    Call<List<get_daily_due_sell_response>> get_daily_sell_due(@Query("shop_id") String shop_id,@Query("date") String date);
}
