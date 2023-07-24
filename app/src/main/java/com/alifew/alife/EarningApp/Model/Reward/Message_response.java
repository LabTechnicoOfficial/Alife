package com.alifew.alife.EarningApp.Model.Reward;

import com.google.gson.annotations.SerializedName;

public class Message_response {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
