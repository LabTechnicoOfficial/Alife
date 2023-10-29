package com.alifew.alifeworld.model.local_sell;

import com.google.gson.annotations.SerializedName;

public class add_local_sell_image_response {
    @SerializedName("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
