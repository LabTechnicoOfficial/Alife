package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class getUser_deviceToken_response {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
