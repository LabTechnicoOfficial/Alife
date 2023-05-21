package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_product_offer_api {
    @GET("get_product_offer.php")
    Call<List<get_product_offer_response>> getproduct_offer(@Query("id") String id);
}
