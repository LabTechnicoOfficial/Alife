package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Shop_details_api {
    @GET("shop_details.php")
    Call<Shop_response> getdata(@Query("id") String id);
}
