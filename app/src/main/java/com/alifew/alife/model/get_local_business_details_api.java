package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_local_business_details_api {
    @GET("get_local_business_details.php")
    Call<List<get_local_business_details_response>> getdetails(@Query("id") String id);

}
