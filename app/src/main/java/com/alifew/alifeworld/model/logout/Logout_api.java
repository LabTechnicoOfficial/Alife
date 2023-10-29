package com.alifew.alifeworld.model.logout;

import com.alifew.alifeworld.model.CommonResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Logout_api {


    @FormUrlEncoded
    @POST("customer_logout.php")
    Call<CommonResponse> customerLogout(@Field("id") String id);

    @FormUrlEncoded
    @POST("shop_logout.php")
    Call<CommonResponse> shopLogout(@Field("id") String id);
}
