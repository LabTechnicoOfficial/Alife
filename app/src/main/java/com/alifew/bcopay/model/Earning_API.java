package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Earning_API {

    @GET("get_earning_user_id.php")
    Call<Earning_response> getEarningID(@Query("user_id") String user_id,
                                        @Query("type") String type);
}
