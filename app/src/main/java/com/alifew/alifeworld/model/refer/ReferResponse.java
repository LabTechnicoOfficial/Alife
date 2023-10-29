package com.alifew.alifeworld.model.refer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferResponse {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
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
