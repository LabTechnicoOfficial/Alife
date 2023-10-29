package com.alifew.alifeworld.model.cupon;

import com.google.gson.annotations.SerializedName;

public class edit_delete_response {
    @SerializedName("message")
    private String messsage;

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
