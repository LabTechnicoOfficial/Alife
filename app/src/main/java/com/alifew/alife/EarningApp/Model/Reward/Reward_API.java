package com.alifew.alife.EarningApp.Model.Reward;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Reward_API {

    @GET("adduserxxxxComission.php")
    Call<Message_response> getReward(@Query("user_id") String userID,
                                     @Query("refferal_id") String refferalID);
}
