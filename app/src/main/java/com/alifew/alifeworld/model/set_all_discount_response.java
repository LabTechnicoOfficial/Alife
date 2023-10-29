package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class set_all_discount_response {
    @SerializedName("message")
    private  String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
