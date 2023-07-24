package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface push_notification_sell_customer_api {
    @GET("push_notification_for_sell_customer.php?")
    Call<push_notification_response> get_notification(@Query("shop_id") String shop_id, @Query("customer_id") String customer_id, @Query("price") String price, @Query("due") String due);


}
