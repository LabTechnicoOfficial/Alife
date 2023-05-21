package com.ALife.alife.EarningApp.Model.CashOut;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface pending_cashOut_api {
    @GET("pending_withdrow_request.php")
    Call<List<pending_cashOut_response>> getResponse(@Query("user_id") String user_id,
                                                     @Query("token") String token);
}
