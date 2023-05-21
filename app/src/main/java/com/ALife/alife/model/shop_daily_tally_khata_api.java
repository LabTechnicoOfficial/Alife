package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_daily_tally_khata_api {
    @GET("get_daily_Tali_Khata.php")
    Call<List<shop_tally_khata_response>> get_daily_sell_list(@Query("shop_id") String shop_id, @Query("date") String date,@Query("page") int page,@Query("limit") int limit);

}
