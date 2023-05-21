package com.ALife.alife.EarningApp.Model.addRequest;

import com.google.gson.annotations.SerializedName;

public class add_request_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
