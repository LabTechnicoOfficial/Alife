package com.alifew.alifeworld.model.cupon;

import com.alifew.alifeworld.model.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface cupon_api {

    @FormUrlEncoded
    @POST("add_cupon_package_customer_list.php")
     Call<CommonResponse> addCustomerReferGift(@Field("pkg_id") String packageID,
                                               @Field("phone") String customerPhone,
                                               @Field("amount") String sellAmount,
                                               @Field("point") String points,
                                               @Field("shop_id") int shopID,
                                               @Field("position") String pos,
                                               @Field("gift_menu") String giftName);

    // add cupon api
    @FormUrlEncoded
    @POST("cupon/add_cupon.php")
    Call<add_response> add_cupon(@Field("shop_id") String shop_id,
                                 @Field("cupon_name") String cupon_name,
                                 @Field("time_range") String time_range,
                                 @Field("create_date") String create_date,
                                 @Field("end_date") String end_date,
                                 @Field("description") String description);

    // fetch cupon api

    @GET("cupon/get_cuponlist.php")
    Call<List<cupon_response>> fetch_cupon(@Query("shop_id") String shop_id);

    // add cupon package api
    @FormUrlEncoded
    @POST("cupon/add_package.php")
    Call<add_response> add_package(@Field("cupon_id") String cupon_id, @Field("package_name") String package_name, @Field("packageSellAmount") String packageSellAmount, @Field("winner") String winner, @Field("gift") String gift);

    //fetch cupon package

    //couponID, shopID, phone, createdDate, endDate
    @GET("cupon/get_packagelist.php")
    Call<List<Package_response>> fetch_package(@Query("cupon_id") String couponID,
                                               @Query("shop_id") String shopID,
                                               @Query("customer_phone") String phone,
                                               @Query("date1") String createdDate,
                                               @Query("date2") String endDate);

    //fetch customerFor_cupon
    @GET("cupon/get_customerFor_cupon.php")
    Call<List<CustomerFor_cupon_response>> fetch_cuponCustomer(@Query("shop_id") String shop_id, @Query("date1") String create_date, @Query("date2") String end_date);

    // fetch cupon shop
    @GET("cupon/get_cupon_shopList.php")
    Call<List<cuponShop_response>> fetch_cuponShop(@Query("page") int page, @Query("limit") int limit);

    //delete cupon
    @FormUrlEncoded
    @POST("cupon/delete_cupon.php")
    Call<edit_delete_response> deleteCupon(@Field("shop_id") String shop_id, @Field("cupon_id") String cupon_id);

    // delete cupon package
    @FormUrlEncoded
    @POST("cupon/delete_cupon_package.php")
    Call<edit_delete_response> deletePackage(@Field("package_id") String package_id);

    //update cupon package
    @FormUrlEncoded
    @POST("cupon/update_cupon_package.php")
    Call<edit_delete_response> updatePackage(@Field("package_id") String package_id, @Field("package_name") String package_name, @Field("packageSellAmount") String packageSellAmount);

    //notify
    @GET("cupon/cupon_notification.php")
    Call<notify_response> notifyCoupon(@Query("shop_id") String ID, @Query("message") String message);

    @GET("cupon/push_notification_to_customer.php")
    Call<notify_response> customernotifyCoupon(@Query("customer_id") String customer_id, @Query("message") String message);


    //ishtiak
    @GET("cupon/total_buy_from_shop.php")
    Call<active_cupon> activeCupon(@Query("cupon_id") String cupon_id,
                                   @Query("shop_id") String shop_id,
                                   @Query("customer_phone") String customer_phone,
                                   @Query("date1") String date1, @Query("date2") String date2);


    @GET("cupon/get_customer_list_for_cupon.php")
    Call<List<CustomerFor_cupon_response>> getCustomerListForCoupon(@Query("shop_id") String shopID,
                                                                    @Query("package_id") String packageID);

    @GET("cupon/get_cupon_package_customer_list.php")
    Call<ShopCouponCustomerResponse> getCouponPackageCustomerResultList(@Query("pkg_id") String packageID);


    @FormUrlEncoded
    @POST("delete_cupon_package_customer_list.php")
    Call<CommonResponse> deleteCouponPackageCustomerResultItem(@Field("id") String id);
}
