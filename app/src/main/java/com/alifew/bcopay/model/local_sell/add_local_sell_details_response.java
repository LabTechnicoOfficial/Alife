package com.alifew.bcopay.model.local_sell;

import com.google.gson.annotations.SerializedName;

public class add_local_sell_details_response {
    @SerializedName("id")
    private String id;
    @SerializedName("message")
    private String message;

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
