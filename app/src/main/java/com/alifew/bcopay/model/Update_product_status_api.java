package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Update_product_status_api {
    @FormUrlEncoded
    @POST("update_product_status.php")
    Call<Update_product_status_response> update_product_status(@Field("id") String id, @Field("value") String value);

}
