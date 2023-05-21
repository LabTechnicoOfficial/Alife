package com.ALife.alife.EarningApp.Model.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface transactionHistory_api {
    @GET("withdrow_history.php")
    Call<List<transactionHistory_response>> getResponse(@Query("user_id") String user_id,
                                                        @Query("token") String token);
}
