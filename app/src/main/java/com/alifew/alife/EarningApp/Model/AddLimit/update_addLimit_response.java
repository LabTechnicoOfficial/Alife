package com.alifew.alife.EarningApp.Model.AddLimit;

import com.google.gson.annotations.SerializedName;

public class update_addLimit_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
