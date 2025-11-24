package com.alifew.bcopay.model.refer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerShopReferPackageResponse {
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
    @SerializedName("min_refer_package_point")
    @Expose
    public String minReferPackagePoint;
    @SerializedName("customer_count")
    @Expose
    public Integer customerCount;
    @SerializedName("inPackage")
    @Expose
    public Boolean inPackage;
}
