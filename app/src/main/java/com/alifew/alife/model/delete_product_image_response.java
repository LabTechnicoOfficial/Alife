package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class delete_product_image_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
