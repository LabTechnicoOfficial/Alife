package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class OTP_response {
    @SerializedName("status")
    String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
