package com.alifew.alife.EarningApp.Model.Registration;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Area_API {
    @GET("agentcity.php")
    Call<List<Area_response>> getResponse();
}
