package com.alifew.alife.model.refer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferResponse {
    @SerializedName("start_at")
    @Expose
    public String startAt;
    @SerializedName("end_at")
    @Expose
    public String endAt;
    @SerializedName("status")
    @Expose
    public String status;
}
