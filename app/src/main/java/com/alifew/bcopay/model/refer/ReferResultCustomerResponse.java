package com.alifew.bcopay.model.refer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferResultCustomerResponse {

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

    @SerializedName("customer_list")
    @Expose
    public List<Customer> customerList;

    public class Customer {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("refer_package_id")
        @Expose
        public String referPackageId;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("point")
        @Expose
        public String point;
        @SerializedName("gift_name")
        @Expose
        public String giftName;
        @SerializedName("position")
        @Expose
        public String position;
        @SerializedName("shop_id")
        @Expose
        public String shopId;
        @SerializedName("status")
        @Expose
        public String status;

    }
}
