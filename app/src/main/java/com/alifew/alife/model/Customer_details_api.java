package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Customer_details_api {
    @GET("customer_details.php")
    Call<Customer_response> getdata(@Query("id") String id);
}
