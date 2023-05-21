package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface push_notification_sell_shop_api {
    @GET("push_notification_for_sell_shop.php?")
    Call<push_notification_response> get_notification(@Query("shop_id") String shop_id, @Query("customer_name") String customer_name,@Query("price") String price,@Query("due") String due);


}
