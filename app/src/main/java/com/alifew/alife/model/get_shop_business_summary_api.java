package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_business_summary_api {
    @GET("get_shop_business_summary_list.php")
    Call<List<get_shop_business_summary_response>> getresponse(@Query("id") String id,@Query("page") int page,@Query("limit") int limit);

}
