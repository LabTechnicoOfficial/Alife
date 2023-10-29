package com.alifew.alifeworld.model;

import com.google.gson.annotations.SerializedName;

public class registration_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
