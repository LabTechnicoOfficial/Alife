package com.alifew.alifeworld.model.refer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferPackageResponse {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("shop_id")
    @Expose
    public String shopId;
    @SerializedName("refer_id")
    @Expose
    public String referId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("min_amount")
    @Expose
    public String minAmount;
    @SerializedName("gift")
    @Expose
    public String gift;
    @SerializedName("winner_amount")
    @Expose
    public String winnerAmount;
    @SerializedName("customer_count")
    @Expose
    public int userCount;
}
