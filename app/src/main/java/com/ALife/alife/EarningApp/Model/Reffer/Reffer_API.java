package com.ALife.alife.EarningApp.Model.Reffer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Reffer_API {

    @GET("reffer.php")
    Call<String> getRefer( @Query("refferal") String refferal, @Query("id") String id);
}
