package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface customer_token_update_api {
    @FormUrlEncoded
    @POST("update_customer_token.php")
    Call<token_update_response> update_token(@Field("id") String id, @Field("token") String token);

}
