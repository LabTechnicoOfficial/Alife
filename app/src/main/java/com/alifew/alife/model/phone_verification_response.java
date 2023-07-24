package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class phone_verification_response {
    @SerializedName("id")
    private String  id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
