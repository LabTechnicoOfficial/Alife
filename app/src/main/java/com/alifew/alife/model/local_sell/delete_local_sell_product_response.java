package com.alifew.alife.model.local_sell;

import com.google.gson.annotations.SerializedName;

public class delete_local_sell_product_response {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
