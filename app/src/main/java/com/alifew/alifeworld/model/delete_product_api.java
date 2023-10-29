package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface delete_product_api {
    @FormUrlEncoded
    @POST("delete_product.php")
    Call<delete_category_response> getdata(@Field("id") String id);


}
