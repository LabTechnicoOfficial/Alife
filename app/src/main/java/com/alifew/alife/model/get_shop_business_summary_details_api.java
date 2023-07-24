package com.alifew.alife.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_business_summary_details_api {
    @GET("get_shop_business_summary_details.php")
    Call<get_shop_business_summary_details_response> getresponse(@Query("id") String id);

}
