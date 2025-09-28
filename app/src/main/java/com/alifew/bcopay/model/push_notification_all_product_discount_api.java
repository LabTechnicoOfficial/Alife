package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface push_notification_all_product_discount_api {
    @GET("send_push_notification_for_all_discount.php")
    Call<push_notification_response> get_notification(@Query("shop_id") String shop_id,@Query("all_discount") String all_discount);

}
