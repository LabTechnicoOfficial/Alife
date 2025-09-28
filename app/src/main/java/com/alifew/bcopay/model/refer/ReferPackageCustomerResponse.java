package com.alifew.bcopay.model.refer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferPackageCustomerResponse {
    @SerializedName("refer_phone")
    @Expose
    public String referPhone;

    @SerializedName("refer_points")
    @Expose
    public Integer referPoints;
}
