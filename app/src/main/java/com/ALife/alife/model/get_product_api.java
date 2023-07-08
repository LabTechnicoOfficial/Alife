package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_product_api {
    @GET("get_product.php")
    Call<List<Get_product_response>> getproduct(@Query("id") String id, @Query("page") int page, @Query("limit") int limit);

    @GET("fetch_product_detail_by_qrcode.php")
    Call<Fetch_product_detail_by_bar_code_response> fetch_product_detail_by_qrcode(@Query("product_shop_id") String shopID,
                                                                                   @Query("code") String barCode);
}
