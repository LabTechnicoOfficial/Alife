package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface delete_product_offer_api {
    @FormUrlEncoded
    @POST("delete_product_offer.php")
    Call<product_offer_edit_delete_response> delete_product_offer( @Field("id") String id);

}
