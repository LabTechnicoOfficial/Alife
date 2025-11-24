package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class add_product_offer_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
