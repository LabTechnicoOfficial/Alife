package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface customer_profile_api {
    @GET("customer_profile.php")
    Call<customer_profile_response> getdata(@Query("id") String id);
}
