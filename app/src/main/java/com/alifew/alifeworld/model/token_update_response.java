package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class token_update_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
