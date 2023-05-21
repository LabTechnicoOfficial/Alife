package com.ALife.alife.EarningApp.Model.Video_ad;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Video_ad_api {

    @GET("getCustomeAddVideo.php")
    Call<Video_ad_response> getVideoAD();
}
