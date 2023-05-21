package com.ALife.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_admin_login_api {
    @GET("shop_agent_login.php")
    Call<shop_admin_login_response> getShopadminLogin(@Query("phone") String phone,
                                           @Query("password") String password,@Query("shop_id") String shop_id);
}
