package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Unit_api {
    @GET("fetch_unit.php")
    Call<List<Unit_response>> getUnit(@Query("value")String value);
}
