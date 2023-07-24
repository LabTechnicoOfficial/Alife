package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_profile_api {
    @GET("shop_profile.php")
    Call<shop_profile_response> getdata(@Query("id") String id);
}
