package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Shop_login_api {
    @GET("shop_login.php")
    Call<Shop_login_response> getShopLogin(@Query("phone") String phone,
                                    @Query("password") String password);
}
