package com.alifew.alifeworld.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface delete_shop_all_product_offer_api {
    @FormUrlEncoded
    @POST("delete_shop_all_product_offer.php")
    Call<delete_shop_all_product_offer_response> getdata(@Field("id") String id);
}
