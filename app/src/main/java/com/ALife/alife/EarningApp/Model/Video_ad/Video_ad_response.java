package com.ALife.alife.EarningApp.Model.Video_ad;

import com.google.gson.annotations.SerializedName;

public class Video_ad_response {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
