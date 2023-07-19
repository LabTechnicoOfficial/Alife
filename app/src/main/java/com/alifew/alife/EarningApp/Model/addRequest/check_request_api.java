package com.alifew.alife.EarningApp.Model.addRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface check_request_api {
    @GET("get_add_active_request.php")
    Call<add_request_response> getResponse(@Query("user_id") String user_id);
}
