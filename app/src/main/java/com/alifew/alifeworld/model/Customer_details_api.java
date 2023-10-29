package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Customer_details_api {
    @GET("customer_details.php")
    Call<Customer_response> getdata(@Query("id") String id);

    @FormUrlEncoded
    @POST("add_referral_code.php")
    Call<CommonResponse> addReferCode(@Field("id") String userID,
                                      @Field("referral_code") String referCode);
}
