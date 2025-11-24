package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class shop_status_response {
    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
