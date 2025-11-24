package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_status_api {
    @GET("get_shop_status.php")
    Call<shop_status_response> getStatus(@Query("id")String shop_id);
}
