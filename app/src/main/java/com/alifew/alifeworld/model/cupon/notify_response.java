package com.alifew.alifeworld.model.cupon;

import com.google.gson.annotations.SerializedName;

public class notify_response {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
