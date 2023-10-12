package com.alifew.alife.model.refer;

import com.alifew.alife.model.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReferApi {
    @GET("get_shop_refer_list.php")
    Call<List<ReferResponse>> getReferList(@Query("id") String shopID);


    @FormUrlEncoded
    @POST("shop_add_refer.php")
    Call<CommonResponse> addRefer(@Query("shop_id") String shopID,
                                  @Query("name") String couponName,
                                  @Query("start_at") String createdAt,
                                  @Query("end_at") String endAt,
                                  @Query("description") String description);

    @GET("shop_refer_delete.php")
    Call<CommonResponse> deleteRefer(@Query("id") String referID);
}
