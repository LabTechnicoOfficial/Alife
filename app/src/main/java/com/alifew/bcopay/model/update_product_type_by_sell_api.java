package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface update_product_type_by_sell_api {
    @FormUrlEncoded
    @POST("update_product_type_by_sell.php")
    Call<update_product_type_by_sell_response> update_product_type(@Field("count") String type_count, @Field("id") String type_id);

}
