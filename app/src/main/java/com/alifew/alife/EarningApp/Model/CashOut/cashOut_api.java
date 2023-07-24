package com.alifew.alife.EarningApp.Model.CashOut;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface cashOut_api {
    @FormUrlEncoded
    @POST("withdrow_request.php")
    Call<cashOut_request_response> request(@Field("user_id") String user_id,
                                           @Field("user_name") String user_name,
                                           @Field("number") String phone,
                                           @Field("amount") String amount,
                                           @Field("balance") String balance,
                                           @Field("date") String date,
                                           @Field("method") String method);

    @GET("comission_rate_withdraw.php")
    Call<Commission_response> getCommission();

    @GET("update_admin_balancexxx.php")
    Call<Message_response> getMessage(@Query("balance") String balance);

    @GET("transaction_method_show.php")
    Call<List<Method_response>> getMethod();

    @GET("transaction_amount_show.php")
    Call<List<AddAmount_response>> getAmount();
}
