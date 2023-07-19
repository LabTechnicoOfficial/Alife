package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_shop_business_summary_api {
    @FormUrlEncoded
    @POST("add_shop_business_summary.php")
    Call<add_shop_business_summary_response> getresponse(@Field("id") String shop_id,@Field("description") String description, @Field("credit_in") String credit_in, @Field("credit_out") String credit_out, @Field("date") String date, @Field("time") String time, @Field("image") String image);

}
