package com.ALife.alife.EarningApp.Model.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Team_API {
    @GET("allmember.php")
    Call<List<Team_response>> getResponse(@Query("id") String id);
}
