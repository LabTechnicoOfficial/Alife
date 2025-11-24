package com.alifew.bcopay.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface systemetic_sell_details_api {
    //@GET("systemetic_sell_details.php")
    @GET("get_systemetic_sell_details.php")
    Call<List<systemetic_sell_details_response>> get_sell_productlist(@Query("sell_id") String sell_id);
}
