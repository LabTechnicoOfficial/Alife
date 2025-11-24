package com.alifew.bcopay.model;

import com.google.gson.annotations.SerializedName;

public class add_product_sell_response {
    @SerializedName("sell_id")
    private String sell_id;

    public String getSell_id() {
        return sell_id;
    }

    public void setSell_id(String sell_id) {
        this.sell_id = sell_id;
    }
}
