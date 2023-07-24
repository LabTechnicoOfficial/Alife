package com.alifew.alife.EarningApp.Model.AddInterval;

import com.google.gson.annotations.SerializedName;

public class addInterval_response {
    @SerializedName("time")
    private String time;
    @SerializedName("token")
    private int token;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
