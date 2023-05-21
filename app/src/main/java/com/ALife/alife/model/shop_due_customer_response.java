package com.ALife.alife.model;

import com.google.gson.annotations.SerializedName;

public class shop_due_customer_response {
    @SerializedName("customer_id")
    public String customer_id;
    @SerializedName("customer_name")
    public String customer_name;
    @SerializedName("customer_address")
    public String customer_address;
    @SerializedName("customer_phone")
    public String customer_phone;
    @SerializedName("customer_image")
    public String customer_image;
    @SerializedName("total_due")
    public String total_due;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }
}
