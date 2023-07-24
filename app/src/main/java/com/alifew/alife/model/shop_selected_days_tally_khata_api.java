package com.alifew.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_selected_days_tally_khata_api {
    @GET("get_selected_days_tali_khata.php")
    Call<List<shop_tally_khata_response>> get_tally(@Query("shop_id") String shop_id, @Query("date1") String date1,@Query("date2") String date2,@Query("page") int page,@Query("limit") int limit);

}
