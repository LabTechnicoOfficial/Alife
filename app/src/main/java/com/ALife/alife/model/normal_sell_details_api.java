package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface normal_sell_details_api {
    @GET("normal_sell_details.php")
    Call<normal_sell_details_response> get_normal_sell_details(@Query("sell_id") String sell_id);
}
