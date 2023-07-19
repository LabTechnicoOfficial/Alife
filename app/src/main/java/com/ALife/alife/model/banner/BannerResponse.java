package com.ALife.alife.model.banner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerResponse {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("banner_link")
    @Expose
    public String bannerLink;
    @SerializedName("shop_id")
    @Expose
    public String shopId;
    @SerializedName("status")
    @Expose
    public String status;
}
