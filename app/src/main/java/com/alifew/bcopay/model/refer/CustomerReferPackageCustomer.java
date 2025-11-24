package com.alifew.bcopay.model.refer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerReferPackageCustomer {
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("customer_phone")
    @Expose
    public String customerPhone;
    @SerializedName("sell_amount")
    @Expose
    public Integer sellAmount;
    @SerializedName("points")
    @Expose
    public Integer points;
}
