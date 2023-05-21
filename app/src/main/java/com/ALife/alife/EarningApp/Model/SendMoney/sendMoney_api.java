package com.ALife.alife.EarningApp.Model.SendMoney;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface sendMoney_api {
    @FormUrlEncoded
    @POST("send_money.php")
    Call<sendMoney_response> sendMoney(@Field("toUser_id") String touser_id,
                                       @Field("toUser_name") String toUser_name,
                                       @Field("toUser_phone") String toUser_phone,
                                       @Field("fromUser_id") String fromuser_id,
                                       @Field("fromUser_name") String fromUser_name,
                                       @Field("fromUser_phone") String fromUser_phone,
                                       @Field("amount") String amount,
                                       @Field("date") String date,
                                       @Field("toUser_balance") String toUser_balance,
                                       @Field("fromUser_balance") String fromUser_balance            );
}
