package com.alifew.alifeworld.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_all_product_offer_api {
    @GET("get_shop_all_product_offer.php")
    Call<List<get_shop_all_product_offer_response>> get_response(@Query("id") String id);
}
