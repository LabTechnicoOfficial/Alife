package com.alifew.alife.EarningApp.Model.Profile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Profile_API {
    @GET("profile.php")
    Call<Profile_response> getResponse(@Query("id") String id);
}
