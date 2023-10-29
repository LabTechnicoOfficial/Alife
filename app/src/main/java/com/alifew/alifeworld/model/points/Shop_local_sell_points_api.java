package com.alifew.alifeworld.model.points;

import com.alifew.alifeworld.model.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Shop_local_sell_points_api {
    @GET("get_local_sell_points_for_shop.php")
    Call<List<Shop_local_sell_point_response>> getShopLocalSellPoints(@Query("id") String shopID);


    @FormUrlEncoded
    @POST("add_local_sell_points_for_shop.php")
    Call<CommonResponse> addLocalSellPoint(@Field("shop_id") String shopID,
                                           @Field("amount") String amount,
                                           @Field("points") String points);

    @FormUrlEncoded
    @POST("delete_local_sell_points_for_shop.php")
    Call<CommonResponse> deleteLocalSellPoint(@Field("id") String id);


    @FormUrlEncoded
    @POST("add_local_sell_refer_point.php")
    Call<CommonResponse> addReferralPointForLocalSell(@Field("shop_id") String shopID,
                                                      @Field("amount") String amount,
                                                      @Field("point") String points);

    @GET("get_local_sell_refer_point.php")
    Call<List<Shop_local_sell_point_response>> getShopLocalSellReferPoints(@Query("id") String shopID);


    @FormUrlEncoded
    @POST("delete_local_sell_refer_point.php")
    Call<CommonResponse> deleteLocalSellReferPoint(@Field("id") String id);
}
