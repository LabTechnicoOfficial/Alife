package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class add_business_summary_image_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
