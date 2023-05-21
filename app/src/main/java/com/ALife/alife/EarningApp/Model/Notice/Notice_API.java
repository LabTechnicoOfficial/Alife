package com.ALife.alife.EarningApp.Model.Notice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Notice_API {
    @GET("notice.php")
    Call<List<Notice_response>> getResponse();
}
