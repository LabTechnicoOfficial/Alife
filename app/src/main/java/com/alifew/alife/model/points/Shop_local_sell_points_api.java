package com.alifew.alife.model.points;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Shop_local_sell_points_api {
    @GET("get_local_sell_points_for_shop.php")
    Call<List<Shop_local_sell_point_response>> getShopLocalSellPoints(@Query("id") String shopID);
}
