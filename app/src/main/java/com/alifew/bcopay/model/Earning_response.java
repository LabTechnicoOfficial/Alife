package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class Earning_response {

    @SerializedName("earning_id")
    String earning_id;

    public String getEarning_id() {
        return earning_id;
    }

    public void setEarning_id(String earning_id) {
        this.earning_id = earning_id;
    }
}
