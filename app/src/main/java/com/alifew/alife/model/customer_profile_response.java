package com.alifew.alife.model;

import com.google.gson.annotations.SerializedName;

public class customer_profile_response {
    @SerializedName("customer01r_id")
    public String customer01r_id;
    @SerializedName("customer01r_name")
    public String customer01r_name;
    @SerializedName("customer01r_address")
    public String customer01r_address;
    @SerializedName("customer01r_phone")
    public String customer01r_phone;
    @SerializedName("customer01r_image")
    public String customer01r_image;
    @SerializedName("balance_point")
    public String balance_point;

    public String getCustomer01r_name() {
        return customer01r_name;
    }

    public String getCustomer01r_id() {
        return customer01r_id;
    }

    public void setCustomer01r_id(String customer01r_id) {
        this.customer01r_id = customer01r_id;
    }

    public void setCustomer01r_name(String customer01r_name) {
        this.customer01r_name = customer01r_name;
    }

    public String getCustomer01r_address() {
        return customer01r_address;
    }

    public void setCustomer01r_address(String customer01r_address) {
        this.customer01r_address = customer01r_address;
    }

    public String getCustomer01r_phone() {
        return customer01r_phone;
    }

    public void setCustomer01r_phone(String customer01r_phone) {
        this.customer01r_phone = customer01r_phone;
    }

    public String getCustomer01r_image() {
        return customer01r_image;
    }

    public void setCustomer01r_image(String customer01r_image) {
        this.customer01r_image = customer01r_image;
    }
}
