package com.alifew.bcopay.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_version_api {
    @GET("get_version.php")
    Call<get_version_response> getproduct(@Query("token") int token);
}
