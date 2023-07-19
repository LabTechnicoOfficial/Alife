package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_selected_days_sell_details_api {
    @GET("get_selected_days_sell_history.php")
    Call<get_sell_details_response> sell_summary(@Query("shop_id") String shop_id, @Query("date1") String date, @Query("date2") String date2);

}
