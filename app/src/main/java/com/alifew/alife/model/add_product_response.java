package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class add_product_response {
    @SerializedName("message")
    private String message;
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
