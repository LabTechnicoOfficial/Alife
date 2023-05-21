package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class customer_registration_response {
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("message")
    private String message;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
