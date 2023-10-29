package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface addMessageApi {
    @FormUrlEncoded
    @POST("addMessagetohistory.php")
    Call<addMessageResponse> add_message(@Field("shop_id") String shop_id, @Field("shop_name") String shopName
            , @Field("shop_phone") String shopPhone, @Field("customer_id") String customer_id
            , @Field("customer_name") String customerName, @Field("customer_phone") String customerPhone
            , @Field("message") String message);

}
