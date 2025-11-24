package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class OTP_response {
    @SerializedName("status")
    public String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
