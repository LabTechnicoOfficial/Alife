package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_all_product_discount_api {
    @GET("get_all_product_discount.php")
    Call<get_all_product_discount_response> getallproduct(@Query("id") String shop_id);

}
