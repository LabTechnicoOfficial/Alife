package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_daily_sell_cash_api {
    @GET("get_daily_cash_sell.php")
    Call<List<get_daily_sell_cash_response>> get_daily_sell_cash(@Query("shop_id") String shop_id,@Query("date") String date);
}
