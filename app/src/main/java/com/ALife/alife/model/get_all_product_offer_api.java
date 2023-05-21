package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_all_product_offer_api {
    @GET("get_product_offerList_on_all_product.php")
    Call<List<get_all_product_offer_response>> getAllproduct_offer(@Query("id") String id);
}
