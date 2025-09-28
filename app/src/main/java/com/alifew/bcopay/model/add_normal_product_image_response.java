package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class add_normal_product_image_response {
    @SerializedName("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

}
