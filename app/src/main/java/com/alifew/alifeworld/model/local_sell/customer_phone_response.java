package com.alifew.alifeworld.model.local_sell;

import com.google.gson.annotations.SerializedName;

public class customer_phone_response {
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_phone")
    private String customer_phone;

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }
}
