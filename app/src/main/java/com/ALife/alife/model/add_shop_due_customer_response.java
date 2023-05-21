package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class add_shop_due_customer_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
