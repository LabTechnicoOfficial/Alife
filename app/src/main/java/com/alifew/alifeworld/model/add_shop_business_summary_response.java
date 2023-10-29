package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class add_shop_business_summary_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
