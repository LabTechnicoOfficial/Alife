package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface payment_method_api {
    @GET("get_payment_method.php")
    Call<List<payment_method_response>> payment_response(@Query("token") String token);
}
