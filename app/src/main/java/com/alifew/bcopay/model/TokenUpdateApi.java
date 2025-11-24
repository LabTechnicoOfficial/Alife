package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenUpdateApi {
    @FormUrlEncoded
    @POST("update_token_for_customer_and_shop.php")
    Call<CommonResponse> newTokenUpdate(@Field("id") String id,
                                                  @Field("token") String token,
                                                  @Field("type") String type);
}
