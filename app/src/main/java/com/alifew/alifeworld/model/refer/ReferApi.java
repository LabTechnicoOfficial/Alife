package com.alifew.alifeworld.model.refer;

import com.alifew.alifeworld.model.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReferApi {
    @GET("get_shop_refer_list.php")
    Call<List<ReferResponse>> getReferList(@Query("id") String shopID);


    @FormUrlEncoded
    @POST("shop_add_refer.php")
    Call<CommonResponse> addRefer(@Field("shop_id") String shopID,
                                  @Field("name") String name,
                                  @Field("start_at") String createdAt,
                                  @Field("end_at") String endAt,
                                  @Field("description") String description);

    @GET("shop_refer_delete.php")
    Call<CommonResponse> deleteRefer(@Query("id") String referID);


    @FormUrlEncoded
    @POST("refer_package_add.php")
    Call<CommonResponse> addReferPackage(@Field("shop_id") String shopID,
                                         @Field("refer_id") String referID,
                                         @Field("title") String name,
                                         @Field("min_refer_package_point") String minReferPackagePoint,
                                         @Field("winner_amount") String winnerAmount,
                                         @Field("gift") String giftName);

    @GET("get_refer_package_list.php")
    Call<List<ReferPackageResponse>> getReferPackageList(@Query("refer_id") String referID);

    @FormUrlEncoded
    @POST("refer_package_delete.php")
    Call<CommonResponse> deleteReferPackage(@Field("id") String id);

    @GET("shop_refer_customer_list.php")
    Call<List<ReferPackageCustomerResponse>> getReferPackageCustomer(@Query("shop_id") String shopID,
                                                                     @Query("package_id") String packageID);


    @FormUrlEncoded
    @POST("add_refer_point_gift_customer.php")
    Call<CommonResponse> addCustomerReferGift(@Field("refer_package_id") String referPackageID,
                                              @Field("shop_id") String shopID,
                                              @Field("phone") String phone,
                                              @Field("point") String points,
                                              @Field("position") String position,
                                              @Field("gift_name") String giftName);


    @GET("get_refer_point_gift_customer_list.php")
    Call<ReferResultCustomerResponse> getResultCustomerList(@Query("refer_package_id") String referPackageID);

    //delete_refer_point_gift_customer.php
    @FormUrlEncoded
    @POST("delete_refer_point_gift_customer.php")
    Call<CommonResponse> deleteReferCustomerResult(@Field("id") String id);


    @FormUrlEncoded
    @POST("update_refer_point_gift_customer_list.php")
    Call<CommonResponse> updateReferCustomerResultStatus(@Field("id") String id,@Field("status") String status);
}
