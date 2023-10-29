package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class last_logintime_response {
    @SerializedName("last_time")
    private String last_time;

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }
}
