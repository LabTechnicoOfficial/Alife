package com.alifew.alife.EarningApp.Model.AddLimit;

import com.google.gson.annotations.SerializedName;

public class addLimit_response {
    @SerializedName("addCount")
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
