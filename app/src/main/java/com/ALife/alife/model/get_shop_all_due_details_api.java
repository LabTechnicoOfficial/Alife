package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_shop_all_due_details_api {
    @GET("get_shop_all_due_details.php")
    Call<List<get_shop_all_due_details_response>> get_due_details(@Query("shop_id") String shop_id,@Query("page") int page,@Query("limit") int limit);
}
