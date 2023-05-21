package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class add_shop_admin_access_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
