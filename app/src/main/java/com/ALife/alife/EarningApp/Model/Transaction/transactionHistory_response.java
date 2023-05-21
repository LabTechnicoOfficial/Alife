package com.ALife.alife.EarningApp.Model.Transaction;

import com.google.gson.annotations.SerializedName;

public class transactionHistory_response {
    @SerializedName("history")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
