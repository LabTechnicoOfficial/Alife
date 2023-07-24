package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class update_product_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
