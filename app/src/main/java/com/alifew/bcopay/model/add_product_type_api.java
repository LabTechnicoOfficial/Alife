package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface add_product_type_api {
    @FormUrlEncoded
    @POST("add_product_type.php")
    Call<add_product_type_response> add_product_type(@Field("type") String type,@Field("count") String count,@Field("id") String id);


}
