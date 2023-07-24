package com.alifew.alife.model.shop_notification;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface shop_notification_api {
    @FormUrlEncoded
    @POST("locall_sell/add_shop_notifications.php")
    Call<shop_notification_response> add_shop_notification(@Field("shop_id") String shop_id, @Field("message") String message);

}
