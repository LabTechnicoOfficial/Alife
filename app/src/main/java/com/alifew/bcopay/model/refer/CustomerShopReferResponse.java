package com.alifew.bcopay.model.refer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerShopReferResponse {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("shop_id")
    @Expose
    public String shopId;
    @SerializedName("start_at")
    @Expose
    public String startAt;
    @SerializedName("end_at")
    @Expose
    public String endAt;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;

}
