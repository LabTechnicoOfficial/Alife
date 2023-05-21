package com.ALife.alife.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface shop_all_tally_khata_api {
    @GET("get_shop_total_tali_khata.php")
    Call<List<shop_tally_khata_response>> get_tally(@Query("shop_id") String shop_id,@Query("page") int page,@Query("limit") int limit);

}
