package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface accept_shop_join_request_api {
    @FormUrlEncoded
    @POST("accept_shop_join_request.php")
    Call<accept_cancle_shop_join_request_response> accept_shop(@Field("customer_id") String customer, @Field("shop_id") String shop);

}
