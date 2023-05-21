package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_local_business_details_api {
    @FormUrlEncoded
    @POST("add_local_business_details.php")
    Call<add_local_business_response> add(@Field("details") String details,@Field("amount") String amount,@Field("price") String price, @Field("id") String shop_id);


}
