package com.alifew.alife.model.refer;

import com.alifew.alife.model.CommonResponse;

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
                                         @Field("min_amount") String packageAmount,
                                         @Field("winner_amount") String winnerAmount,
                                         @Field("gift") String giftName);

    @GET("get_refer_package_list.php")
    Call<List<ReferPackageResponse>> getReferPackageList(@Query("refer_id") String referID);
}
