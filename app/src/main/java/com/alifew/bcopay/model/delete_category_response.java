package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class delete_category_response {
    @SerializedName("message")
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
