package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class add_payment_transaction_response {
    @SerializedName("message")
    private String message;
@SerializedName("total_due")
private String total_due;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }
}
