package com.alifew.alife.model.shop_notification;

import com.google.gson.annotations.SerializedName;

public class shop_notification_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
