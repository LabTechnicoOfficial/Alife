package com.ALife.alife.EarningApp.Model.UserValidation;

import com.google.gson.annotations.SerializedName;

public class userValidation_response {
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("user_balance")
    private String user_balance;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(String user_balance) {
        this.user_balance = user_balance;
    }
}
